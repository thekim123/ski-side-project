package com.project.ski.repository;

import com.project.ski.domain.user.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    @EntityGraph(attributePaths = {"roles"})
    Users findByUsername(String username);

    @EntityGraph(attributePaths = {"roles"})
    @Override
    Optional<Users> findById(Long id);
}
