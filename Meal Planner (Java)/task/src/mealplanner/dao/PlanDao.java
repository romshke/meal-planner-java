package mealplanner.dao;


import mealplanner.model.PlanOption;

import java.util.List;
import java.util.Map;

public interface PlanDao {
    List<PlanOption> get();
    Map<String, Integer> getShoppingList();
    void add(PlanOption planOption);
    void delete();
}
