package Phanthom.example.Aarthrakshak.controller;

import Phanthom.example.Aarthrakshak.model.Budget;
import Phanthom.example.Aarthrakshak.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/budget")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    // GET /api/budget/{userId}
    @GetMapping("/{userId}")
    public ResponseEntity<List<Budget>> getBudgets(
            @PathVariable Long userId) {
        return ResponseEntity.ok(budgetService.getUserBudgets(userId));
    }

    // POST /api/budget/set
    @PostMapping("/set")
    public ResponseEntity<Budget> setBudget(
            @RequestBody Map<String, Object> body) {

        Long userId = Long.valueOf(body.get("userId").toString());
        String category = body.get("category").toString();
        Double limit = Double.valueOf(body.get("limit").toString());

        return ResponseEntity.ok(
                budgetService.setBudget(userId, category, limit));
    }
}
