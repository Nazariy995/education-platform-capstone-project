package edu.umdearborn.astronomyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.umdearborn.astronomyapp.entity.PasswordRecoveryRequest;

public interface PasswordRecoveryRequestRepository
    extends JpaRepository<PasswordRecoveryRequest, String> {

  @Query("select prr from PasswordRecoveryRequest prr join prr.user u "
      + "where prr.token = :token and u.email = :email "
      + "and CURRENT_TIMESTAMP < prr.expirationTimestamp")
  public boolean passwordRecoveryRequestExists(@Param("email") String email,
      @Param("token") String token);

  @Query("delete from PasswordRecoveryRequest where token = :token")
  public void invalidatePasswordRecoveryRequest(@Param("token") String token);
}
