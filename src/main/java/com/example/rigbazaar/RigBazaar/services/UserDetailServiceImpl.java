package com.example.rigbazaar.RigBazaar.services;

import com.example.rigbazaar.RigBazaar.entities.UserEntity;
import com.example.rigbazaar.RigBazaar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmail(username);
        if(user.isPresent()){
            UserEntity userEntity = user.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userEntity.getEmail())  // Use email as the username
                    .password(userEntity.getPassword())  // Password remains the same
                    .roles("CUSTOMER")
                    .build();

        }
        else{
            throw new UsernameNotFoundException("User not found");
        }
    }
}

