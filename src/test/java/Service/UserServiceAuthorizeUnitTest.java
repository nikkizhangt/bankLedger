package Service;

import dev.codescreen.Entity.*;
import dev.codescreen.Repository.TransactionRepository;
import dev.codescreen.Repository.UserRepository;
import dev.codescreen.Service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceAuthorizeUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private UserService userService;

    // Test the AuthorizeTransaction method in a scenario where the user exists and the authorization is successful.
    @Test
    public void testAuthorizeTransaction() {
        // Arrange test 1
        String userId = "1";
        User user = new User();
        double balance = 103.23;
        user.setId(userId);
        user.setBalance(balance);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        String messageId = "3";
        double amountDouble = 100.00;
        String currency = "USD";
        DebitCredit debitOrCredit = DebitCredit.DEBIT;

        // Convert the double to a string
        String amountString = String.format("%.2f", amountDouble);
        // Create a new Amount object
        Amount amount = new Amount(amountString, currency, debitOrCredit);

        AuthorizationRequest request = new AuthorizationRequest(userId, messageId, amount);

        // Act
        AuthorizationResponse response = userService.authorizeTransaction(request);

        // Assert
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
        verify(transactionRepository, times(1)).save(any(Transaction.class));


        // Assert the response
        assertEquals(userId, response.getUserId());
        assertEquals(messageId, response.getMessageId());
        assertEquals(ResponseCode.APPROVED, response.getResponseCode());
        assertEquals(String.format("%.2f", balance - amountDouble), response.getBalance().getAmount());
    }

    // Test the AuthorizeTransaction method in a scenario where the Transaction amount is greater than user's current balance and the transaction should be declined.
    @Test
    public void testAuthorizeTransactionFailed() {
        // Arrange test 1
        String userId = "1";
        User user = new User();
        double balance = 3.23;
        user.setId(userId);
        user.setBalance(balance);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        String messageId = "3";
        double amountDouble = 10.00;
        String currency = "USD";
        DebitCredit debitOrCredit = DebitCredit.DEBIT;

        // Convert the double to a string
        String amountString = String.format("%.2f", amountDouble);
        // Create a new Amount object
        Amount amount = new Amount(amountString, currency, debitOrCredit);

        AuthorizationRequest request = new AuthorizationRequest(userId, messageId, amount);

        // Act
        AuthorizationResponse response = userService.authorizeTransaction(request);

        // Assert
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(0)).save(any(User.class));
        verify(transactionRepository, times(1)).save(any(Transaction.class));


        // Assert the response
        assertEquals(userId, response.getUserId());
        assertEquals(messageId, response.getMessageId());
        assertEquals(ResponseCode.DECLIEND, response.getResponseCode());
        assertEquals(String.format("%.2f", balance), response.getBalance().getAmount());

    }




}
