package org.example.budgetmanager.service;

import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.Currency;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EnumsServiceImpl {

    public List<Category> getAllCategories() {
        return Arrays.asList(Category.values());
    }

    public List<Currency> getAllCurrencies() {
        return Arrays.asList(Currency.values());
    }

}
