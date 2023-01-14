package com.tass.userservice.repositories;

import com.tass.common.model.myenums.UserStatus;
import com.tass.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {

    Optional<User> findByUsernameAndStatus(String username, UserStatus status);

    Optional<User> findByIdAndStatus(Long id, UserStatus status);
}
