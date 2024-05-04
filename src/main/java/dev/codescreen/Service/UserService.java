package dev.codescreen.Service;

import dev.codescreen.Entity.*;
import dev.codescreen.Repository.TransactionRepository;
import dev.codescreen.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    private final Set<String> processedMessageIds = new HashSet<>(); // In-memory store for processed message IDs

    public UserService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    // Method for testing purposes only
    public User createUser(String userId, double initialBalance) {
        User user = new User();
        user.setId(userId);
        user.setBalance(initialBalance);
        return userRepository.save(user);
    }

    @Transactional
    public LoadResponse loadFunds(LoadRequest request) {
        if (processedMessageIds.contains((request.getMessageId()))) {
            return null;
        }
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        // Parse the amount from the request
        double amount = Double.parseDouble(request.getTransactionAmount().getAmount());
        ResponseCode status;

        if (amount >= 0) {
            // Update the user's balance
            user.setBalance(user.getBalance() + amount);

            // Save the updated user
            userRepository.save(user);
            status = ResponseCode.APPROVED;
        } else {
            status = ResponseCode.DECLIEND;
        }

        // Create a new transaction
        Transaction transaction = new Transaction(UUID.randomUUID().toString(), request.getUserId(), amount, DebitCredit.CREDIT , new Date(), status);

        // Save the transaction
        transactionRepository.save(transaction);

        // After processing the request, add its messageId to the set of processed message IDs
        processedMessageIds.add(request.getMessageId());

        // Create a LoadResponse with the updated balance
        Amount balance = new Amount(String.format("%.2f", user.getBalance()), request.getTransactionAmount().getCurrency(), DebitCredit.CREDIT);

        return new LoadResponse(request.getUserId(), request.getMessageId(), balance);
    }
    @Transactional
    public AuthorizationResponse authorizeTransaction(AuthorizationRequest request) {
        if (processedMessageIds.contains(request.getMessageId())) {
            return null;
        }
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        double amount = Double.parseDouble(request.getTransactionAmount().getAmount());
        ResponseCode status;
        // Check if the user has sufficient funds
        if (user.getBalance() < amount) {
            status = ResponseCode.DECLIEND;
        } else {
            status = ResponseCode.APPROVED;
        }


        if (status == ResponseCode.APPROVED){
            // Deduct the amount from the user's balance
            user.setBalance(user.getBalance() - amount);

            // Save the updated user
            userRepository.save(user);
        }

        // Create a new transaction
        Transaction transaction = new Transaction(UUID.randomUUID().toString(), request.getUserId(), amount, DebitCredit.DEBIT, new Date(), status);

        // Save the transaction
        transactionRepository.save(transaction);

        // After processing the request, add its messageId to the set of processed message IDs
        processedMessageIds.add(request.getMessageId());

        Amount balance = new Amount(String.format("%.2f", user.getBalance()), request.getTransactionAmount().getCurrency(), DebitCredit.DEBIT);
        return new AuthorizationResponse(request.getUserId(), request.getMessageId(), status, balance);
    }

}
