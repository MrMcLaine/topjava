package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.to.MealTo;
import java.util.Collection;
import java.util.List;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    @Autowired
    public MealService() {
        this.repository = new InMemoryMealRepository();
    }

    public void delete(int userId, int id) {
        checkNotFoundWithId(repository.delete(userId, id), id);
    }

    public Meal get(int userId, int id) {
        return checkNotFoundWithId(repository.get(userId, id), id);
    }

    public List<Meal> getAll(int userId) {
        return (List<Meal>) repository.getAll(userId);
    }

    public Meal create(int userId, Meal meal) {
        return repository.save(userId, meal);
    }

    public void edit(int userId, Meal meal) {
        checkNotFoundWithId(repository.save(userId, meal), meal.getId());
    }

    public List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return repository.filterByPredicate(meals, caloriesPerDay, meal -> true);
    }

    public List<MealTo> getTosWithFilter(int userId, int calPerDay, String startDate, String finishDate,
                                         String startTime, String finishTime) {
        return repository.getTosWithFilter(userId, calPerDay, startDate, finishDate, startTime, finishTime);
    }
}
