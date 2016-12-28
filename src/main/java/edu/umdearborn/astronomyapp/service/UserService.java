package edu.umdearborn.astronomyapp.service;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.repository.UserRepository;
import edu.umdearborn.astronomyapp.util.converter.UserConverter;

@Service
@Transactional
public class UserService implements UserDetailsService {

  private UserConverter userConverter;

  private UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository, UserConverter userConverter) {
    this.userRepository = userRepository;
    this.userConverter = userConverter;
  }

  private AstroAppUser getUser(String username) {
    Optional<AstroAppUser> user = Optional.of(userRepository.findByEmail(username));
    return user.orElseThrow(() -> new UsernameNotFoundException("Cannot find: " + username));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userConverter.convert(getUser(username));
  }

  @PostConstruct
  public void postConstruct() {
    Assert.notNull(userRepository);
    Assert.notNull(userConverter);
  }

}
