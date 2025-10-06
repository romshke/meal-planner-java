package mealplanner.service.impl;

import mealplanner.dao.MealDao;
import mealplanner.model.Ingredient;
import mealplanner.model.Meal;
import mealplanner.model.MealCategory;
import mealplanner.service.MealService;

import java.util.*;

public class MealServiceImpl implements MealService {
    private final MealDao mealDao;

    public MealServiceImpl(MealDao mealDao) {
        this.mealDao = mealDao;
    }

    @Override
    public void addMeal(Scanner scanner) {
        // выбрать категорию из списка
        MealCategory category = selectCategoryToAdd(scanner);

        // ввести название блюда
        String name = enterMealName(scanner);

        // ввести ингредиенты через запятую
        String[] ingredientsNames = enterIngredients(scanner);

        Meal meal = new Meal();
        meal.setCategory(category);
        meal.setName(name);

        List<Ingredient> ingredients = new ArrayList<>();

        for (String ingredientName : ingredientsNames) {
            Ingredient ingredient = new Ingredient();
            ingredient.setMealId(meal.getId());
            ingredient.setName(ingredientName);
            ingredients.add(ingredient);
        }

        mealDao.add(meal);
        mealDao.addIngredients(meal, ingredients);

        System.out.println("The meal has been added!");
    }

    @Override
    public void showMealsByCategoryWithIngredients(Scanner scanner) {
        // выбрать категорию из списка breakfast, lunch, dinner
        MealCategory category = selectCategoryToShow(scanner);

        // получить meals по выбранной категории
        List<Meal> meals = mealDao.findMealsByCategory(category);

        if (meals.isEmpty()) {
            System.out.println("No meals found.");
            return;
        }

        Map<Meal, List<Ingredient>> mealsWithIngredients = new LinkedHashMap<>();

        for (Meal meal : meals) {
            List<Ingredient> ingredients = mealDao.findIngredientsByMealId(meal.getId());
            mealsWithIngredients.put(meal, ingredients);
        }

        System.out.printf("Category: %s%n%n", category);

        mealsWithIngredients.forEach((meal, ingredients) ->
            System.out.println(mealWithIngredients(meal, mealsWithIngredients.get(meal)))
        );
    }

    private MealCategory selectCategoryToShow(Scanner scanner) {
        System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");

        String category;

        while (true) {
            category = scanner.nextLine();

            if (!MealCategory.contains(category)) {
                System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                continue;
            }

            break;
        }

        return MealCategory.fromString(category);
    }

    private String mealWithIngredients(Meal meal, List<Ingredient> ingredients) {
        StringBuilder result = new StringBuilder();

        result.append("Name: ").append(meal.getName()).append(System.lineSeparator())
                .append("Ingredients:").append(System.lineSeparator());

        for (Ingredient ingredient : ingredients) {
            result.append(ingredient.getName()).append(System.lineSeparator());
        }

        return result.toString();
    }

    private MealCategory selectCategoryToAdd(Scanner scanner) {
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");

        String category;

        while (true) {
            category = scanner.nextLine();

            if (!MealCategory.contains(category)) {
                System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                continue;
            }

            break;
        }

        return MealCategory.fromString(category);
    }

    private boolean isCorrectMealName(String name) {
        return name.matches("[a-zA-Z]+[a-zA-Z\\s]*");
    }

    static boolean isCorrectIngredientsList(String ingredients) {
        return ingredients.matches("[a-zA-Z]+[a-zA-Z\\s]*(,\\s*[a-zA-Z]+[a-zA-Z\\s]*)*");
    }

    private String enterMealName(Scanner scanner) {
        System.out.println("Input the meal's name:");

        while (true) {
            String name = scanner.nextLine();

            if (!isCorrectMealName(name)) {
                System.out.println("Wrong format. Use letters only!");
                continue;
            }

            return name;
        }
    }

    private String[] enterIngredients(Scanner scanner) {
        System.out.println("Input the ingredients:");

        while (true) {
            String ingredients = scanner.nextLine();

            if (!isCorrectIngredientsList(ingredients)) {
                System.out.println("Wrong format. Use letters only!");
                continue;
            }

            return ingredients.split(",\\s*");
        }
    }
}
