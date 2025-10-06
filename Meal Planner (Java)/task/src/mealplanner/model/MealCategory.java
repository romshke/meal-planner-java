package mealplanner.model;

public enum MealCategory {
    BREAKFAST("breakfast"),
    LUNCH("lunch"),
    DINNER("dinner");

    private final String name;

    MealCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static boolean contains(String category) {
        for (MealCategory mealMealCategory : MealCategory.values()) {
            if (mealMealCategory.getName().equals(category)) {
                return true;
            }
        }

        return false;
    }

    public static MealCategory fromString(String category) {
        for (MealCategory mealMealCategory : MealCategory.values()) {
            if (mealMealCategory.getName().equals(category)) {
                return mealMealCategory;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}