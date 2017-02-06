package edu.umdearborn.astronomyapp.repository;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public abstract class RepositoryTestHelper {

  @Autowired
  private EntityManagerFactory entityManagerFactory;

  protected TestEntityManager testEntityManager;

  protected PersistenceUnitUtil persistenceUnitUtil;

  @PostConstruct
  public void postConstruct() {
    testEntityManager = new TestEntityManager(entityManagerFactory);
    persistenceUnitUtil = entityManagerFactory.getPersistenceUnitUtil();
  }

  protected void assertPropertyLoaded(Object entity, String... attributeNames) {
    Arrays.stream(attributeNames).forEach(name -> {
      assertThat(name + " has not been initalized", persistenceUnitUtil.isLoaded(entity, name));
    });
  }

  protected void assertPropertyNotLoaded(Object entity, String... attributeNames) {
    Arrays.stream(attributeNames).forEach(name -> {
      assertThat(name + " has been initalized", !persistenceUnitUtil.isLoaded(entity, name));
    });
  }

  protected void assertEntityLoaded(Object entity) {
    assertThat(entity + " has not been initalized", persistenceUnitUtil.isLoaded(entity));
  }

  protected void assertEntityNotLoaded(Object entity) {
    assertThat(entity + " has been initalized", !persistenceUnitUtil.isLoaded(entity));
  }
}
