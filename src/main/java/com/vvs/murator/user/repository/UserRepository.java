package com.vvs.murator.user.repository;

import com.vvs.murator.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialUserId(String socialUserId);
}
