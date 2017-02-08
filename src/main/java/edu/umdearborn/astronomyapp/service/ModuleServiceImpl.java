package edu.umdearborn.astronomyapp.service;

import static edu.umdearborn.astronomyapp.entity.PageItem.PageItemType.QUESTION;
import static edu.umdearborn.astronomyapp.entity.PageItem.PageItemType.TEXT;
import static edu.umdearborn.astronomyapp.entity.Question.QuestionType.MULTIPLE_CHOICE;
import static edu.umdearborn.astronomyapp.entity.Question.QuestionType.NUMERIC;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.annotations.QueryHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.umdearborn.astronomyapp.entity.Module;
import edu.umdearborn.astronomyapp.entity.MultipleChoiceQuestion;
import edu.umdearborn.astronomyapp.entity.NumericQuestion;
import edu.umdearborn.astronomyapp.entity.PageItem;
import edu.umdearborn.astronomyapp.entity.Question;
import edu.umdearborn.astronomyapp.util.ResultListUtil;
import edu.umdearborn.astronomyapp.util.json.JsonDecorator;

@Service
@Transactional
public class ModuleServiceImpl implements ModuleService {

  private static final Logger logger = LoggerFactory.getLogger(ModuleServiceImpl.class);

  private EntityManager entityManager;

  public ModuleServiceImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Module createModule(Module module) {
    entityManager.persist(module);
    return module;
  }

  @Override
  public Module updateModule(Module module) {
    entityManager.merge(module);
    return module;
  }

  @Override
  public List<Module> getModules(String courseId, boolean showVisibleOnly) {
    StringBuilder jpql =
        new StringBuilder("select m from Module m join m.course c where c.id = :courseId");

    if (showVisibleOnly) {
      logger.debug("Hiding not yet visible modules");
      jpql.append(" and m.visibleTimestamp <= current_timestamp()");
    }

    logger.debug("Resuting JPQL: {}", jpql.toString());
    TypedQuery<Module> query = entityManager.createQuery(jpql.toString(), Module.class);
    query.setParameter("courseId", courseId);

    return query.getResultList();
  }

  @Override
  public JsonDecorator<Module> getModuleDetails(String moduleId) {

    TypedQuery<Module> moduleQuery =
        entityManager.createQuery("select m from Module m where m.id = :moduleId", Module.class);
    EntityGraph<Module> entityGraph = entityManager.createEntityGraph(Module.class);
    entityGraph.addAttributeNodes("humanReadableText");
    moduleQuery.setHint(QueryHints.FETCHGRAPH, entityGraph);
    moduleQuery.setParameter("moduleId", moduleId);
    List<Module> resultList = moduleQuery.getResultList();

    if (ResultListUtil.hasResult(resultList)) {
      JsonDecorator<Module> decorator = new JsonDecorator<>();
      decorator.setPayload(resultList.get(0));

      TypedQuery<Long> pageCountQuery = entityManager.createQuery(
          "select count(p) from Page p join p.module m where m.id = :moduleId group by m.id",
          Long.class);
      pageCountQuery.setParameter("moduleId", moduleId);
      decorator.addProperty("numPages", pageCountQuery.getSingleResult());

      TypedQuery<Long> questionCountQuery =
          entityManager.createQuery("select count(q) from Question q join q.page p join "
              + "p.module m where m.id = :moduleId group by m.id", Long.class);
      questionCountQuery.setParameter("moduleId", moduleId);
      decorator.addProperty("numQuestion", questionCountQuery.getSingleResult());
      
      decorator.addProperty("points", getMaxPoints(moduleId));

      return decorator;
    }

    logger.debug("Not results for module: '{}'", moduleId);
    return null;

  }

  @Override
  public List<PageItem> getPage(String moduleId, int pageNumber) {

    TypedQuery<PageItem> textPageItemQuery = entityManager.createQuery(
        "select i from PageItem i join i.page p join p.module m where m.id = :moduleId and "
            + "p.order = :pageNumber and i.pageItemType = :textType",
        PageItem.class);
    textPageItemQuery.setParameter("moduleId", moduleId).setParameter("pageNumber", pageNumber)
        .setParameter("textType", TEXT);
    List<PageItem> result = textPageItemQuery.getResultList();

    if (!ResultListUtil.hasResult(result)) {
      result = new ArrayList<>();
    }

    TypedQuery<Question> questionPageItemQuery = entityManager.createQuery(
        "select q from Question q join q.page p join p.module m where m.id = :moduleId and "
            + "p.order = :pageNumber and q.pageItemType = :questionType and q.questionType not "
            + "in :machineGradeable",
        Question.class);
    questionPageItemQuery.setParameter("moduleId", moduleId).setParameter("pageNumber", pageNumber)
        .setParameter("questionType", QUESTION)
        .setParameter("machineGradeable", Arrays.asList(MULTIPLE_CHOICE, NUMERIC));
    List<Question> questionResult = questionPageItemQuery.getResultList();

    if (ResultListUtil.hasResult(questionResult)) {
      result.addAll(questionResult);
    }

    TypedQuery<MultipleChoiceQuestion> multipleChoicePageItemQuery = entityManager
        .createQuery("select q from MultipleChoiceQuestion q join q.page p join p.module m where"
            + " m.id = :moduleId and p.order = :pageNumber", MultipleChoiceQuestion.class);
    multipleChoicePageItemQuery.setParameter("moduleId", moduleId).setParameter("pageNumber",
        pageNumber);
    List<MultipleChoiceQuestion> multipleChoiceResult = multipleChoicePageItemQuery.getResultList();

    if (ResultListUtil.hasResult(multipleChoiceResult)) {
      result.addAll(multipleChoiceResult);
    }

    TypedQuery<NumericQuestion> numericPageItemQuery = entityManager
        .createQuery("select q from NumericQuestion q join q.page p join p.module m where"
            + " m.id = :moduleId and p.order = :pageNumber", NumericQuestion.class);
    numericPageItemQuery.setParameter("moduleId", moduleId).setParameter("pageNumber", pageNumber);
    List<NumericQuestion> numericResult = numericPageItemQuery.getResultList();

    if (ResultListUtil.hasResult(numericResult)) {
      result.addAll(numericResult);
    }

    return result;
  }

  @Override
  public BigDecimal getMaxPoints(String moduleId) {
    
    TypedQuery<BigDecimal> query =
        entityManager.createQuery("select sum(q.points) from Question q join q.page p join "
            + "p.module m where m.id = :moduleId group by m.id", BigDecimal.class);
    query.setParameter("moduleId", moduleId);
    
    return query.getSingleResult();
  }
}
