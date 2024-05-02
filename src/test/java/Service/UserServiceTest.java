package Service;

import dev.codescreen.Entity.*;
import dev.codescreen.Repository.TransactionRepository;
import dev.codescreen.Repository.UserRepository;
import dev.codescreen.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private UserService userService;

    @Test
    public void testLoadFunds() {
        // Arrange
        String userId = "1";
        User user = new User();
        user.setId(userId);
        user.setBalance(0.00);


        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        double amountDouble = 100.00;
        String currency = "USD";
        DebitCredit debitOrCredit = DebitCredit.CREDIT; // Replace with your actual logic

        // Convert the double to a string
        String amountString = String.format("%.2f", amountDouble);
        // Create a new Amount object
        Amount amount = new Amount(amountString, currency, debitOrCredit);
        LoadRequest request = new LoadRequest();
        request.setUserId(userId);
        request.setTransactionAmount(amount);

        // Act
        LoadResponse response = userService.loadFunds(request);

        // Assert
        assertEquals(amount, response.getBalance());


        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
    }
}