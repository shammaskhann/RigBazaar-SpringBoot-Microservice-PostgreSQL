package com.example.rigbazaar.RigBazaar.entities.Forums;

import com.example.rigbazaar.RigBazaar.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "forum_comment")
public class ForumComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long comment_id;   // PK

    // FK -> forum_thread.thread_id
    @ManyToOne
    @JoinColumn(name = "thread_id", nullable = false)
    private ForumThread thread;

    // FK -> users.user_id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime created_at;
}
