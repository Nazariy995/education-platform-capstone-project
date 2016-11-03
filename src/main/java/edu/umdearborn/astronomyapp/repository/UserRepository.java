package edu.umdearborn.astronomyapp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.Role;

/**
 * Interface to persist {@link AstroAppUser} to the database. There is no implementation because
 * Spring automatically generates and injects logic
 * 
 * @author Patrick Bremer
 *
 */
@Repository
public interface UserRepository extends JpaRepository<AstroAppUser, String> {

  @EntityGraph(attributePaths = "roles", type = EntityGraphType.LOAD)
  @Query("select u from AstroAppUser u join fetch u.roles r where " +
      "lower(u.email) = lower(:email)")
  public AstroAppUser findByEmail(@Param(value = "email") String email);

  @Query("select u from AstroAppUser u where " +
      "lower(u.email) = lower(:email) and u.passwordHash = :password")
  public AstroAppUser authUser(@Param(value = "email") String email,
      @Param(value = "password") String password);

  @Query("select r from Role r where lower(r.user.email) = lower(:email)")
  public Collection<Role> getUserRoles(@Param(value = "email") String email);
}
