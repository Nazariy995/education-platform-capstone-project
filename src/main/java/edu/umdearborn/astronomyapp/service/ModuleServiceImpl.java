package edu.umdearborn.astronomyapp.service;

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
import edu.umdearborn.astronomyapp.util.ResultListUtil;
import edu.umdearborn.astronomyapp.util.jsondecorator.JsonDecorator;

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

      return decorator;
    }

    logger.debug("Not results for module: '{}'", moduleId);
    return null;

  }
}