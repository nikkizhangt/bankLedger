package dev.codescreen.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Transaction {
    @Id
    private String id;
    private String userId;
    private double amount;
    private DebitCredit type;
    private Date timestamp;
    private ResponseCode status;


    public Transaction(String id, String userId, double amount, DebitCredit type, Date timestamp, ResponseCode status) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
        this.status = status;
    }
    public Transaction() {

    }
}
