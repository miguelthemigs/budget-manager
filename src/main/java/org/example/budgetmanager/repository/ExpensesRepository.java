package org.example.budgetmanager.repository;

import org.example.budgetmanager.model.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpensesRepository  {
    // extends JpaRepository<Expense, Long>
    /**
     * Find expenses by a specific category.
     *
     * @param category the category of the expenses.
     * @return a list of expenses in the given category.
     */
    //List<Expense> findByCategory(String category);

    /**
     * Find expenses for a specific user.
     *
     * @param userId the ID of the user.
     * @return a list of expenses for the given user.
     */
    //List<Expense> findByUserId(Long userId);

    /**
     * Find expenses within a specific date range.
     *
     * @param startDate the start date of the range.
     * @param endDate   the end date of the range.
     * @return a list of expenses within the given date range.
     */
    //List<Expense> findByDateBetween(String startDate, String endDate);

    /**
     * Find an expense by its ID.
     *
     * @param id the ID of the expense.
     * @return an Optional containing the expense if found, otherwise empty.
     */
    //Optional<Expense> findById(Long id);

    /**
     * Delete an expense by its ID.
     *
     * @param id the ID of the expense to be deleted.
     */
    //void deleteById(Long id);
}
