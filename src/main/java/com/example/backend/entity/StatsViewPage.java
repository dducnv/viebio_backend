package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@Transactional
@Entity
@Table(name = "stats_view_pages")
public class StatsViewPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "total_views")
    private int totalViews;
    @ManyToOne(targetEntity = Country.class)
    @JsonIgnore
    @JoinColumn(name = "country_id", nullable = false,referencedColumnName = "id")
    private Country country;
    @ManyToOne(targetEntity = User.class)
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false,referencedColumnName = "id")
    private User user;
    @CreationTimestamp
    private LocalDateTime dateTime;
}
