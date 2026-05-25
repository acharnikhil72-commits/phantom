package Phanthom.example.Aarthrakshak.service;

import lombok.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FraudDetectionService {

    private final IPLocationService ipLocationService;

    public FraudResult checkFraud(
            String ipAddress,
            Double amount,
            Double averageSpend,
            Boolean isNewDevice) {
        List<String> reasons = new ArrayList<>();

        // CHECK 1: Foreign IP
        String country = ipLocationService.getCountry(ipAddress);
        if (!country.equalsIgnoreCase("India")) {
            reasons.add("Foreign IP detected from: " + country);
        }

        // CHECK 2: Time anomaly
        int hour = LocalDateTime.now().getHour();
        if (hour >= 1 && hour <= 4) {
            reasons.add("Unusual time: " + hour + ":00 AM");
        }

        // CHECK 3: Amount anomaly
        if (averageSpend > 0 && amount > averageSpend * 3) {
            reasons.add("Amount ₹" + amount +
                    " is 3x your average spend of ₹" + averageSpend);
        }

        // CHECK 4: New device + high amount
        if (Boolean.TRUE.equals(isNewDevice) && amount > 5000) {
            reasons.add("New device with high amount ₹" + amount);
        }

        boolean isFraud = !reasons.isEmpty();
        String status = isFraud ? "BLOCKED" : "SUCCESS";

        return new FraudResult(isFraud, reasons, status, country);
    }

    @Data
    @AllArgsConstructor
    public static class FraudResult {
        private boolean fraud;
        private List<String> reasons;
        private String status;
        private String country;
    }
}