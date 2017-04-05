package edu.umdearborn.astronomyapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.base.Predicates;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.util.ResultListUtil;
import edu.umdearborn.astronomyapp.util.email.NewUserEmailContextBuilder;

@Service
@Transactional
public class UserManagementServiceImpl implements UserManagementService {

	private static final Logger logger = LoggerFactory.getLogger(UserManagementServiceImpl.class);

	private EntityManager entityManager;
	private EmailService emailService;
	private PasswordEncoder passwordEncoder;

	public UserManagementServiceImpl(EntityManager entityManager, EmailService emailService,
			PasswordEncoder passwordEncoder) {
		this.entityManager = entityManager;
		this.emailService = emailService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<CourseUser> addUsersToCourse(String courseId, CourseUser... users) {
		Course course = entityManager.find(Course.class, courseId);

		List<CourseUser> userList = StreamSupport.stream(Arrays.spliterator(users), false).map(u -> {
			if (!emailExists(u.getUser().getEmail())) {
				logger.info("Inserting {} as application user", u.getUser().getEmail());
				u.setUser(persistNewUser(u.getUser()));
			} else {
				u.setUser(
						entityManager
								.createQuery("select u from AstroAppUser u where lower(u.email) = lower(:email)",
										AstroAppUser.class)
								.setParameter("email", u.getUser().getEmail()).getSingleResult());
			}

			u.setCourse(course);

			boolean canAdd = entityManager
					.createQuery("select count(cu) = 0 from CourseUser cu join cu.user u join cu.course c "
							+ "where lower(u.email) = lower(:email) and c.id = :courseId", Boolean.class)
					.setParameter("email", u.getUser().getEmail()).setParameter("courseId", courseId).getSingleResult();

			if (canAdd) {
				entityManager.persist(u);
				return u;
			}

			logger.info("USer: '{}' already enroleld in course: '{}'", u.getUser().getEmail(), courseId);
			return null;
		}).filter(Predicates.notNull()).collect(Collectors.toList());

		return userList;
	}

	public boolean emailExists(String email) {
		return entityManager.createQuery("select count(u) > 0 from AstroAppUser u where lower(u.email) = lower(:email)",
				Boolean.class).setParameter("email", email).getSingleResult();
	}

	@Override
	public AstroAppUser persistNewUser(AstroAppUser user) {

		user.setPassword(RandomStringUtils.randomAlphanumeric(12));

		Map<String, String> context = emailService.buildEmailContext(new NewUserEmailContextBuilder(user));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		emailService.send(context);

		entityManager.persist(user);

		return user;
	}

	@PostConstruct
	public void postConstruct() {
		Assert.notNull(emailService);
		Assert.notNull(passwordEncoder);
	}

	@Override
	public AstroAppUser updateUser(AstroAppUser user) {
		List<AstroAppUser> results = entityManager
				.createNamedQuery("select u from AstroAppUser u where lower(u.email) = lower(:email)",
						AstroAppUser.class)
				.setParameter("email", user.getEmail()).getResultList();
		
		if (ResultListUtil.hasResult(results)) {
			AstroAppUser managedUser = results.get(0);
			managedUser.setEnabled(user.isEnabled());
			managedUser.setRoles(user.getRoles());
			// managedUser.setPassword(user.getPassword());
			managedUser.setUserNonExpired(user.isUserNonExpired());
			//managedUser.setUserNonLocked(isUserNonLocked);
			
			entityManager.merge(managedUser);
			
			return managedUser;
		}
		
		
		// throw some exception...
		return null;
	}

	@Override
	public CourseUser updateCourseUserStatus(String courseUserId, boolean isActive) {
		CourseUser user = entityManager.find(CourseUser.class, courseUserId);
		user.setActive(isActive);
		entityManager.merge(user);

		return user;
	}
	
	// selecy u from AtroAppUser u where u.roles in ('ADMIN', 'INSTRUCTOR')
	//make new method

}
