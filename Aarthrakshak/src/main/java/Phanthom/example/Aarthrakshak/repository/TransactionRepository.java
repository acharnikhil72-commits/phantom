package Phanthom.example.Aarthrakshak.repository;

import Phanthom.example.Aarthrakshak.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserIdOrderByTransactionTimeDesc(Long userId);

    List<Transaction> findByUserId(Long userId);
}