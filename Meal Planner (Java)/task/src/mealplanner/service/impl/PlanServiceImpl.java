package mealplanner.service.impl;

import mealplanner.dao.MealDao;
import mealplanner.dao.PlanDao;
import mealplanner.model.Day;
import mealplanner.model.Meal;
import mealplanner.model.MealCategory;
import mealplanner.model.PlanOption;
import mealplanner.service.PlanService;

import java.util.List;
import java.util.Scanner;

public class PlanServiceImpl implements PlanService {
    private final PlanDao planDao;
    private final MealDao mealDao;

    public PlanServiceImpl(PlanDao planDao, MealDao mealDao) {
        this.planDao = planDao;
        this.mealDao = mealDao;
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
        List<PlanOption> plan = planDao.getPlan();

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
