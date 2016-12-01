package edu.umdearborn.astronomyapp.repository;

import static org.apache.commons.lang3.RandomStringUtils.randomAscii;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.entity.CourseUser.CourseRole;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = true)
public abstract class RepositoryTestHelper {

  @Autowired
  private EntityManagerFactory entityManagerFactory;

  protected TestEntityManager testEntityManager;

  protected PersistenceUnitUtil persistenceUnitUtil;

  protected Map<String, AstroAppUser> users = new HashMap<>();

  protected Map<String, Course> courses = new HashMap<>();

  protected Map<String, CourseUser> courseUsers = new HashMap<>();

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

  @Before
  public void setupTestData() throws InterruptedException {
    setupUsers();
    setupCourse();
    setupCourseUsers();
    testEntityManager.flush();
    Thread.sleep(1000);
  }

  private void setupUsers() {
    AstroAppUser admin1 = new AstroAppUser();
    admin1.setEmail("admin1@email.com");
    admin1.setFirstName(randomAscii(15));
    admin1.setLastName(randomAscii(15));
    admin1.setPassword(randomAscii(15));
    admin1.setRoles(Arrays.asList(AstroAppUser.Role.ADMIN).stream().collect(Collectors.toSet()));
    testEntityManager.persist(admin1);
    users.put("admin1", admin1);

    AstroAppUser adminInst = new AstroAppUser();
    adminInst.setEmail("adminInst@email.com");
    adminInst.setFirstName(randomAscii(15));
    adminInst.setLastName(randomAscii(15));
    adminInst.setPassword(randomAscii(15));
    adminInst.setRoles(Arrays.asList(AstroAppUser.Role.ADMIN, AstroAppUser.Role.INSTRUCTOR).stream()
        .collect(Collectors.toSet()));
    testEntityManager.persist(adminInst);
    users.put("adminInst", adminInst);

    AstroAppUser inst1 = new AstroAppUser();
    inst1.setEmail("inst1@email.com");
    inst1.setFirstName(randomAscii(15));
    inst1.setLastName(randomAscii(15));
    inst1.setPassword(randomAscii(15));
    inst1
        .setRoles(Arrays.asList(AstroAppUser.Role.INSTRUCTOR).stream().collect(Collectors.toSet()));
    testEntityManager.persist(inst1);
    users.put("inst1", inst1);

    AstroAppUser inst2 = new AstroAppUser();
    inst2.setEmail("inst2@email.com");
    inst2.setFirstName(randomAscii(15));
    inst2.setLastName(randomAscii(15));
    inst2.setPassword(randomAscii(15));
    inst2
        .setRoles(Arrays.asList(AstroAppUser.Role.INSTRUCTOR).stream().collect(Collectors.toSet()));
    testEntityManager.persist(inst2);
    users.put("inst2", inst2);

    AstroAppUser inst3 = new AstroAppUser();
    inst3.setEmail("inst3@email.com");
    inst3.setFirstName(randomAscii(15));
    inst3.setLastName(randomAscii(15));
    inst3.setPassword(randomAscii(15));
    inst3
        .setRoles(Arrays.asList(AstroAppUser.Role.INSTRUCTOR).stream().collect(Collectors.toSet()));
    testEntityManager.persist(inst3);
    users.put("inst3", inst3);

    AstroAppUser user1 = new AstroAppUser();
    user1.setEmail("user1@email.com");
    user1.setFirstName(randomAscii(15));
    user1.setLastName(randomAscii(15));
    user1.setPassword(randomAscii(15));
    user1.setRoles(Arrays.asList(AstroAppUser.Role.USER).stream().collect(Collectors.toSet()));
    testEntityManager.persist(user1);
    users.put("user1", user1);

    AstroAppUser user2 = new AstroAppUser();
    user2.setEmail("user1@email.com");
    user2.setFirstName(randomAscii(15));
    user2.setLastName(randomAscii(15));
    user2.setPassword(randomAscii(15));
    user2.setRoles(Arrays.asList(AstroAppUser.Role.USER).stream().collect(Collectors.toSet()));
    testEntityManager.persist(user2);
    users.put("user2", user2);

    AstroAppUser user3 = new AstroAppUser();
    user3.setEmail("user1@email.com");
    user3.setFirstName(randomAscii(15));
    user3.setLastName(randomAscii(15));
    user3.setPassword(randomAscii(15));
    user3.setRoles(Arrays.asList(AstroAppUser.Role.USER).stream().collect(Collectors.toSet()));
    testEntityManager.persist(user3);
    users.put("user3", user3);

    AstroAppUser user4 = new AstroAppUser();
    user4.setEmail("user1@email.com");
    user4.setFirstName(randomAscii(15));
    user4.setLastName(randomAscii(15));
    user4.setPassword(randomAscii(15));
    user4.setRoles(Arrays.asList(AstroAppUser.Role.USER).stream().collect(Collectors.toSet()));
    testEntityManager.persist(user4);
    users.put("user4", user4);

    AstroAppUser user5 = new AstroAppUser();
    user5.setEmail("user1@email.com");
    user5.setFirstName(randomAscii(15));
    user5.setLastName(randomAscii(15));
    user5.setPassword(randomAscii(15));
    user5.setRoles(Arrays.asList(AstroAppUser.Role.USER).stream().collect(Collectors.toSet()));
    testEntityManager.persist(user5);
    users.put("user5", user5);

    AstroAppUser user6 = new AstroAppUser();
    user6.setEmail("user1@email.com");
    user6.setFirstName(randomAscii(15));
    user6.setLastName(randomAscii(15));
    user6.setPassword(randomAscii(15));
    user6.setRoles(Arrays.asList(AstroAppUser.Role.USER).stream().collect(Collectors.toSet()));
    testEntityManager.persist(user6);
    users.put("user6", user6);

    AstroAppUser user7 = new AstroAppUser();
    user7.setEmail("user1@email.com");
    user7.setFirstName(randomAscii(15));
    user7.setLastName(randomAscii(15));
    user7.setPassword(randomAscii(15));
    user7.setRoles(Arrays.asList(AstroAppUser.Role.USER).stream().collect(Collectors.toSet()));
    testEntityManager.persist(user7);
    users.put("user7", user7);

    AstroAppUser user8 = new AstroAppUser();
    user8.setEmail("user1@email.com");
    user8.setFirstName(randomAscii(15));
    user8.setLastName(randomAscii(15));
    user8.setPassword(randomAscii(15));
    user8.setRoles(Arrays.asList(AstroAppUser.Role.USER).stream().collect(Collectors.toSet()));
    testEntityManager.persist(user8);
    users.put("user8", user8);

    AstroAppUser user9 = new AstroAppUser();
    user9.setEmail("user1@email.com");
    user9.setFirstName(randomAscii(15));
    user9.setLastName(randomAscii(15));
    user9.setPassword(randomAscii(15));
    user9.setRoles(Arrays.asList(AstroAppUser.Role.USER).stream().collect(Collectors.toSet()));
    testEntityManager.persist(user9);
    users.put("user9", user9);

    AstroAppUser user10 = new AstroAppUser();
    user10.setEmail("user1@email.com");
    user10.setFirstName(randomAscii(15));
    user10.setLastName(randomAscii(15));
    user10.setPassword(randomAscii(15));
    user10.setRoles(Arrays.asList(AstroAppUser.Role.USER).stream().collect(Collectors.toSet()));
    testEntityManager.persist(user10);
    users.put("user10", user10);

  }

  private void setupCourse() throws InterruptedException {
    Course openCourse1 = new Course();
    openCourse1.setCourseTitle(randomAscii(10));
    openCourse1.setCourseCode(randomAscii(3));
    openCourse1.setSubjectCode(randomAscii(3));
    openCourse1.setOpenTimestamp(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 7)));
    openCourse1.setCloseTimestamp(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7)));
    testEntityManager.persist(openCourse1);
    courses.put("openCourse1", openCourse1);

    Course openCourse2 = new Course();
    openCourse2.setCourseTitle(randomAscii(10));
    openCourse2.setCourseCode(randomAscii(3));
    openCourse2.setSubjectCode(randomAscii(3));
    openCourse2.setOpenTimestamp(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 7)));
    openCourse2.setCloseTimestamp(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7)));
    testEntityManager.persist(openCourse2);
    courses.put("openCourse2", openCourse2);

    Course openCourse3 = new Course();
    openCourse3.setCourseTitle(randomAscii(10));
    openCourse3.setCourseCode(randomAscii(3));
    openCourse3.setSubjectCode(randomAscii(3));
    openCourse3.setOpenTimestamp(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 7)));
    openCourse3.setCloseTimestamp(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7)));
    testEntityManager.persist(openCourse3);
    courses.put("openCourse3", openCourse3);

    Course closeCourse1 = new Course();
    closeCourse1.setCourseTitle(randomAscii(10));
    closeCourse1.setCourseCode(randomAscii(3));
    closeCourse1.setSubjectCode(randomAscii(3));
    closeCourse1.setOpenTimestamp(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 7)));
    closeCourse1.setCloseTimestamp(new Date(System.currentTimeMillis() + 1000));
    testEntityManager.persist(closeCourse1);
    courses.put("closeCourse1", closeCourse1);

    Course closeCourse2 = new Course();
    closeCourse2.setCourseTitle(randomAscii(10));
    closeCourse2.setCourseCode(randomAscii(3));
    closeCourse2.setSubjectCode(randomAscii(3));
    closeCourse2.setOpenTimestamp(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 7)));
    closeCourse2.setCloseTimestamp(new Date(System.currentTimeMillis() + 1000));
    testEntityManager.persist(closeCourse2);
    courses.put("closeCourse2", closeCourse2);

    Course closeCourse3 = new Course();
    closeCourse3.setCourseTitle(randomAscii(10));
    closeCourse3.setCourseCode(randomAscii(3));
    closeCourse3.setSubjectCode(randomAscii(3));
    closeCourse3.setOpenTimestamp(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 7)));
    closeCourse3.setCloseTimestamp(new Date(System.currentTimeMillis() + 1000));
    testEntityManager.persist(closeCourse3);
    courses.put("closeCourse3", closeCourse3);

    Course closeCourse4 = new Course();
    closeCourse4.setCourseTitle(randomAscii(10));
    closeCourse4.setCourseCode(randomAscii(3));
    closeCourse4.setSubjectCode(randomAscii(3));
    closeCourse4.setOpenTimestamp(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 7)));
    closeCourse4.setCloseTimestamp(new Date(System.currentTimeMillis() + 1000));
    testEntityManager.persist(closeCourse4);
    courses.put("closeCourse4", closeCourse4);

  }

  private void setupCourseUsers() {
    CourseUser student1_1 = new CourseUser();
    student1_1.setCourse(courses.get("openCourse1"));
    student1_1.setUser(users.get("user1"));
    student1_1.setRole(CourseRole.STUDENT);
    testEntityManager.persist(student1_1);
    courseUsers.put("student1_1", student1_1);

    CourseUser student2_1 = new CourseUser();
    student2_1.setCourse(courses.get("openCourse1"));
    student2_1.setUser(users.get("user2"));
    student2_1.setRole(CourseRole.STUDENT);
    testEntityManager.persist(student2_1);
    courseUsers.put("student2_1", student2_1);

    CourseUser student3_1 = new CourseUser();
    student3_1.setCourse(courses.get("openCourse1"));
    student3_1.setUser(users.get("user3"));
    student3_1.setRole(CourseRole.STUDENT);
    testEntityManager.persist(student3_1);
    courseUsers.put("student3_1", student3_1);

    CourseUser student1_2 = new CourseUser();
    student1_2.setCourse(courses.get("openCourse2"));
    student1_2.setUser(users.get("user1"));
    student1_2.setRole(CourseRole.STUDENT);
    testEntityManager.persist(student1_2);
    courseUsers.put("student1_2", student1_2);

    CourseUser inst1_1 = new CourseUser();
    inst1_1.setCourse(courses.get("openCourse1"));
    inst1_1.setUser(users.get("inst1"));
    inst1_1.setRole(CourseRole.INSTRUCTOR);
    testEntityManager.persist(inst1_1);
    courseUsers.put("inst1_1", inst1_1);

  }

}
