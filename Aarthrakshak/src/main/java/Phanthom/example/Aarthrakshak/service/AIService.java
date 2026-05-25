package Phanthom.example.Aarthrakshak.service;

import Phanthom.example.Aarthrakshak.config.GroqClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AIService {

    private final GroqClient groqClient;  // 👈 only change here

    // WHY explainer
    public String explainFraud(String merchant, Double amount,
            List<String> reasons, String country) {
        String prompt = """
                A transaction was flagged as suspicious.
                Merchant: %s
                Amount: ₹%.2f
                From country: %s
                Reasons flagged: %s

                Explain to a 22 year old Indian user in 2-3 simple friendly
                sentences why this looks suspicious. Be clear, not scary.
                """.formatted(merchant, amount, country,
                String.join(", ", reasons));

        return groqClient.chat(prompt);  // 👈 only change here
    }

    // Health score
    public String getHealthScore(Long userId,
            String archetype,
            String spendingSummary) {
        String prompt = """
                Analyze this user's spending:
                Archetype: %s
                Spending summary: %s

                Return ONLY this JSON, nothing else:
                {
                  "score": <number 0-100>,
                  "tips": ["tip1", "tip2", "tip3"]
                }
                """.formatted(archetype, spendingSummary);

        return groqClient.chat(prompt);  // 👈 only change here
    }

    // What-if simulator
    public String getWhatIfPlan(Double savingsGoal,
            Integer months,
            Double monthlySpend) {
        String prompt = """
                User wants to save ₹%.2f in %d months.
                Current monthly spend: ₹%.2f

                Return ONLY this JSON, nothing else:
                {
                  "monthlyTarget": <number>,
                  "cutSuggestions": ["suggestion1", "suggestion2"],
                  "chartData": [<month1>, <month2>, ...]
                }
                """.formatted(savingsGoal, months, monthlySpend);

        return groqClient.chat(prompt);  // 👈 only change here
    }
}