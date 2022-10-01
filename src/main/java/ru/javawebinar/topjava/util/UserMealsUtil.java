package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;


import java.time.*;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));

    }


    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> totalCaloriesByDay = new HashMap<>();
        List<UserMealWithExcess> filteredMeals = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            LocalDate currentDate = userMeal.getDateTime().toLocalDate();
            boolean checkDay = totalCaloriesByDay.containsKey(currentDate);
            int sumOfCalories;
            if (checkDay) {
                sumOfCalories = totalCaloriesByDay.get(currentDate) + userMeal.getCalories();
            } else {
                sumOfCalories = userMeal.getCalories();
            }
            totalCaloriesByDay.put(currentDate, sumOfCalories);
        }
        for (UserMeal userMeal : meals) {
            boolean timeCheck = TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime);
            if (timeCheck) {
                int sumOfCalories = totalCaloriesByDay.get(userMeal.getDateTime().toLocalDate());
                boolean excess = sumOfCalories <= caloriesPerDay;
                filteredMeals.add(
                        new UserMealWithExcess(userMeal.getDateTime(),
                                userMeal.getDescription(),
                                userMeal.getCalories(), excess));
            }
        }
        return filteredMeals;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        // 1. Map <Day, SumPerDay>
        Map<Integer, Integer> sums = new HashMap<>();
        for (UserMeal foo : meals) {
            sums.merge(foo.getDateTime().toLocalDate().getDayOfMonth(), foo.getCalories(), Integer::sum);
        }
        // 2. Create filteredList
        List<UserMealWithExcess> filteredMeals = new ArrayList<>();
        meals.stream()
                .filter(x -> TimeUtil.isBetweenHalfOpen(x.getDateTime().toLocalTime(), startTime, endTime))
                .forEach(x -> filteredMeals.add(new UserMealWithExcess(x.getDateTime(), x.getDescription(), x.getCalories(),
                        caloriesPerDay <= sums.get(x.getDateTime().toLocalDate().getDayOfMonth()))));

        return filteredMeals;
    }
}
