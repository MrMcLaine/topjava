package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;;
import ru.javawebinar.topjava.util.MealsUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Repository
public class InMemoryMealRepository implements MealRepository {
    /*    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();*/
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    public static final int userId = 1;

    {
        //      MealsUtil.meals.forEach(this::save);
        MealsUtil.meals.forEach(meal -> save(userId, meal));
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
        return filteredMeals.remove(id) != null;
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
        return filterByPredicate(meals, caloriesPerDay, meal -> isBetweenHalfOpen(meal.getTime(), startTime, endTime));
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

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }
    public List<MealTo> getTosWithFilter(int userId, int calPerDay, String startDate, String finishDate,
                                         String startTime, String finishTime) {
        List<Meal> mealsFilteredByDate = getBetweenDate(userId, localDateConvert(startDate), localDateConvert(finishDate));
        return getFilteredTos(mealsFilteredByDate, calPerDay, localTimeConvert(startTime), localTimeConvert(finishTime));
    }

    public List<Meal> getBetweenDate(int userId, LocalDate startDate, LocalDate finishDate) {
        Map<Integer, Meal> meals = repository.get(userId);
        Set<Integer> keys = meals.keySet();
        int minKey = 32;
        int maxKey = 0;
        for(Integer key : keys) {
            if (minKey > key) {
                minKey = key;
            } else if (maxKey < key) {
                maxKey = key;
            }
        }
        List<Meal> resultList = new ArrayList<>();
        for(int i = minKey; i < maxKey + 1; i++) {
            if (i >= startDate.getDayOfMonth() && i <= finishDate.getDayOfMonth()) {
                resultList.add(meals.get(i));
            }
        }
        return resultList;
    }

    public static LocalDate localDateConvert (String date) {
        return LocalDate.parse(date);
    }

    public static LocalTime localTimeConvert (String time) {
        return LocalTime.parse(time);
    }
}

