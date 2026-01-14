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

    @Test
    void setTotalAmountShouldUpdateAmountWhenValid() {
        Financing financing = new Financing(100000.0,2000.0,80);
        financing.setTotalAmount(90000.0);
        assertEquals(90000.0, financing.getTotalAmount());
    }

    @Test
    void setTotalAmountShouldThrowIllegalArgumentExceptionWhenInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
        Financing financing = new Financing(100000.0,2000.0,80);
        financing.setTotalAmount(1100000.0);
        assertEquals(1100000.0, financing.getTotalAmount());
        });
    }

    @Test
    void setIncomeShouldUpdateWhenValid() {
        Financing financing = new Financing(100000.0,2000.0,80);
        financing.setIncome(3000.0);
        assertEquals(3000.0, financing.getIncome());
    }

    @Test
    void setIncomeShouldThrowIllegalArgumentExceptionWhenInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            Financing financing = new Financing(100000.0,2000.0,80);
            financing.setIncome(1500.0);
            assertEquals(1500.0, financing.getIncome());
        });
    }

    @Test
    void setMonthsShouldUpdateWhenValid() {
        Financing financing = new Financing(100000.0,2000.0,80);
        financing.setMonths(100);
        assertEquals(100, financing.getMonths());
    }

    @Test
    void setMonthsShouldThrowIllegalArgumentExceptionWhenInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            Financing financing = new Financing(100000.0,2000.0,80);
            financing.setMonths(70);
            assertEquals(70, financing.getMonths());
        });
    }
}

