package edu.umdearborn.astronomyapp.util;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.util.Assert;

@Configuration
@Transactional
public class StartupHousekeeper {
  // private static final Logger logger = LoggerFactory.getLogger(StartupHousekeeper.class);

  private EntityManager em;

  @Autowired
  public StartupHousekeeper(EntityManager em) {
    this.em = em;
  }

  @EventListener
  public void handleContextRefresh(ContextRefreshedEvent event) {
    em.createNativeQuery("INSERT INTO astro_app_user(\r\n"
        + "        user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password\r\n"
        + "    ) VALUES \r\n"
        + "    ('AAAA', 'ADMIN@umdearborn.edu', 'Joe', true, true, true, true, 'Smoow', 'password'),\r\n"
        + "    ('BBBB', 'INSTRUCTOR@umdearborn.edu', 'Joe', true, true, true, true, 'Smoow', 'password'),\r\n"
        + "    ('CCCC', 'ADMIN.INSTRUCTOR@umdearborn.edu', 'Joe', true, true, true, true, 'Smoow', 'password'),\r\n"
        + "    ('DDDD', 'USER@umdearborn.edu', 'Joe', true, true, true, true, 'Smoow', 'password');")
        .executeUpdate();

    em.createNativeQuery(
        "INSERT INTO role(user_id, role) VALUES \r\n" + "    ('AAAA', 'ADMIN'),\r\n"
            + "    ('BBBB', 'INSTRUCTOR'),\r\n" + "    ('CCCC', 'INSTRUCTOR'),\r\n"
            + "    ('CCCC', 'ADMIN'),\r\n" + "    ('DDDD', 'USER');")
        .executeUpdate();

  }

  @PostConstruct
  public void onInit() {
    Assert.notNull(em);
  }

}
