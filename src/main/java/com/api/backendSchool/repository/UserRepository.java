package com.api.backendSchool.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.backendSchool.model.User;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	Optional<User> findByEmail(String Email);
	Boolean existsByEmail(String email);
}
