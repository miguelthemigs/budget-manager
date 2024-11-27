package org.example.budgetmanager.service.impl;

import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@ActiveProfiles("test")
class EnumsServiceImplTest {

    private EnumsServiceImpl enumsService;

    @BeforeEach
    void setUp() {
        enumsService = new EnumsServiceImpl();
    }

    @Test
    void getAllCategoriesReturnsAllCategories() {
        List<Category> expectedCategories = List.of(Category.values());
        List<Category> actualCategories = enumsService.getAllCategories();

        assertIterableEquals(expectedCategories, actualCategories, "getAllCategories should return all Category enums");
    }

    @Test
    void getAllCurrenciesReturnsAllCurrencies() {
        List<Currency> expectedCurrencies = List.of(Currency.values());
        List<Currency> actualCurrencies = enumsService.getAllCurrencies();

        assertIterableEquals(expectedCurrencies, actualCurrencies, "getAllCurrencies should return all Currency enums");
    }
}
