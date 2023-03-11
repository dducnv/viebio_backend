package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@Transactional
@Entity
@Table(name = "themes")
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String styles;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "theme_id")
    private Background background;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "button_id")
    private Button button;
    @OneToMany(targetEntity = User.class)
    @JsonIgnore
    private Set<User> users = new HashSet<>();

}
