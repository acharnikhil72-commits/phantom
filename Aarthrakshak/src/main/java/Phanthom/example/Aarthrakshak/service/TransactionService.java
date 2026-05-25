package Phanthom.example.Aarthrakshak.service;

import Phanthom.example.Aarthrakshak.model.Transaction;
import Phanthom.example.Aarthrakshak.repository.TransactionRepository;
import Phanthom.example.Aarthrakshak.service.FraudDetectionService.FraudResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final FraudDetectionService fraudDetectionService;
    private final UserService userService;

    public Transaction addTransaction(
            Long userId, String merchant, Double amount,
            String category, String ipAddress, Boolean isNewDevice) {
        var user = userService.getUser(userId);

        FraudResult result = fraudDetectionService.checkFraud(
                ipAddress, amount, user.getAverageSpend(), isNewDevice);

        Transaction txn = Transaction.builder()
                .userId(userId)
                .merchant(merchant)
                .amount(amount)
                .category(category)
                .ipAddress(ipAddress)
                .ipCountry(result.getCountry())
                .isNewDevice(isNewDevice)
                .status(result.getStatus())
                .fraudReason(String.join(", ", result.getReasons()))
                .build();

        if (!result.isFraud()) {
            userService.updateAverageSpend(userId, amount);
        }

        return transactionRepository.save(txn);
    }

    public List<Transaction> getUserTransactions(Long userId) {
        return transactionRepository
                .findByUserIdOrderByTransactionTimeDesc(userId);
    }
}