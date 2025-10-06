package mealplanner.model;

public class Meal {
    private int id;
    private MealCategory mealCategory;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MealCategory getCategory() {
        return mealCategory;
    }

    public void setCategory(MealCategory mealCategory) {
        this.mealCategory = mealCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", mealCategory=" + mealCategory +
                ", name='" + name + '\'' +
                '}';
    }
}
