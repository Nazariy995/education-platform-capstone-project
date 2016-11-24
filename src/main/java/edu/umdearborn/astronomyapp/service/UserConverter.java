package edu.umdearborn.astronomyapp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;

@Component
public class UserConverter implements Converter<AstroAppUser, User> {

  @Override
  public User convert(AstroAppUser astroAppUser) {
    Collection<GrantedAuthority> authorities =
        astroAppUser.getRoles().parallelStream().map(r -> new SimpleGrantedAuthority(r.roleValue()))
            .collect(Collectors.toCollection(ArrayList::new));

    return new User(astroAppUser.getEmail(), astroAppUser.getPassword(),
        astroAppUser.isEnabled(), astroAppUser.isUserNonExpired(),
        astroAppUser.isPasswordNonExpired(), astroAppUser.isUserNonLocked(), authorities);
  }

}
