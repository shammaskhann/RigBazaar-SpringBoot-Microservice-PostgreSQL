package com.example.rigbazaar.RigBazaar.repositories;

import com.example.rigbazaar.RigBazaar.entities.UserEntity;

import org.springframework.data.jpa.repository.*;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUsername(String username);

    Optional<UserEntity> findById(String id);

    Optional<UserEntity> findByEmail(String email);

    UserEntity findByEmailAndPassword(String email, String password);


}