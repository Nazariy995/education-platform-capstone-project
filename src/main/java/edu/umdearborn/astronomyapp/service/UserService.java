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

@Service
@Transactional
public class UserService implements UserDetailsService {

  private UserRepository userRepository;

  private UserConverter userConverter;

  @Autowired
  public UserService(UserRepository userRepository, UserConverter userConverter) {
    this.userRepository = userRepository;
    this.userConverter = userConverter;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userConverter.convert(getUser(username));
  }

  @PostConstruct
  public void onInit() {
    Assert.notNull(userRepository);
    Assert.notNull(userConverter);
  }

  private AstroAppUser getUser(String username) {
    Optional<AstroAppUser> user = Optional.of(userRepository.findByEmail(username));
    return user.orElseThrow(() -> new UsernameNotFoundException("Cannot find: " + username));
  }

}
