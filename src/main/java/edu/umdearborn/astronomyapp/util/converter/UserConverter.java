package edu.umdearborn.astronomyapp.util.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;

@Component
public class UserConverter implements Converter<AstroAppUser, User> {

  private static final Logger logger = LoggerFactory.getLogger(UserConverter.class);
  
  @Override
  public User convert(AstroAppUser astroAppUser) {
    
    logger.debug("Converting {} to {}", astroAppUser.toString(), UserConverter.class.getName());
    
    Collection<GrantedAuthority> authorities =
        astroAppUser.getRoles().parallelStream().map(r -> new SimpleGrantedAuthority(r.roleValue()))
            .collect(Collectors.toCollection(ArrayList::new));

    return new User(astroAppUser.getEmail(), astroAppUser.getPassword(), astroAppUser.isEnabled(),
        astroAppUser.isUserNonExpired(), astroAppUser.isPasswordNonExpired(),
        astroAppUser.isUserNonLocked(), authorities);
  }

}
