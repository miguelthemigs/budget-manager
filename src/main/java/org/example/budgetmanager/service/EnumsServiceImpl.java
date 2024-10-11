package org.example.budgetmanager.service;

import org.example.budgetmanager.model.Category;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EnumsServiceImpl {

    public List<Category> getAllCategories() {
        // Retrieve all categories from the repository
        return Arrays.asList(Category.values());
    }

}
