package mealplanner.service;

import mealplanner.model.PlanOption;

import java.util.List;
import java.util.Scanner;

public interface PlanService {
    List<PlanOption> getPlan();
    void addPlan(Scanner scanner);
    void showPlan();
    void saveShoppingList(Scanner scanner);
}
