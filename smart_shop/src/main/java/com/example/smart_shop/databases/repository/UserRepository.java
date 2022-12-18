package com.example.smart_shop.databases.repository;

import com.example.smart_shop.databases.entities.User;
import com.example.smart_shop.databases.myenum.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsernameAndStatus(String username, UserStatus status);
    Optional<User> findByIdAndStatus(Long id, UserStatus status);


}
