package io.github.marrafon91.testsJunit.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FinancingTest {

    @Test
    void constructorShouldWorksWhenValid() {
        Financing financing = new Financing(100000.0,2000.0,80);

        double totalAmount = 100000.0;
        double income = 2000.0;
        int months = 80;

       assertEquals(totalAmount, financing.getTotalAmount());
       assertEquals(income, financing.getIncome());
       assertEquals(months, financing.getMonths());
    }

    @Test
    void constructorShouldThrowIllegalArgumentExceptionWhenInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new Financing(100000.0, 2000.0, 20)
        );
    }
}
