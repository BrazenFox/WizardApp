package com.netcracker.wizardapp.repository;

import com.netcracker.wizardapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    Boolean existsByUsernameAndIdNot(String username, Long id);
    Boolean existsByUsername(String username);
}
