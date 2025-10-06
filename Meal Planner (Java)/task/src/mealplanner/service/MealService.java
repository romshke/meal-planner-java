package mealplanner.service;

import java.util.Scanner;

public interface MealService {
    void addMeal(Scanner scanner);

    void showMealsByCategoryWithIngredients(Scanner scanner);
}
