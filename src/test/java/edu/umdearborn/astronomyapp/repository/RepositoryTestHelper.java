package edu.umdearborn.astronomyapp.repository;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = true)
public abstract class RepositoryTestHelper {

  @Autowired
  private EntityManagerFactory entityManagerFactory;

  protected TestEntityManager testEntityManager;

  protected PersistenceUnitUtil persistenceUnitUtil;
  
  private static final String SQL = "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('admin1', 'admin1name@gmail.com', 'admin1name', true, true, true, true, 'admin1name', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('admin2', 'admin2name@gmail.com', 'admin2name', true, true, true, true, 'admin2name', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('adminstructor1', 'adminstructor1@gmail.com', 'admininst1', true, true, true, true, 'admininst1', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('adminstructor2', 'adminstructor2@gmail.com', 'admininst2', true, true, true, true, 'admininst2', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('instructor3', 'instructor3@gmail.com', '', true, true, true, true, '', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('instructor4', 'instructor4@gmail.com', 'instructor4fname', true, true, true, true, 'instructor4lname', 'password');\r\n"
      + "\r\n" + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User1', 'User1@gmail.com', 'User1fname', true, true, true, true, 'User1lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User2', 'User2@gmail.com', 'User2fname', true, true, true, true, 'User2lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User3', 'User3@gmail.com', 'User3fname', true, true, true, true, 'User3lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User4', 'User4@gmail.com', 'User4fname', true, true, true, true, 'User4lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User5', 'User5@gmail.com', 'User5fname', true, true, true, true, 'User5lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User6', 'User6@gmail.com', 'User6fname', true, true, true, true, 'User6lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User7', 'User7@gmail.com', 'User7fname', true, true, true, true, 'User7lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User8', 'User8@gmail.com', 'User8fname', true, true, true, true, 'User8lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User9', 'User9@gmail.com', 'User9fname', true, true, true, true, 'User9lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User10', 'User10@gmail.com', 'User10fname', true, true, true, true, 'User10lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User11', 'User11@gmail.com', 'User11fname', true, true, true, true, 'User11lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User12', 'User12@gmail.com', 'User12fname', true, true, true, true, 'User12lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User13', 'User13@gmail.com', 'User13fname', true, true, true, true, 'User13lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User14', 'User14@gmail.com', 'User14fname', true, true, true, true, 'User14lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User15', 'User15@gmail.com', 'User15fname', true, true, true, true, 'User15lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User16', 'User16@gmail.com', 'User16fname', true, true, true, true, 'User16lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User17', 'User17@gmail.com', 'User17fname', true, true, true, true, 'User17lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User18', 'User18@gmail.com', 'User18fname', true, true, true, true, 'User18lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User19', 'User19@gmail.com', 'User19fname', true, true, true, true, 'User19lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User20', 'User20@gmail.com', 'User20fname', true, true, true, true, 'User20lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User21', 'User21@gmail.com', 'User21fname', true, true, true, true, 'User21lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User22', 'User22@gmail.com', 'User22fname', true, true, true, true, 'User22lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User23', 'User23@gmail.com', 'User23fname', true, true, true, true, 'User23lname', 'password');\r\n"
      + "\r\n" + "INSERT INTO astro_app_user(\r\n"
      + "user_id, email, first_name, is_enabled, is_password_non_expired, is_user_non_expired, is_user_non_locked, last_name, password)\r\n"
      + "VALUES ('User24', 'User24@gmail.com', 'User24fname', true, true, true, true, 'User24lname', 'password');\r\n"
      + "\r\n" + "\r\n" + "INSERT INTO role(\r\n" + "user_id, role)\r\n"
      + "VALUES ('admin1', 'ADMIN');\r\n" + "\r\n" + "INSERT INTO role(\r\n" + "user_id, role)\r\n"
      + "VALUES ('admin2', 'ADMIN');\r\n" + "\r\n" + "INSERT INTO role(\r\n" + "user_id, role)\r\n"
      + "VALUES ('adminstructor1', 'ADMIN');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('adminstructor2', 'ADMIN');\r\n" + "\r\n"
      + "INSERT INTO role(\r\n" + "user_id, role)\r\n" + "VALUES ('instructor3', 'USER');\r\n"
      + "\r\n" + "INSERT INTO role(\r\n" + "user_id, role)\r\n"
      + "VALUES ('instructor4', 'USER');\r\n" + "\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User1', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User2', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User3', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User4', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User5', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User6', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User7', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User8', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User9', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User10', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User11', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User12', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User13', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User14', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User15', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User16', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User17', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User18', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User19', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User20', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User21', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User22', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User23', 'USER');\r\n" + "\r\n" + "INSERT INTO role(\r\n"
      + "user_id, role)\r\n" + "VALUES ('User24', 'USER');\r\n" + "\r\n" + "\r\n"
      + "INSERT INTO course(\r\n"
      + "course_id, close_timestamp, course_code, course_title, open_timestamp, subject_code)\r\n"
      + "VALUES ('course1A',dateadd('month', 1, current_timestamp), '1Acode', 'course1Atitle',dateadd('month', -1, current_timestamp) , 'subj1A');\r\n"
      + "\r\n" + "INSERT INTO course(\r\n"
      + "course_id, close_timestamp, course_code, course_title, open_timestamp, subject_code)\r\n"
      + "VALUES ('course1B',dateadd('month', 1, current_timestamp), '1Bcode', 'course1Btitle', dateadd('month', -1, current_timestamp) , 'subj1B');\r\n"
      + "\r\n" + "INSERT INTO course(\r\n"
      + "course_id, close_timestamp, course_code, course_title, open_timestamp, subject_code)\r\n"
      + "VALUES ('course1C',dateadd('month', 1, current_timestamp), '1Ccode', 'course1Ctitle', dateadd('month', -2, current_timestamp) , 'subj1C');\r\n"
      + "\r\n" + "INSERT INTO course(\r\n"
      + "course_id, close_timestamp, course_code, course_title, open_timestamp, subject_code)\r\n"
      + "VALUES ('course2A', dateadd('month', 1, current_timestamp), '2Acode', 'course2Atitle',dateadd('month', -1, current_timestamp) , 'subj2A');\r\n"
      + "\r\n" + "INSERT INTO course(\r\n"
      + "course_id, close_timestamp, course_code, course_title, open_timestamp, subject_code)\r\n"
      + "VALUES ('course2B', dateadd('month', -1, current_timestamp), '2Bcode', 'course2Btitle',  dateadd('month', -2, current_timestamp), 'subj2B');\r\n"
      + "\r\n" + "INSERT INTO course(\r\n"
      + "course_id, close_timestamp, course_code, course_title, open_timestamp, subject_code)\r\n"
      + "VALUES ('course3A', dateadd('month', 1, current_timestamp), '3Acode', 'course3Atitle', dateadd('month', -1, current_timestamp) , 'subj3A');\r\n"
      + "\r\n" + "INSERT INTO course(\r\n"
      + "course_id, close_timestamp, course_code, course_title, open_timestamp, subject_code)\r\n"
      + "VALUES ('course3B', dateadd('month', 3, current_timestamp), '3Bcode', 'course3Btitle', dateadd('month', 1, current_timestamp) , 'subj3B');\r\n"
      + "\r\n" + "INSERT INTO course(\r\n"
      + "course_id, close_timestamp, course_code, course_title, open_timestamp, subject_code)\r\n"
      + "VALUES ('course4A', dateadd('month', 1, current_timestamp), '4Acode', 'course4Atitle', dateadd('month', -1, current_timestamp) , 'subj4A');\r\n"
      + "\r\n" + "INSERT INTO course(\r\n"
      + "course_id, close_timestamp, course_code, course_title, open_timestamp, subject_code)\r\n"
      + "VALUES ('course4B', dateadd('month', 1, current_timestamp), '4Bcode', 'course4Btitle', dateadd('month', -1, current_timestamp) , 'subj4B');\r\n"
      + "\r\n" + "INSERT INTO course(\r\n"
      + "course_id, close_timestamp, course_code, course_title, open_timestamp, subject_code)\r\n"
      + "VALUES ('course4C', dateadd('month', 1, current_timestamp), '4Ccode', 'course4Ctitle', dateadd('month', -2, current_timestamp) , 'subj4C');\r\n"
      + "\r\n" + "\r\n" + "INSERT INTO course_user(\r\n"
      + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('1A-instruc', true, 'INSTRUCTOR', 'course1A', 'adminstructor1');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('1B-instruc', true, 'INSTRUCTOR', 'course1B', 'adminstructor1');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('1C-instruc', true, 'INSTRUCTOR', 'course1C', 'adminstructor1');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('2A-instruc', true, 'INSTRUCTOR', 'course2A', 'adminstructor2');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('2B-instruc', true, 'INSTRUCTOR', 'course2B', 'adminstructor2');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('3A-instruc', true, 'INSTRUCTOR', 'course3A', 'instructor3');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('3B-instruc', true, 'INSTRUCTOR', 'course3B', 'instructor3');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('4A-instruc', true, 'INSTRUCTOR', 'course4A', 'instructor4');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('4B-instruc', true, 'INSTRUCTOR', 'course4B', 'instructor4');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('4C-instruc', true, 'INSTRUCTOR', 'course4C', 'instructor4');\r\n" + "\r\n"
      + "\r\n" + "INSERT INTO course_user(\r\n"
      + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud1-course1A', true, 'STUDENT', 'course1A', 'User1');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud1-course2A', true, 'STUDENT', 'course2A', 'User1');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud2-course1A', true, 'STUDENT', 'course1A', 'User2');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud2-course2A', true, 'STUDENT', 'course2A', 'User2');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud3-course1A', true, 'STUDENT', 'course1A', 'User3');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud3-course2A', true, 'STUDENT', 'course2A', 'User3');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud4-course1A', true, 'STUDENT', 'course1A', 'User4');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud4-course2A', true, 'STUDENT', 'course2A', 'User4');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud5-course1A', true, 'STUDENT', 'course1A', 'User5');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud5-course2A', true, 'STUDENT', 'course2A', 'User5');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud6-course1A', true, 'STUDENT', 'course1A', 'User6');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud6-course2A', true, 'STUDENT', 'course2A', 'User6');\r\n" + "\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud1-course1B', true, 'STUDENT', 'course1B', 'User7');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud1-course2B', true, 'STUDENT', 'course2B', 'User7');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud2-course1B', true, 'STUDENT', 'course1B', 'User8');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud2-course2B', true, 'STUDENT', 'course2B', 'User8');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud3-course1B', true, 'STUDENT', 'course1B', 'User9');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud3-course2B', true, 'STUDENT', 'course2B', 'User9');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud4-course1B', true, 'STUDENT', 'course1B', 'User10');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud4-course2B', true, 'STUDENT', 'course2B', 'User10');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud5-course1B', true, 'STUDENT', 'course1B', 'User11');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud5-course2B', true, 'STUDENT', 'course2B', 'User11');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud6-course1B', true, 'STUDENT', 'course1B', 'User12');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud6-course2B', true, 'STUDENT', 'course2B', 'User12');\r\n" + "\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud1-course3A', true, 'STUDENT', 'course3A', 'User13');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud1-course4A', true, 'STUDENT', 'course4A', 'User13');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud2-course3A', true, 'STUDENT', 'course3A', 'User14');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud2-course4A', true, 'STUDENT', 'course4A', 'User14');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud3-course3A', true, 'STUDENT', 'course3A', 'User15');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud3-course4A', true, 'STUDENT', 'course4A', 'User15');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud4-course3A', true, 'STUDENT', 'course3A', 'User16');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud4-course4A', true, 'STUDENT', 'course4A', 'User16');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud5-course3A', true, 'STUDENT', 'course3A', 'User17');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud5-course4A', true, 'STUDENT', 'course4A', 'User17');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud6-course3A', true, 'STUDENT', 'course3A', 'User18');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud6-course4A', true, 'STUDENT', 'course4A', 'User18');\r\n" + "\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud1-course3B', true, 'STUDENT', 'course3B', 'User19');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud1-course4B', true, 'STUDENT', 'course4B', 'User19');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud2-course3B', true, 'STUDENT', 'course3B', 'User20');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud2-course4B', true, 'STUDENT', 'course4B', 'User20');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud3-course3B', true, 'STUDENT', 'course3B', 'User21');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud3-course4B', true, 'STUDENT', 'course4B', 'User21');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud4-course3B', true, 'STUDENT', 'course3B', 'User22');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud4-course4B', true, 'STUDENT', 'course4B', 'User22');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud5-course3B', true, 'STUDENT', 'course3B', 'User23');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud5-course4B', true, 'STUDENT', 'course4B', 'User23');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud6-course3B', true, 'STUDENT', 'course3B', 'User24');\r\n" + "\r\n"
      + "INSERT INTO course_user(\r\n" + "course_user_id, is_active, role, course_id, user_id)\r\n"
      + "VALUES ('stud6-course4B', true, 'STUDENT', 'course4B', 'User24');";
/*
  protected Map<String, AstroAppUser> users = new HashMap<>();

  protected Map<String, Course> courses = new HashMap<>();

  protected Map<String, CourseUser> courseUsers = new HashMap<>();
*/

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
  public void setupTestData() /*throws InterruptedException*/ {
    testEntityManager.getEntityManager().createNativeQuery(SQL).executeUpdate();
//    entityManagerFactory.createEntityManager().createNativeQuery(SQL).executeUpdate();
  }
/*
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
*/
}
