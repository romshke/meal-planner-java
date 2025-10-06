package mealplanner.dao;


import mealplanner.model.PlanOption;

import java.util.List;

public interface PlanDao {
    List<PlanOption> getPlan();
    void add(PlanOption planOption);
    void delete();
}
