package Phanthom.example.Aarthrakshak.controller;

import Phanthom.example.Aarthrakshak.service.*;
import Phanthom.example.Aarthrakshak.service.FraudDetectionService.FraudResult;
import Phanthom.example.Aarthrakshak.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/fraud")
@RequiredArgsConstructor
public class FraudController {

    private final FraudDetectionService fraudDetectionService;
    private final AIService aiService;
    private final UserService userService;
    private final TransactionRepository transactionRepository;

    // POST /api/fraud/check
    @PostMapping("/check")
    public ResponseEntity<FraudResult> checkFraud(
            @RequestBody Map<String, Object> body) {

        Long userId = Long.valueOf(body.get("userId").toString());
        String ip = body.get("ipAddress").toString();
        Double amount = Double.valueOf(body.get("amount").toString());
        Boolean newDev = Boolean.valueOf(body.get("isNewDevice").toString());

        var user = userService.getUser(userId);
        var result = fraudDetectionService.checkFraud(
                ip, amount, user.getAverageSpend(), newDev);
        return ResponseEntity.ok(result);
    }

    // GET /api/fraud/explain/{transactionId}
    @GetMapping("/explain/{transactionId}")
    public ResponseEntity<Map<String, String>> explainFraud(
            @PathVariable Long transactionId) {

        var txn = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        String explanation = aiService.explainFraud(
                txn.getMerchant(),
                txn.getAmount(),
                java.util.List.of(txn.getFraudReason().split(", ")),
                txn.getIpCountry());
        return ResponseEntity.ok(Map.of("explanation", explanation));
    }
}