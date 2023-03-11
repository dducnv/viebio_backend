package com.example.backend.entity;

import com.example.backend.entity.my_enum.UserStatus;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Transactional
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String avatar;
    @Column(nullable = false)
    private String name;
    private String bio;
    private boolean verify;
    private boolean emailVerify;
    @Column(unique = true,nullable = false)
    private String username;
    @JsonIgnore
    @Column(unique = true,nullable = false)
    private String email;
    @Column(name="email_public")
    private String emailPublic;
    @JsonIgnore
    private String providerId;
    @Column(name="on_time_password")
    @JsonIgnore
    private String oneTimePassword;
    @Column(name="expire_time")
    @JsonIgnore
    private Date expireTime;
    @CreationTimestamp
    @Column(name="created_at")
    @JsonIgnore
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name="updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;
    private UserStatus status;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Item> items = new HashSet<>();
    @ManyToOne(targetEntity = Theme.class)
    @JoinColumn(name = "theme_using_id")
    private Theme theme;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

}
