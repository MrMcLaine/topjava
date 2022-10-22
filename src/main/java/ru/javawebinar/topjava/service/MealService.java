package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.atStartOfDayOrMin;
import static ru.javawebinar.topjava.util.DateTimeUtil.atStartOfNextDayOrMax;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

/*    private final MealRepository repository;*/
    private final JdbcMealRepository jdbcRepository;
    @Autowired
    public MealService(JdbcMealRepository repository) {
        this.jdbcRepository = repository;
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(jdbcRepository.get(id, userId), id);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(jdbcRepository.delete(id, userId), id);
    }

    public List<Meal> getBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId) {
        return jdbcRepository.getBetweenHalfOpen(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate), userId);
    }

    public List<Meal> getAll(int userId) {
        return jdbcRepository.getAll(userId);
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(jdbcRepository.save(meal, userId), meal.getId());
    }

    public Meal create(Meal meal, int userId) {
        return jdbcRepository.save(meal, userId);
    }
}