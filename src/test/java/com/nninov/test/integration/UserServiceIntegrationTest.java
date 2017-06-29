package com.nninov.test.integration;

import com.FullstackIntellijApplication;
import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.backend.service.UserService;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import com.devopsbuddy.utils.UserUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FullstackIntellijApplication.class)
public class UserServiceIntegrationTest {

	@Autowired
	private UserService userService;

	@Rule
	public TestName testName = new TestName();
	
	@Test
	public void testCreateNewUser() {
		Set<UserRole> userRoles = new HashSet<>();
		String userName = testName.getMethodName();
		String email = testName.getMethodName() + "@blabslbas.com";
		User basicUser = UserUtils.createBasicUser(userName, email);
		userRoles.add(new UserRole(basicUser, new Role(RolesEnum.BASIC)));
		
		User user = userService.createUser(basicUser, PlansEnum.BASIC, userRoles);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());
 	}
}
