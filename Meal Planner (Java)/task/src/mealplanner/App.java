package mealplanner;

import mealplanner.dao.MealDao;
import mealplanner.dao.PlanDao;
import mealplanner.dao.impl.MealDaoImpl;
import mealplanner.dao.impl.PlanDaoImpl;
import mealplanner.service.MealService;
import mealplanner.service.PlanService;
import mealplanner.service.impl.MealServiceImpl;
import mealplanner.service.impl.PlanServiceImpl;
import mealplanner.ui.Menu;
import mealplanner.util.ConnectionUtil;
import mealplanner.util.DatabaseInitializer;

import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            connection.setAutoCommit(true);
            DatabaseInitializer.createTables(connection);

            MealDao mealDao = new MealDaoImpl(connection);
            PlanDao planDao = new PlanDaoImpl(connection);

            MealService mealService = new MealServiceImpl(mealDao);
            PlanService planService = new PlanServiceImpl(planDao, mealDao);

            new Menu(mealService, planService).start();
        } catch (Exception e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}
