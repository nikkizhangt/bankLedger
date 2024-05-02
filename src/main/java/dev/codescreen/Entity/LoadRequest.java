package dev.codescreen.Entity;

import lombok.Data;

@Data
public class LoadRequest {
    private String userId;
    private String messageId;
    private Amount transactionAmount;

    public LoadRequest (String userId, String messageId, Amount transactionAmount) {
        this.userId = userId;
        this.messageId = messageId;
        this.transactionAmount = transactionAmount;
    }
    public LoadRequest () {}

}
