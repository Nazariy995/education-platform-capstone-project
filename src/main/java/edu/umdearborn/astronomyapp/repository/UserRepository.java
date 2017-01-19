package edu.umdearborn.astronomyapp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;

/**
 * Interface to persist {@link AstroAppUser} to the database. There is no implementation because
 * Spring automatically generates and injects logic
 *
 * @author Patrick Bremer
 *
 */
@Repository
public interface UserRepository extends JpaRepository<AstroAppUser, String> {

  @Query("select count(r) > 0 from AstroAppUser u join u.roles r where r = 'ADMIN'")
  public boolean adminExists();

  @Query("select count(u) > 0 from AstroAppUser u where lower(u.email) = lower(:email)")
  public boolean emailExists(@Param(value = "email") String email);

  @EntityGraph(attributePaths = "roles", type = EntityGraphType.LOAD)
  @Query("select u from AstroAppUser u join fetch u.roles r where "
      + "lower(u.email) = lower(:email)")
  public AstroAppUser findByEmail(@Param(value = "email") String email);

}
