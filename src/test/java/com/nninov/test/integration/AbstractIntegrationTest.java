package com.nninov.test.integration;

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
import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public abstract  class AbstractIntegrationTest {
    @Autowired
    protected PlanRepository planRepo;

    @Autowired
    protected RoleRepository roleRepo;

    @Autowired
    protected UserRepository userRepo;

    protected User createUser(String name, String email) {
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

    protected User createUserFromTestName(TestName testName) {
        String userName = testName.getMethodName();
        String email = testName.getMethodName() + "@blabslbas.com";
        return createUser(userName, email);
    }
}
