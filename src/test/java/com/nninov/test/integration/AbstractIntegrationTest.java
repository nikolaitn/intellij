package com.nninov.test.integration;

import com.devopsbuddy.backend.persistence.domain.backend.*;
import com.devopsbuddy.backend.persistence.repositories.PasswordResetTokenRepository;
import com.devopsbuddy.backend.persistence.repositories.PlanRepository;
import com.devopsbuddy.backend.persistence.repositories.RoleRepository;
import com.devopsbuddy.backend.persistence.repositories.UserRepository;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import com.devopsbuddy.utils.UserUtils;
import org.junit.Assert;
import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public abstract  class AbstractIntegrationTest {
    @Autowired
    protected PlanRepository planRepo;

    @Autowired
    protected RoleRepository roleRepo;

    @Autowired
    protected UserRepository userRepo;

    @Autowired
    protected PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${token.expiration.length.minutes}")
    protected int expirationTimeInMinutes;

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


    protected PasswordResetToken createPasswordResetToken(String token, User user, LocalDateTime now) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, now, expirationTimeInMinutes);
        passwordResetTokenRepository.save(passwordResetToken);
        Assert.assertNotNull(passwordResetToken.getId());
        return passwordResetToken;

    }
}
