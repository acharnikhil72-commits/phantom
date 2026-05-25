package Phanthom.example.Aarthrakshak.service;

import Phanthom.example.Aarthrakshak.model.Budget;
import Phanthom.example.Aarthrakshak.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public Budget setBudget(Long userId, String category, Double limit) {
        int month = LocalDateTime.now().getMonthValue();
        int year = LocalDateTime.now().getYear();

        Budget budget = budgetRepository
                .findByUserIdAndCategoryAndMonthAndYear(
                        userId, category, month, year)
                .orElse(Budget.builder()
                        .userId(userId)
                        .category(category)
                        .month(month)
                        .year(year)
                        .spentAmount(0.0)
                        .build());

        budget.setBudgetLimit(limit);
        return budgetRepository.save(budget);
    }

    public List<Budget> getUserBudgets(Long userId) {
        return budgetRepository.findByUserId(userId);
    }
}