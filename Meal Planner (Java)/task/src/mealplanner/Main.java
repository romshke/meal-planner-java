package mealplanner;

import java.sql.*;
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
    
    public static void menu(Statement mealStatement, Statement ingredientsStatement) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int mealId = 1, ingredientId = 1;

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

                            mealStatement.executeUpdate("insert into meals (category, meal, meal_id) " +
                                    "values ('" + category + "', '" + name + "', " + mealId + ")");

                            String[] ingredientsArr = ingredients.split(",\\s*");
                            for (String ingredient : ingredientsArr) {

                                ingredientsStatement.executeUpdate("insert into ingredients (ingredient, ingredient_id, meal_id) " +
                                        "values ('" + ingredient.trim() + "', '" + ingredientId + "', " + mealId + ")");

                                ingredientId++;
                            }

                            mealId++;

                            System.out.println("The meal has been added!");

                            break;
                        } else {
                            System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                        }
                    }
                    break;
                case "show":
                    ResultSet mealsResultSet = mealStatement.executeQuery("select * from meals");

                    if (!mealsResultSet.isBeforeFirst()) {
                        System.out.println("No meals saved. Add a meal first.");
                    } else {
                        while (mealsResultSet.next()) {
                            String category = mealsResultSet.getString("category");
                            String name = mealsResultSet.getString("meal");
                            List<String> ingredients = new ArrayList<>();

                            ResultSet ingredientsResultSet = ingredientsStatement.executeQuery("select ingredient from ingredients " +
                                    "join meals on meals.meal_id = ingredients.meal_id " +
                                    "where ingredients.meal_id = " + mealsResultSet.getInt("meal_id"));

                            while (ingredientsResultSet.next()) {
                                ingredients.add(ingredientsResultSet.getString("ingredient"));
                            }

                            Meal meal = new Meal(category, name, ingredients);

                            System.out.printf("%s%n", meal);

                            ingredientsResultSet.close();
                        }
                    }

                    mealsResultSet.close();

                    break;
                case "exit":
                    System.out.println("Bye!");
                    return;
                default:
                    break;
            }
        }
    }

    public static void connectToDatabase() {
        String DB_URL = "jdbc:postgresql:meals_db";
        String USER = "postgres";
        String PASS = "1111";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            connection.setAutoCommit(true);

            Statement mealStatement = connection.createStatement();
            Statement ingredientsStatement = connection.createStatement();

            mealStatement.executeUpdate("create table if not exists meals (" +
                    "category varchar(1024) NOT NULL," +
                    "meal varchar(1024) NOT NULL," +
                    "meal_id integer NOT NULL PRIMARY KEY" +
                    ")");

            ingredientsStatement.executeUpdate("create table if not exists ingredients (" +
                    "ingredient varchar(1024) NOT NULL," +
                    "ingredient_id integer NOT NULL PRIMARY KEY," +
                    "meal_id integer NOT NULL," +
                    "FOREIGN KEY (meal_id) REFERENCES meals(meal_id)" +
                    ")");

            menu(mealStatement, ingredientsStatement);

            mealStatement.close();
            ingredientsStatement.close();
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        connectToDatabase();
    }
}