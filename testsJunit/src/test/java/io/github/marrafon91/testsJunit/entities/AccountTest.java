package io.github.marrafon91.testsJunit.entities;

import io.github.marrafon91.testsJunit.factory.AccountFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {

    @Test
    public void depositShouldIncreaseBalanceWhenPositiveAmount() {
        double amount = 200.0;
        double expectedValue = 196.0;
        Account acc = AccountFactory.createEmptyAccount();

        acc.deposit(amount);

        assertEquals(expectedValue, acc.getBalance());
    }

    @Test
    void depositShouldDoNothingWhenNegativeAmount() {

        double expectedValue = 100.0;
        Account acc = AccountFactory.createAccount(expectedValue);
        double amount = -200.0;

        acc.deposit(amount);

        assertEquals(expectedValue, acc.getBalance());
    }
}
