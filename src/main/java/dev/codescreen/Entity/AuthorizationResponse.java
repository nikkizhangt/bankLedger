package dev.codescreen.Entity;

import lombok.Data;

@Data
public class AuthorizationResponse {
    private String userId;
    private String messageId;
    private ResponseCode responseCode;
    private Amount balance;
    public AuthorizationResponse (String userId, String messageId, ResponseCode responseCode, Amount balance) {
        this.userId = userId;
        this.messageId = messageId;
        this.responseCode = responseCode;
        this.balance = balance;
    }
    public AuthorizationResponse() {}

}
