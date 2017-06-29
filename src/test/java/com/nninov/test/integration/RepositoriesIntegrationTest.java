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
public class RepositoriesIntegrationTest {

    @Autowired
    private PlanRepository planRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserRepository userRepo;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void init() {
        Assert.assertNotNull(planRepo);
        Assert.assertNotNull(roleRepo);
        Assert.assertNotNull(userRepo);
    }

    @Test
    public void testCreatedNewPlan() throws Exception {
        Plan basicPlan = new Plan(PlansEnum.BASIC);
        planRepo.save(basicPlan);
        Plan retrievedPlan = planRepo.findOne(PlansEnum.BASIC.getId());
        Assert.assertNotNull(retrievedPlan);
    }


    @Test
    public void createNewUser() throws Exception {
        String userName = testName.getMethodName();
        String email = testName.getMethodName() + "@blabslbas.com";
        User basicUser = createUser(userName, email);

        User newlyCreatedUser = userRepo.findOne(basicUser.getId());
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

    @Test
    public void testDeleteUser() throws Exception {
        String userName = testName.getMethodName();
        String email = testName.getMethodName() + "@blabslbas.com";
        User user = createUser(userName, email);
        userRepo.delete(user.getId());
    }

    private User createUser(String name, String email) {
        Plan basicBlan = new Plan(PlansEnum.BASIC);
        planRepo.save(basicBlan);

        User basicUser = UserUtils.createBasicUser(name, email);
        basicUser.setPlan(basicBlan);

        Role basicRole = new Role(RolesEnum.BASIC);
        roleRepo.save(basicRole);

        UserRole userRole = new UserRole(basicUser, basicRole);
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(userRole);
        basicUser.getUserRoles().addAll(userRoles);

        userRepo.save(basicUser);

        return basicUser;
    }
}
