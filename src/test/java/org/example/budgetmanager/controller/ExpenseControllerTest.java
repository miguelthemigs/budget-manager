package org.example.budgetmanager.controller;

import org.example.budgetmanager.model.Expense;
import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // Disable security filters for tests
@ActiveProfiles("test")
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @Test
    void testGetExpensesForUser_Success() throws Exception {
        Expense expense = new Expense(1L, Category.RESTAURANTS, "Lunch", 12.5, LocalDate.now(), 1L);
        Mockito.when(expenseService.getExpensesForUser(1L))
                .thenReturn(Optional.of(Collections.singletonList(expense)));

        mockMvc.perform(get("/expenses?userId=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].category").value("RESTAURANTS"))
                .andExpect(jsonPath("$[0].amount").value(12.5));
    }

    @Test
    void testAddExpense_Success() throws Exception {
        Mockito.doNothing().when(expenseService).addExpense(any(Expense.class));

        mockMvc.perform(post("/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "category": "RESTAURANTS",
                                    "amount": 25.0,
                                    "date": "2024-11-25",
                                    "userId": 1
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void testDeleteExpense_Success() throws Exception {
        Mockito.doNothing().when(expenseService).deleteExpense(1L);

        mockMvc.perform(delete("/expenses/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateExpense_Success() throws Exception {
        Mockito.doNothing().when(expenseService).updateExpense(any(Expense.class));

        mockMvc.perform(put("/expenses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": 1,
                                    "category": "RESTAURANTS",
                                    "amount": 30.0,
                                    "date": "2024-11-25",
                                    "userId": 1
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void testGetExpensesTotalForSelectedMonth_Success() throws Exception {
        Mockito.when(expenseService.getTotalValueOfExpensesForSelectedMonth(1L, "2024-10"))
                .thenReturn(Optional.of(150.0));

        mockMvc.perform(get("/expenses/monthly?userId=1&month=2024-10"))
                .andExpect(status().isOk())
                .andExpect(content().string("150.0"));
    }
}
