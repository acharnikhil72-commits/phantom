package Phanthom.example.Aarthrakshak.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String archetype;
    private Double averageSpend;
    private Integer healthScore;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.healthScore == null)
            this.healthScore = 50;
        if (this.averageSpend == null)
            this.averageSpend = 0.0;
    }
}
