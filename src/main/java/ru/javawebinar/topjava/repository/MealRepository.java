package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

// TODO add userId
public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(int userId, Meal meal);

    // false if meal does not belong to userId
    boolean delete(int userId, int id);

    // null if meal does not belong to userId
    Meal get(int userId, int id);

    // ORDERED dateTime desc
    Collection<Meal> getAll(int userId);

    List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime);

    List<MealTo> filterByPredicate(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter);

    MealTo createTo(Meal meal, boolean excess);

    List<MealTo> getTosWithFilter(int userId, int calPerDay, String startDate, String finishDate,
                                  String startTime, String finishTime);

}
