package com.example.backend.entity;

import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;

@NoArgsConstructor
@Data
@Transactional
@Entity
@Table(name = "background")
public class Background {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    @Column(name = "primary_color")
    private String primaryColor;
    @Column(name = "textcolor")
    private String textColor;
}
