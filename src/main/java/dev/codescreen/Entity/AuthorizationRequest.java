package dev.codescreen.Entity;

import lombok.Data;

@Data
public class AuthorizationRequest {
    private String userId;
    private String messageId;
    private Amount transactionAmount;

    public AuthorizationRequest(String userId, String messageId, Amount transactionAmount) {
        this.userId = userId;
        this.messageId = messageId;
        this.transactionAmount = transactionAmount;
    }
    public AuthorizationRequest() {

    }
}
