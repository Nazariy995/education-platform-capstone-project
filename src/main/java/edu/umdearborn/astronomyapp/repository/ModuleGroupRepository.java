package edu.umdearborn.astronomyapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.ModuleGroup;

@Repository
public interface ModuleGroupRepository extends JpaRepository<ModuleGroup, String> {

  @Query("select u from GroupMember gm join gm.moduleGroup mg join mg.module m join "
      + "gm.courseUser cu join cu.user u where cu.isActive = true and u.isEnabled = true and "
      + "mg.id = :moduleGroupId and m.id = :moduleId")
  public List<AstroAppUser> getGroupMembers(@Param("moduleGroupId") String moduleGroupId,
      @Param("moduleId") String moduleId);

  @Query("select mg.id from GroupMember gm join gm.moduleGroup mg join mg.module m join "
      + "gm.courseUser cu join cu.user u where cu.isActive = true and u.isEnabled = true and "
      + "lower(u.email) = (:email) and m.id = :moduleId")
  public String getGroupId(@Param("moduleId") String moduleId, @Param("email") String email);

  @Query("select u from GroupMember gm join gm.moduleGroup mg join mg.module m join "
      + "gm.courseUser cu join cu.user u where cu.isActive = true and u.isEnabled = true "
      + "and m.id = :moduleId")
  public List<AstroAppUser> getUsersInGroup(@Param("moduleId") String moduleId);

}
