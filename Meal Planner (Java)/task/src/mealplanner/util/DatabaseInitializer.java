package mealplanner.util;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void createTables(Connection connection) throws Exception {
        try (Statement st = connection.createStatement()) {
            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS meals (
                    meal_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                    meal VARCHAR(1024) NOT NULL,
                    category VARCHAR(1024) NOT NULL
                );
            """);
            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS ingredients (
                    ingredient_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                    ingredient VARCHAR(1024) NOT NULL,
                    meal_id INTEGER NOT NULL,
                    FOREIGN KEY (meal_id) REFERENCES meals(meal_id)
                );
            """);
            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS plan (
                    plan_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                    day VARCHAR(1024) NOT NULL,
                    category VARCHAR(1024) NOT NULL,
                    meal_id INTEGER NOT NULL,
                    FOREIGN KEY (meal_id) REFERENCES meals(meal_id)
                );
            """);
        }
    }
}
