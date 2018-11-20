package com.eumin.poeaa.transaction_script.util;

import java.math.BigDecimal;

public class Money {
    private BigDecimal amount;

    public static Money dollars(BigDecimal amount) {
        return new Money(amount);
    }

    public static Money dollars(double amount) {
        return new Money(new BigDecimal(amount));
    }

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public Money add(Money money) {
        return Money.dollars(amount.doubleValue() + money.amount().doubleValue());
    }

    public Money[] allocate(int count) {
        Money[] allocation = new Money[count];
        double diviedAmount = amount.doubleValue() / count;
        for (int i = 0; i < count ; i++) {
            allocation[i] = Money.dollars(diviedAmount);
        }
        return allocation;
    }

    public BigDecimal amount() {
        return amount;
    }
}
