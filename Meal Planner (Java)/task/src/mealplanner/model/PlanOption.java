package mealplanner.model;

public class PlanOption {
    private int id;
    private Day day;
    private MealCategory category;
    private int mealId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public MealCategory getCategory() {
        return category;
    }

    public void setCategory(MealCategory mealCategory) {
        this.category = mealCategory;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", day='" + day + '\'' +
                ", category=" + category +
                ", mealId=" + mealId +
                '}';
    }
}
