package edu.umdearborn.astronomyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.umdearborn.astronomyapp.entity.Role;

/**
 * Interface to persist {@link Role} to the database. There is no implementation because Spring
 * automatically generates and injects logic
 * 
 * @author Patrick Bremer
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

  @Query("select count(r) > 0 from Role r where r.role = 'ROLE_ADMIN'")
  public boolean adminExists();
}
