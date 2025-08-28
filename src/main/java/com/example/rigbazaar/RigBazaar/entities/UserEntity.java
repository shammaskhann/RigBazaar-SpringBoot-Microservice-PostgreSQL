package com.example.rigbazaar.RigBazaar.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "users")
public class UserEntity implements Serializable {
    @Id
    private String id;
    //MongoDB Unique constraint
    @Indexed(unique = true)
    @NonNull
    private String username;
    @NonNull
    private String password;
    @Indexed(unique = true)
    private String email;
    private String profilePicUrl;
    private String deviceToken;
//    private List<FriendRequest> friendRequests = new ArrayList<>();
//    private List<FriendEntity> friends = new ArrayList<>();
//    private List<String> blockedUsersId = new ArrayList<>();
}
