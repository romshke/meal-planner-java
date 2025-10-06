package mealplanner.dao;

import mealplanner.model.Ingredient;
import mealplanner.model.Meal;
import mealplanner.model.MealCategory;

import java.util.List;

public interface MealDao {
    Meal findMealById(int mealId);
    List<Meal> findMealsByCategory(MealCategory category);
    List<Meal> findMealsByCategoryOrderByName(MealCategory category);
    List<Ingredient> findIngredientsByMealId(int mealId);
    void add(Meal meal);
    void addIngredients(Meal meal, List<Ingredient> ingredients);
}
