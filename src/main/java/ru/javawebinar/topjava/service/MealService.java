package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static ru.javawebinar.topjava.util.DateTimeUtil.*;
import static ru.javawebinar.topjava.util.MealsUtil.getFilteredTos;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public void delete(int userId, int id) {
        checkNotFoundWithId(repository.delete(userId, id), id);
    }

    public Meal get(int userId, int id) {
        return checkNotFoundWithId(repository.get(userId, id), id);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public Meal create(int userId, Meal meal) {
        return repository.save(userId, meal);
    }

    public void update(int userId, Meal meal) {
        checkNotFoundWithId(repository.save(userId, meal), meal.getId());
    }

    public List<MealTo> getTosWithFilter(int userId, int calPerDay, LocalDate startDate, LocalDate finishDate,
                                         LocalTime startTime, LocalTime finishTime) {
        List<Meal> mealsFilteredByDate = repository.getBetweenDate(userId, isStartDayNull(startDate), isFinishDayNull(finishDate));
        return getFilteredTos(mealsFilteredByDate, calPerDay, isStartTimeNull(startTime), isFinishTimeNull(finishTime));
    }
}
