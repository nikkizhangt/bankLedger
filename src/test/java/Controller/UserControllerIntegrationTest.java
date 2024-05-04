package Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.codescreen.Application;
import dev.codescreen.Entity.*;
import dev.codescreen.Repository.UserRepository;
import dev.codescreen.Service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes= Application.class)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPing() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ping"))
                .andExpect(status().isOk());
    }
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void testLoad() throws Exception {
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

        mockMvc.perform(put("/load")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAuthorization() throws Exception {
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

        // Act and Assert
        mockMvc.perform(put("/authorization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }



}
