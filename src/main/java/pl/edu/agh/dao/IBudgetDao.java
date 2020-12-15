package pl.edu.agh.dao;

import pl.edu.agh.model.Budget;

import java.time.Month;
import java.util.List;

public interface IBudgetDao {
    void saveBudget(Budget budget);
    List<Budget> getBudgetsByYear(int year);
    Budget getBudgetByMonth(int year, Month month);
}
