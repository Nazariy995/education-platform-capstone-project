package edu.umdearborn.astronomyapp.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@RunWith(SpringRunner.class)
@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ModuleGroupRepositoryTest {

  @Autowired
  private ModuleGroupRepository repository;

  @Test
  public void getGroupMembersTest() {
    assertThat(repository.getGroupMembers("group1-course1A ", "mod1-course1A"), hasSize(3));
  }

  @Test
  public void getGroupIdTest() {
    assertThat(repository.getGroupId("mod1-course1A", "user1@umich.edu"),
        Matchers.equalTo("group1-course1A "));
  }

  @Test
  public void getUsersInGroupTest() {
    assertThat(repository.getUsersInGroup("mod1-course1A"), hasSize(3));
  }
}
