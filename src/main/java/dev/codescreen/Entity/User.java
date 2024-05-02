package dev.codescreen.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    private String id;
    private double balance;

    public User(String id, double balance) {
        this.id = id;
        this.balance = balance;

    }
    public User() {

    }

}


