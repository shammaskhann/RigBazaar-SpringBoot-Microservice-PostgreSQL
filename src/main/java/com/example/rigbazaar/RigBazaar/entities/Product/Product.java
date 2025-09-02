package com.example.rigbazaar.RigBazaar.entities.Product;

import com.example.rigbazaar.RigBazaar.constants.Condition;
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
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;   // PK (BIGSERIAL in Postgres)

    // FK -> users.user_id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // FK -> categories.category_id
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Enumerated(EnumType.STRING) // stores enum as text
    @Column(name = "condition")
    private Condition condition;

    @Column(name = "location")
    private String location;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
