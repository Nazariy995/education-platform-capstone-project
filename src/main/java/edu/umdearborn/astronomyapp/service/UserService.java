package edu.umdearborn.astronomyapp.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.util.ResultListUtil;
import edu.umdearborn.astronomyapp.util.converter.UserConverter;

@Service
@Transactional
public class UserService implements UserDetailsService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  private EntityManager entityManager;
  private UserConverter userConverter;

  public UserService(EntityManager entityManager, UserConverter userConverter) {
    this.entityManager = entityManager;
    this.userConverter = userConverter;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    TypedQuery<AstroAppUser> query = entityManager.createQuery(
        "select u from AstroAppUser u join fetch u.roles r where lower(u.email) = lower(:email)",
        AstroAppUser.class);
    query.setParameter("email", username);
    List<AstroAppUser> result = query.getResultList();

    if (!ResultListUtil.hasResult(result)) {
      logger.debug("Cannot find: {}", username);
      throw new UsernameNotFoundException("Cannot find: " + username);
    }

    return userConverter.convert(result.get(0));

  }

}
