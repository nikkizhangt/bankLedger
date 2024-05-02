package dev.codescreen.Entity;

import lombok.Data;

@Data
public class LoadResponse {
    private String userId;
    private String messageId;
    private Amount balance;
    public LoadResponse(String userId, String messageId, Amount balance) {
        this.userId = userId;
        this.messageId = messageId;
        this.balance = balance;
    }
    public LoadResponse() {}

}
