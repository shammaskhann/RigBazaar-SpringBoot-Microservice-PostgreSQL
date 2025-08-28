package com.example.rigbazaar.RigBazaar.controllers;


import com.example.rigbazaar.RigBazaar.entities.UserEntity;
import com.example.rigbazaar.RigBazaar.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/get-all-users")
    ResponseEntity<?> getAllUsers(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String search) {

        try {
            UserEntity user = userService.findByEmail(userDetails.getUsername());
            if (user == null) {
                return ResponseEntity.badRequest().body(Map.of("status", false, "message", "User not found"));
            }

            // Get all users except current user
            List<UserEntity> allUsersFetched = userService.getAllUsers()
                    .stream()
                    .filter(u -> !u.getId().equals(user.getId()))
                    .collect(Collectors.toList());



            return ResponseEntity.ok(Map.of("status", true, "users", allUsersFetched));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("status", false, "message", e.getMessage()));
        }
    }


}
