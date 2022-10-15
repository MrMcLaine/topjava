package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;
import java.util.Collection;
import java.util.List;

@Controller
/*@RequestMapping("/meals")*/
public class MealRestController {
    private MealService service;

    public MealRestController() {
        this.service = new MealService();
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

    public Meal save(Meal meal) {
        return service.save(SecurityUtil.authUserId(), meal);
    }

    public List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        service.getAll(SecurityUtil.authUserId());
        return service.getTos(meals, caloriesPerDay);
    }
}