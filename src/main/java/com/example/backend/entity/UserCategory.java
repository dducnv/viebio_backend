package com.example.backend.entity;

import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;

@NoArgsConstructor
@Data
@Transactional
@Entity
@Table(name = "user_categories")
public class UserCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
