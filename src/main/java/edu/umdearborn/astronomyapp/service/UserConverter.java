package edu.umdearborn.astronomyapp.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.Role;

@Component
public class UserConverter implements Converter<AstroAppUser, User> {

  @Override
  public User convert(AstroAppUser astroAppUser) {
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    for (Role role : astroAppUser.getRoles()) {
      GrantedAuthority authority = new SimpleGrantedAuthority(role.getRole().roleValue());
      authorities.add(authority);
    }
    return new User(astroAppUser.getEmail(), astroAppUser.getPasswordHash(),
        astroAppUser.isEnabled(), astroAppUser.isUserNonExpired(),
        astroAppUser.isPasswordNonExpired(), astroAppUser.isUserNonLocked(), authorities);
  }

}
