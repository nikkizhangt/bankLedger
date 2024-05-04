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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceLoadUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private UserService userService;

    // Test the loadFunds method in a scenario where the user exists and the load is successful.
    @Test
    public void testLoadFunds() {
        // Arrange
        String userId = "1";
        User user = new User();
        user.setId(userId);
//        double balance = 0.00;
        double balance = 100.00;

        user.setBalance(balance);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));


//        double amountDouble = 100.00;
//        double amountDouble = 3.23;
        double amountDouble = 50.01;

//        String messageId = "1";
        String messageId = "2";



        String currency = "USD";
        DebitCredit debitOrCredit = DebitCredit.CREDIT; // Replace with your actual logic

        // Convert the double to a string
        String amountString = String.format("%.2f", amountDouble);
        // Create a new Amount object
        Amount amount = new Amount(amountString, currency, debitOrCredit);
        LoadRequest request = new LoadRequest(userId, messageId, amount);


        // Act
        LoadResponse response = userService.loadFunds(request);

        // Assert
        assertEquals(balance + amountDouble, Double.parseDouble(response.getBalance().getAmount()));
        assertEquals(userId, response.getUserId());
        assertEquals(messageId, response.getMessageId());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);


    }
    // Assumption: The loadFunds method does not create a new account if the userId does not exist.
    // If findById returns null, a User not found exception should be thrown.
    // Test scenario where loadFunds is called with a non-existent userId.
    @Test
    public void testLoadFunds_UserNotFound() {
        // Arrange
        String userId = "5";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        String messageId = "";
        double amountDouble = 50.00;
        String currency = "USD";
        DebitCredit debitOrCredit = DebitCredit.CREDIT; // Replace with your actual logic

        // Convert the double to a string
        String amountString = String.format("%.2f", amountDouble);
        // Create a new Amount object
        Amount amount = new Amount(amountString, currency, debitOrCredit);

        LoadRequest request = new LoadRequest(userId, messageId, amount);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userService.loadFunds(request));
    }
}