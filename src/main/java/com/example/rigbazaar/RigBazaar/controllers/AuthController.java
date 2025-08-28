package com.example.rigbazaar.RigBazaar.controllers;

import com.example.rigbazaar.RigBazaar.entities.UserCredentials;
import com.example.rigbazaar.RigBazaar.entities.UserEntity;
import com.example.rigbazaar.RigBazaar.repositories.MongoDBGridFsRepository;
import com.example.rigbazaar.RigBazaar.services.UserService;
import com.example.rigbazaar.RigBazaar.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;// User Service to get info and details
    @Autowired
    private AuthenticationManager authenticationManager;  // For Authentication the Jwt auth Tokens
    @Autowired
    private JwtUtil jwtUtil; // JWT Auth token Main Service
    @Autowired
    private MongoDBGridFsRepository gridFsRepository; // repo (Mongo for storing the image

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> loginPost(@RequestBody UserCredentials userCredentials) {
        if (userCredentials.getEmail() == null || userCredentials.getPassword() == null) {
            return new ResponseEntity<>(Map.of("status", false, "message", "Email or password is required"), HttpStatus.BAD_REQUEST);
        }
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.getEmail(), userCredentials.getPassword()));
            final UserEntity userEntity = userService.findByEmail(userCredentials.getEmail());


            if(userEntity == null) {
                return new ResponseEntity<>(Map.of("status", false, "message", "Wrong Email or Password "), HttpStatus.NOT_FOUND);
            }else{
                String jwtToken = jwtUtil.generateToken(userEntity.getEmail());
                return new ResponseEntity<>(Map.of("status", true, "data",Map.of(
                        "token", jwtToken,
                        "user", userEntity
                )), HttpStatus.OK);
            }
        }catch (AuthenticationException e){
            log.error("Exception occurred while createAuthenticationToken", e);
            return new ResponseEntity<>(Map.of(
                    "status", false,
                    "message", "Invalid email or password"
            ), HttpStatus.UNAUTHORIZED);
        }
    }

@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<?> registerPost(
        @RequestParam("username") String username,
        @RequestParam("email") String email,
        @RequestParam("password") String password,
        @RequestPart(value = "profilePic", required = false) MultipartFile profilePic
) {
    List<String> errors = new ArrayList<>();
    if (email.isEmpty()) errors.add("email is required");
    if (password.isEmpty()) errors.add("password is required");
    if (username.isEmpty()) errors.add("username is required");
    //check for file size and restrict to 5MB
    if (profilePic != null && profilePic.getSize() > 5 * 1024 * 1024) {
        errors.add("Profile picture size should not exceed 5MB");
    }
    if (!errors.isEmpty())
        return ResponseEntity.badRequest().body(Map.of("status", false, "message", errors));

    try {
        if (userService.checkEmailExists(email)) {
            return ResponseEntity.badRequest().body(Map.of("status", false, "message", "Email already exists"));
        }

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        if (profilePic != null && !profilePic.isEmpty()) {
            String imageId = gridFsRepository.uploadToGridFs(profilePic);
            String imageUrl = "http://localhost:8080/api/files/profile/" + imageId;

            user.setProfilePicUrl(imageUrl); // Save GridFS file ID
        }
        userService.register(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("status", true, "data", user));

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("status", false, "message", "Error occurred while creating user"));
        }
    }
}

