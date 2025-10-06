package mealplanner.ui;

import mealplanner.service.MealService;
import mealplanner.service.PlanService;

import java.util.Scanner;

public class Menu {
    private final MealService mealService;
    private final PlanService planService;
    private final Scanner scanner;

    public Menu(MealService mealService, PlanService planService) {
        this.mealService = mealService;
        this.planService = planService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("What would you like to do (add, show, plan, list plan, exit)?");
            String action = scanner.nextLine().trim().toLowerCase();

            switch (action) {
                case "add" -> mealService.addMeal(scanner);
                case "show" -> mealService.showMealsByCategoryWithIngredients(scanner);
                case "plan" -> planService.addPlan(scanner);
                case "list plan" -> planService.showPlan();
                case "exit" -> {
                    System.out.println("Bye!");
                    return;
                }
            }
        }
    }
}
