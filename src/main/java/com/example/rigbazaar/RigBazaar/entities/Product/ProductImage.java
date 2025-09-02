package com.example.rigbazaar.RigBazaar.entities.Product;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product_image")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long image_id;  // Auto-increment PK in PostgreSQL

    // Foreign key to Product (assuming product table exists)
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "is_primary")
    private boolean isPrimary;
}
