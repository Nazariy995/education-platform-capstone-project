package edu.umdearborn.astronomyapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.util.ResultListUtil;

@Repository
@Transactional
public class CommonResourceServiceImpl implements CommonResourceService {

  private static final Logger logger = LoggerFactory.getLogger(CommonResourceServiceImpl.class);

  private EntityManager entityManager;

  public CommonResourceServiceImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public AstroAppUser findByEmail(String email) {

    TypedQuery<AstroAppUser> query = entityManager.createQuery(
        "select u from AstroAppUser u join fetch u.roles r where lower(u.email) = lower(:email)",
        AstroAppUser.class);
    query.setParameter("email", email);
    List<AstroAppUser> result = query.getResultList();

    if (!ResultListUtil.hasResult(result)) {
      logger.debug("Cannot find: {}", email);
      throw new UsernameNotFoundException("Cannot find: " + email);
    }

    return result.get(0);
  }

  @Override
  public Map<String, String> getCourseUserSummary(String email) {

    Query query = entityManager
        .createQuery("select c.id, cu.id from CourseUser cu join cu.user u join cu.course c "
            + "where lower(u.email) = lower(:email)");
    query.setParameter("email", email);
    @SuppressWarnings("unchecked")
    List<Object[]> result = (List<Object[]>) query.getResultList();

    if (ResultListUtil.hasResult(result)) {
      logger.debug("Transforming result to map");
      Map<String, String> map = new HashMap<>();
      result.parallelStream().forEach(e -> map.put(String.valueOf(e[0]), String.valueOf(e[1])));
      return map;
    }

    return null;
  }

}
