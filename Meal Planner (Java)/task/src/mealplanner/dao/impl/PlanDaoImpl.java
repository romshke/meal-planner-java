package mealplanner.dao.impl;

import mealplanner.dao.PlanDao;
import mealplanner.model.Day;
import mealplanner.model.MealCategory;
import mealplanner.model.PlanOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanDaoImpl implements PlanDao {
    private final Connection connection;

    public PlanDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<PlanOption> getPlan() {
        String sql = """
            SELECT *
            FROM plan;
        """;

        List<PlanOption> plan = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PlanOption planOption = new PlanOption();
                planOption.setId(rs.getInt("plan_id"));
                planOption.setDay(Day.fromString(rs.getString("day")));
                planOption.setCategory(MealCategory.fromString(rs.getString("category")));
                planOption.setMealId(rs.getInt("meal_id"));
                plan.add(planOption);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting plan", e);
        }

        return plan;
    }

    @Override
    public void add(PlanOption planOption) {
        String sql = """
            INSERT INTO plan (day, category, meal_id)
            VALUES (?, ?, ?);
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, planOption.getDay().getName());
            stmt.setString(2, planOption.getCategory().getName());
            stmt.setInt(3, planOption.getMealId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding plan", e);
        }
    }

    @Override
    public void delete() {
        String sql = """
            DELETE FROM plan;
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting plan", e);
        }
    }
}
