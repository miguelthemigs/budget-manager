package org.example.budgetmanager.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Expense {
    private Long id;
    private String category;
    private String description; // optional
    private double amount;
    private String date;
    private Long userId;



}
