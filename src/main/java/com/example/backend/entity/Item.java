package com.example.backend.entity;

import com.example.backend.entity.my_enum.ItemType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@Transactional
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    @Column(nullable = false)
    private String name;
    @Column(name = "order_number")
    private Integer orderNumber;
    private String details;
    private ItemType type;
    @Column(name = "total_clicks")
    private Integer totalClicks;
    @Column(nullable = false)
    private String url;
    @ManyToOne
    @JoinColumn(name = "embed_id")
    private EmbedItem embedItem;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "created_at")
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;
}
