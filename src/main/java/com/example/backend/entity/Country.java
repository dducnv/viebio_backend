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
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonIgnore
    @Column(name = "country_code",unique = true)
    private String countryCode;
    @OneToMany(mappedBy = "country")
    @JsonIgnore
    private Set<StatsViewPage> statsViewPages = new HashSet<>();
}
