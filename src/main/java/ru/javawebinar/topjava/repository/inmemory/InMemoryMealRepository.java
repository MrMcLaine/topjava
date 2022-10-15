package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    /*    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();*/
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        //      MealsUtil.meals.forEach(this::save);
        MealsUtil.meals.forEach(meal -> save(1, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> filteredMeals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            filteredMeals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return filteredMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        Map<Integer, Meal> filteredMeals = repository.get(userId);
        return filteredMeals.remove(id) != null && filteredMeals != null;
    }

    @Override
    public Meal get(int userId, int id) {
        Map<Integer, Meal> filteredMeals = repository.get(userId);
        return filteredMeals == null ? null : filteredMeals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> filteredMeals = repository.get(userId);
        return filteredMeals.values().stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }

    public List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return filterByPredicate(meals, caloriesPerDay, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime));
    }

    public List<MealTo> filterByPredicate(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}

