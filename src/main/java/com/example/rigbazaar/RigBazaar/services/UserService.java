package com.example.rigbazaar.RigBazaar.services;


import com.example.rigbazaar.RigBazaar.controllers.RoomController;
import com.example.rigbazaar.RigBazaar.entities.UserCredentials;
import com.example.rigbazaar.RigBazaar.entities.UserEntity;
import com.example.rigbazaar.RigBazaar.repositories.UserRepository;
import com.example.rigbazaar.RigBazaar.repositories.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@CacheConfig(cacheNames = "users")
public class UserService {

    @Autowired
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    public void register(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }

    public UserEntity login(UserCredentials userCredentials) {

        userCredentials.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
        return userRepository.findByEmailAndPassword(userCredentials.getEmail(), userCredentials.getPassword());
    }



    @Cacheable(key = "#id.toString()")
    public Optional<UserEntity> getUserById(String id) {
        return userRepository.findById(id);
    }

    public boolean checkEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    //@Cacheable(key = "#email")
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Cacheable(value = "usersCache", key = "'users'")
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}

