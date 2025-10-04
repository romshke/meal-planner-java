package mealplanner;

import java.util.Iterator;
import java.util.List;

public class Meal {
    String category;
    String name;
    List<String> ingredients;

    public Meal(String category, String name, String ingredients) {
        this.category = category;
        this.name = name;
        this.ingredients = addIngredients(ingredients);
    }

    public Meal(String category, String name, List<String> ingredients) {
        this.category = category;
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> addIngredients(String ingredients) {
        return List.of(ingredients.split("\\s*,\\s*"));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("Category: ").append(category).append(System.lineSeparator());
        result.append("Name: ").append(name).append(System.lineSeparator());
        result.append("Ingredients: ").append(System.lineSeparator());

        Iterator<String> iterator = ingredients.iterator();

        while (iterator.hasNext()) {
            result.append(iterator.next());
            if (iterator.hasNext()) {
                result.append(System.lineSeparator());
            }
        }

        return result.toString();
    }
}
