package mealplanner.dao.impl;

import mealplanner.dao.MealDao;
import mealplanner.model.Ingredient;
import mealplanner.model.Meal;
import mealplanner.model.MealCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealDaoImpl implements MealDao {
    private final Connection connection;

    public MealDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Meal findMealById(int mealId) {
        String sql = """
            SELECT * FROM meals
            WHERE meal_id = ?;
        """;

        Meal meal = new Meal();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, mealId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                meal.setCategory(MealCategory.fromString(rs.getString("category")));
                meal.setName(rs.getString("meal"));
                meal.setId(mealId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching meal by id", e);
        }

        return meal;
    }

    @Override
    public List<Meal> findMealsByCategory(MealCategory category) {
        String sql = """
            SELECT * FROM meals
            WHERE category = ?;
        """;

        List<Meal> meals = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Meal meal = new Meal();
                meal.setId(rs.getInt("meal_id"));
                meal.setCategory(MealCategory.fromString(rs.getString("category")));
                meal.setName(rs.getString("meal"));
                meals.add(meal);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching meals by category", e);
        }

        return meals;
    }

    @Override
    public List<Meal> findMealsByCategoryOrderByName(MealCategory category) {
        String sql = """
            SELECT * FROM meals
            WHERE category = ?
            ORDER BY meal;
        """;

        List<Meal> meals = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Meal meal = new Meal();
                meal.setId(rs.getInt("meal_id"));
                meal.setCategory(MealCategory.fromString(rs.getString("category")));
                meal.setName(rs.getString("meal"));
                meals.add(meal);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching meals by category", e);
        }

        return meals;
    }

    @Override
    public List<Ingredient> findIngredientsByMealId(int mealId) {
        String sql = """
            SELECT *
            FROM ingredients i
            JOIN meals m ON m.meal_id = i.meal_id
            WHERE m.meal_id = ?;
        """;

        List<Ingredient> ingredients = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, mealId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(rs.getInt("ingredient_id"));
                ingredient.setName(rs.getString("ingredient"));
                ingredient.setMealId(mealId);
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching ingredients", e);
        }

        return ingredients;
    }


    @Override
    public void add(Meal meal) {
        String sql = """
            INSERT INTO meals (category, meal)
            VALUES (?, ?)
            RETURNING meal_id;
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, meal.getCategory().toString());
            stmt.setString(2, meal.getName());
            stmt.executeUpdate();

            // Получаем сгенерированный meal_id
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                meal.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding meal", e);
        }

    }

    @Override
    public void addIngredients(Meal meal, List<Ingredient> ingredients) {
        String sql = """
            INSERT INTO ingredients (ingredient, meal_id)
            VALUES (?, ?);
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (Ingredient ingredient : ingredients) {
                stmt.setString(1, ingredient.getName());
                stmt.setInt(2, meal.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding ingredients", e);
        }
    }
}
