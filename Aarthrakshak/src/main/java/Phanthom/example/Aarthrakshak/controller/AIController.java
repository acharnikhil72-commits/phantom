package Phanthom.example.Aarthrakshak.controller;

import Phanthom.example.Aarthrakshak.service.*;
import Phanthom.example.Aarthrakshak.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;
    private final UserService userService;
    private final TransactionRepository transactionRepository;

    // GET /api/ai/health-score/{userId}
    @GetMapping("/health-score/{userId}")
    public ResponseEntity<Map<String, String>> getHealthScore(
            @PathVariable Long userId) {

        var user = userService.getUser(userId);
        var txns = transactionRepository.findByUserId(userId);

        String summary = "Total transactions: " + txns.size() +
                ", Average spend: ₹" + user.getAverageSpend();

        String result = aiService.getHealthScore(
                userId, user.getArchetype(), summary);
        return ResponseEntity.ok(Map.of("result", result));
    }

    // POST /api/ai/whatif
    @PostMapping("/whatif")
    public ResponseEntity<Map<String, String>> whatIf(
            @RequestBody Map<String, Object> body) {

        Double goal = Double.valueOf(body.get("savingsGoal").toString());
        Integer months = Integer.valueOf(body.get("timelineMonths").toString());
        Long userId = Long.valueOf(body.get("userId").toString());
        var user = userService.getUser(userId);

        String result = aiService.getWhatIfPlan(
                goal, months, user.getAverageSpend());
        return ResponseEntity.ok(Map.of("result", result));
    }
}