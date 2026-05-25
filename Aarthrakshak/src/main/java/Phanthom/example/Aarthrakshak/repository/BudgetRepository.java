package Phanthom.example.Aarthrakshak.repository;

import Phanthom.example.Aarthrakshak.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository
        extends JpaRepository<Budget, Long> {
    List<Budget> findByUserId(Long userId);

    Optional<Budget> findByUserIdAndCategoryAndMonthAndYear(
            Long userId, String category, Integer month, Integer year);
}
