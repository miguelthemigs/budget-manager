package org.example.budgetmanager.service.impl;

import lombok.AllArgsConstructor;
import org.example.budgetmanager.repository.ExpensesRepository;
import org.example.budgetmanager.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // contains the business logic, interacts with the Repository.
public class ExpenseServiceImpl implements ExpenseService {


    private final ExpensesRepository expensesRepository;

    @Autowired // doing dependency injection
        public ExpenseServiceImpl(ExpensesRepository expensesRepository) {
            this.expensesRepository = expensesRepository;
        }
}
