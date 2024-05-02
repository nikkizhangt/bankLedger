package dev.codescreen.Entity;

import lombok.Data;

@Data
public class Amount {
    private String amount;
    private String currency;
    private DebitCredit debitOrCredit;
    public Amount(String amount, String currency, DebitCredit debitOrCredit) {
        this.amount = amount;
        this.currency = currency;
        this.debitOrCredit = debitOrCredit;
    }
    public Amount() {}
}
