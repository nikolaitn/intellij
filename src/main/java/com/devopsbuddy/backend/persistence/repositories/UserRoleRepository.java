package com.devopsbuddy.backend.persistence.repositories;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

	Set<UserRole> findByUser(User targetUser);
}
