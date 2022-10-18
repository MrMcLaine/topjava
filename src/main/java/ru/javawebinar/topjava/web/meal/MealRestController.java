package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public void delete(int id) {
        service.delete(SecurityUtil.authUserId(), id);
    }

    public Meal get(int id) {
        return service.get(SecurityUtil.authUserId(), id);
    }

    public List<Meal> getAll() {
        return service.getAll(SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        return service.create(SecurityUtil.authUserId(), meal);
    }

    public void edit(int userId, Meal meal) {
        assureIdConsistent(meal, userId);
        service.edit(SecurityUtil.authUserId(), meal);
    }

    public List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        service.getAll(SecurityUtil.authUserId());
        return service.getTos(meals, caloriesPerDay);
    }

    public List<MealTo> getTosWithFilter(String startDate, String finishDate,
                                         String startTime, String finishTime) {
        int userId = SecurityUtil.authUserId();
        int calPerDay = SecurityUtil.authUserCaloriesPerDay();
        return service.getTosWithFilter(userId, calPerDay, startDate, finishDate, startTime, finishTime);
    }
}