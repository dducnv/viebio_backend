package com.example.backend.entity;

import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;

@NoArgsConstructor
@Data
@Transactional
@Entity
@Table(name = "buttons")
public class Button {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String styles;
}
