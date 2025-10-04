package mealplanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    enum MealCategory {
        BREAKFAST("breakfast"),
        LUNCH("lunch"),
        DINNER("dinner");

        final String category;

        MealCategory(String category) {
            this.category = category;
        }

        public String getCategory() {
            return category;
        }
    }

    public static boolean isCorrectName(String name) {
        return name.matches("[a-zA-Z]+[a-zA-Z\\s]*");
    }

    private static boolean isCorrectIngredients(String ingredients) {
        return ingredients.matches("[a-zA-Z]+(,\\s+[a-zA-Z]+[a-zA-Z\\s]*)*");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Meal> meals = new ArrayList<>();

        while (true) {
            System.out.println("What would you like to do (add, show, exit)?");

            String input = scanner.nextLine();

            switch (input) {
                case "add":
                    while (true) {
                        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
                        String category = scanner.nextLine();

                        if (category.equals(MealCategory.BREAKFAST.getCategory()) ||
                            category.equals(MealCategory.LUNCH.getCategory()) ||
                            category.equals(MealCategory.DINNER.getCategory())) {

                            String name, ingredients;

                            while (true) {
                                System.out.println("Input the meal's name:");
                                name = scanner.nextLine();

                                if (!isCorrectName(name)) {
                                    System.out.println("Wrong format. Use letters only!");
                                    continue;
                                }

                                break;
                            }

                            while (true) {
                                System.out.println("Input the ingredients:");
                                ingredients = scanner.nextLine();

                                if (!isCorrectIngredients(ingredients)) {
                                    System.out.println("Wrong format. Use letters only!");
                                    continue;
                                }

                                break;
                            }

                            Meal meal = new Meal(category, name, ingredients);
                            meals.add(meal);
                            System.out.println("The meal has been added!");

                            break;
                        } else {
                            System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                        }
                    }
                    break;
                case "show":
                    if (meals.isEmpty()) {
                        System.out.println("No meals saved. Add a meal first.");
                    } else {
                        for (Meal meal : meals) {
                            System.out.println(meal);
                        }
                    }
                    break;
                case "exit":
                    System.out.println("Bye!");
                    return;
                default:
                    break;
            }
        }
    }


}