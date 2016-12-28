package edu.umdearborn.astronomyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.umdearborn.astronomyapp.entity.PasswordRecoveryRequest;

public interface PasswordRecoveryRequestRepository
    extends JpaRepository<PasswordRecoveryRequest, String> {

  @Query("delete from PasswordRecoveryRequest where id = :token")
  public void invalidatePasswordRecoveryRequest(@Param("token") String token);

  @Query("select prr from PasswordRecoveryRequest prr join prr.user u "
      + "where prr.id = :token and u.email = :email "
      + "and CURRENT_TIMESTAMP < prr.expirationTimestamp")
  public boolean passwordRecoveryRequestExists(@Param("email") String email,
      @Param("token") String token);
}
