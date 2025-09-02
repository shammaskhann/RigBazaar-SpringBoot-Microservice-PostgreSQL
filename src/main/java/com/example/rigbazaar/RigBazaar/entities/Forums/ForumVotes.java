package com.example.rigbazaar.RigBazaar.entities.Forums;

import com.example.rigbazaar.RigBazaar.constants.VoteType;
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
@Table(name = "forum_vote")
public class ForumVotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long vote_id;   // PK

    // FK -> forum_thread.thread_id
    @ManyToOne
    @JoinColumn(name = "thread_id", nullable = false)
    private ForumThread thread;

    // FK -> users.user_id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING) // stores enum as text (UPVOTE/DOWNVOTE)
    @Column(name = "vote_type", nullable = false)
    private VoteType vote_type;

    @Column(name = "created_at")
    private LocalDateTime created_at;
}
