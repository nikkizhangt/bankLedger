package Service;

import dev.codescreen.Controller.UserController;
import dev.codescreen.Entity.*;
import dev.codescreen.Repository.UserRepository;
import dev.codescreen.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@@SpringBootTest(classes= UserController.class)
//@Import(UserService.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testLoadFunds() {
        // Arrange
        String userId = "1";
        User user = new User();
        user.setId(userId);
        user.setBalance(0.00);
        userRepository.save(user);

        double amountDouble = 100.00;
        String currency = "USD";
        DebitCredit debitOrCredit = DebitCredit.CREDIT;

        String amountString = String.format("%.2f", amountDouble);
        Amount amount = new Amount(amountString, currency, debitOrCredit);
        LoadRequest request = new LoadRequest();
        request.setUserId(userId);
        request.setTransactionAmount(amount);

        // Act
        LoadResponse response = userService.loadFunds(request);

        // Assert
        assertEquals(amount, response.getBalance());
        Optional<User> updatedUser = userRepository.findById(userId);
        assertTrue(updatedUser.isPresent());
        assertEquals(amount.getAmount(), Double.toString(updatedUser.get().getBalance()));
    }
}
