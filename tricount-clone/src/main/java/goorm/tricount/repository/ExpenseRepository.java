package goorm.tricount.repository;

import goorm.tricount.model.Expense;

public interface ExpenseRepository {
    Expense save(Expense expense);
}
