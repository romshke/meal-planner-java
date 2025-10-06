package mealplanner.service.impl;

import mealplanner.dao.MealDao;
import mealplanner.dao.PlanDao;
import mealplanner.model.Day;
import mealplanner.model.Meal;
import mealplanner.model.MealCategory;
import mealplanner.model.PlanOption;
import mealplanner.service.PlanService;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PlanServiceImpl implements PlanService {
    private final PlanDao planDao;
    private final MealDao mealDao;

    public PlanServiceImpl(PlanDao planDao, MealDao mealDao) {
        this.planDao = planDao;
        this.mealDao = mealDao;
    }

    @Override
    public List<PlanOption> getPlan() {
        return planDao.get();
    }

    @Override
    public void addPlan(Scanner scanner) {
        planDao.delete();

        for (Day day : Day.values()) {
            System.out.println(day);

            for (MealCategory category : MealCategory.values()) {
                List<Meal> meals = mealDao.findMealsByCategoryOrderByName(category);

                meals.forEach(meal ->
                    System.out.println(meal.getName())
                );

                System.out.printf("Choose the %s for %s from the list above:%n", category, day);

                Meal meal = selectMeal(scanner, meals);

                PlanOption planOption = new PlanOption();
                planOption.setDay(day);
                planOption.setCategory(category);
                planOption.setMealId(meal.getId());

                planDao.add(planOption);

            }

            System.out.printf("Yeah! We planned the meals for %s.%n", day);
        }
        System.out.println();
        showPlan();
    }

    @Override
    public void showPlan() {
        List<PlanOption> plan = getPlan();

        if (plan.isEmpty()) {
            System.out.println("Database does not contain any meal plans");
        } else {
            String currentDay = "";

            for (PlanOption planOption : plan) {
                if (!planOption.getDay().getName().equals(currentDay)) {
                    System.out.printf(currentDay.isBlank() ? "" : "%n");
                    currentDay = planOption.getDay().getName();
                    System.out.println(currentDay);
                }
                System.out.printf("%s: %s%n",
                        capitalize(planOption.getCategory().getName()),
                        mealDao.findMealById(planOption.getMealId()).getName()
                );
            }
        }
    }

    @Override
    public void saveShoppingList(Scanner scanner) {
        Map<String, Integer> shoppingList = planDao.getShoppingList();

        if (shoppingList.isEmpty()) {
            System.out.println("Unable to save. Plan your meals first.");
        } else {
            System.out.println("Input a filename:");
            String fileName = scanner.nextLine();

            File file = new File(fileName);

            try (PrintWriter printWriter = new PrintWriter(file)) {
                shoppingList.forEach((ingredient, count) -> {
                    if (count > 1) {
                        printWriter.printf("%s x%d%n", ingredient, count);
                    } else {
                        printWriter.println(ingredient);
                    }
                });
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println("Saved!");
        }
    }

    private Meal selectMeal(Scanner scanner, List<Meal> meals) {
        while (true) {
            String mealName = scanner.nextLine();

            for (Meal meal : meals) {
                if (meal.getName().equalsIgnoreCase(mealName)) {
                    return meal;
                }
            }

            System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
        }
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
