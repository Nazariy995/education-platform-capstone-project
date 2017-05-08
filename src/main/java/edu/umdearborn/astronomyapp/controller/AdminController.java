package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.ADMIN_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.Valid;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.service.UserManagementService;

@RestController
@RequestMapping(REST_PATH_PREFIX + ADMIN_PATH)
public class AdminController {

	private UserManagementService userManagementService;
	private EntityManager entityManager;

	public AdminController(UserManagementService userManagementService) {
		this.userManagementService = userManagementService;
	}

	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public List<AstroAppUser> getAdmins(){
		return userManagementService.getAdminInstructorList();
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public AstroAppUser createNewUser(@Valid @RequestBody AstroAppUser user) {

		if (Optional.ofNullable(user.getRoles()).orElseGet(HashSet<AstroAppUser.Role>::new)
				.contains(AstroAppUser.Role.USER)) {
			throw new AccessDeniedException("Cannot create student users");
		}

		return userManagementService.persistNewUser(user);
	}

	@RequestMapping(value = "/user/{email}", method = RequestMethod.PUT)
	public AstroAppUser updateUser(@PathVariable String email, @Valid @RequestBody AstroAppUser user,
			Principal principal) {

		user.setEmail(email);

		//if the current user that's being edited is the user that is logged in, make sure they're not removing
		//the admin role

		if(principal.getName().equalsIgnoreCase(user.getEmail()) &&
				!Optional.ofNullable(user.getRoles()).orElseGet(HashSet<AstroAppUser.Role>::new)
				.contains(AstroAppUser.Role.ADMIN)){
			throw new AccessDeniedException("Cannot remove admin role");
		}

		if (Optional.ofNullable(user.getRoles()).orElseGet(HashSet<AstroAppUser.Role>::new)
				.contains(AstroAppUser.Role.USER)) {
			throw new AccessDeniedException("Cannot create student users");
		}
		return userManagementService.updateUser(user);
	}

	// create method to query a list for all admins and instructors (check
	// UserManagementServiceImpl.java)

}
