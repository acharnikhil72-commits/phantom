package Phanthom.example.Aarthrakshak.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String merchant;
    private Double amount;
    private String category;
    private String ipAddress;
    private String ipCountry;
    private Boolean isNewDevice;

    // SUCCESS / BLOCKED
    private String status;
    private String fraudReason;

    private LocalDateTime transactionTime;

    @PrePersist
    public void prePersist() {
        if (this.transactionTime == null)
            this.transactionTime = LocalDateTime.now();
        if (this.status == null)
            this.status = "PENDING";
    }
}