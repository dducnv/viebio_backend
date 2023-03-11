package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;

@NoArgsConstructor
@Data
@Transactional
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    @Column(name = "rating_avg")
    private float ratingAvg;
    @Column(name = "rating_count")
    private int ratingCount;
    private String image;
    private String url;
    @ManyToOne(targetEntity = User.class)
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(targetEntity = Category.class)
    @JsonIgnore
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
