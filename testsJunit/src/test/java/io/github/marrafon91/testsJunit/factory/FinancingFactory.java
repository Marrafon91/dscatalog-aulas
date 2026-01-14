package io.github.marrafon91.testsJunit.factory;

import io.github.marrafon91.testsJunit.entities.Financing;

public class FinancingFactory {

    public static Financing financingOne() {
      return new Financing(100000.0, 2000.0, 20);
    }

    public static Financing financingTwo() {
      return new Financing(100000.0, 2000.0, 80);
    }
}
