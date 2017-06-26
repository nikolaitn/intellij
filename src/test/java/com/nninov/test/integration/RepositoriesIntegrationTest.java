package com.nninov.test.integration;

import com.FullstackIntellijApplication;
import com.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.backend.persistence.repositories.PlanRepository;
import com.devopsbuddy.backend.persistence.repositories.RoleRepository;
import com.devopsbuddy.backend.persistence.repositories.UserRepository;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import com.devopsbuddy.utils.UserUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FullstackIntellijApplication.class)
public class RepositoriesIntegrationTest {

    @Autowired
    private PlanRepository planRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserRepository userRepo;

    @Before
    public void init() {
        Assert.assertNotNull(planRepo);
        Assert.assertNotNull(roleRepo);
        Assert.assertNotNull(userRepo);
    }

    @Test
    public void testCreatedNewPlan() throws Exception {
        Plan basicPlan = createPlan(PlansEnum.BASIC);
        planRepo.save(basicPlan);
        Plan retrievedPlan = planRepo.findOne(PlansEnum.BASIC.getId());
        Assert.assertNotNull(retrievedPlan);
    }

    private Plan createPlan(PlansEnum planType) {
        Plan result = new Plan();
        result.setId(planType.getId());
        result.setName(planType.getPlanName());
        return result;
    }

    @Test
    public void createNewUser() throws Exception {
        Plan plan = createPlan(PlansEnum.BASIC);
        planRepo.save(plan);

        User user = UserUtils.createBasicUser("john", "jonh@mail.com");
        user.setPlan(plan);

        Role role = createRole(RolesEnum.BASIC);
        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRoles.add(userRole);

        user.getUserRoles().addAll(userRoles);

        for (UserRole ur : userRoles) {
            roleRepo.save(ur.getRole());
        }

        user = userRepo.save(user);
        User newlyCreatedUser = userRepo.findOne(user.getId());

        Assert.assertNotNull(newlyCreatedUser);
        Assert.assertTrue(newlyCreatedUser.getId() != 0);
        Assert.assertNotNull(newlyCreatedUser.getPlan());
        Assert.assertNotNull(newlyCreatedUser.getPlan().getId());
        Set<UserRole> newlyCreatedUserRoles = newlyCreatedUser.getUserRoles();
                
        for (UserRole ur : newlyCreatedUserRoles) {
            Assert.assertNotNull(ur.getRole());
            Assert.assertTrue(ur.getRole().getId() != 0);
        }
    }
    
    private Role createRole(RolesEnum roleType) {
        Role result = new Role();
        result.setId(roleType.getId());
        result.setName(roleType.getRoleName());
        return result;
    }
}
