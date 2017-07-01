package com.nninov.test.integration;

import com.FullstackIntellijApplication;
import com.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.enums.PlansEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FullstackIntellijApplication.class)
public class UserIntegrationTest extends  AbstractIntegrationTest {

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
        User basicUser = createUserFromTestName(testName);

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
        User user = createUserFromTestName(testName);
        userRepo.delete(user.getId());
    }
}
