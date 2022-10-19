package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final MealService service;
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    public MealRestController(MealService service) {
        this.service = service;
    }

    public void delete(int id) {
        log.info("delete meal {} user {}", id, SecurityUtil.authUserId());
        service.delete(SecurityUtil.authUserId(), id);
    }

    public Meal get(int id) {
        log.info("get meal {} user {}", id, SecurityUtil.authUserId());
        return service.get(SecurityUtil.authUserId(), id);
    }

    public List<MealTo> getAll() {
        log.info("getAll meals user {}", SecurityUtil.authUserId());
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Meal create(Meal meal) {
        checkNew(meal);
        log.info("create {} user {}", meal, SecurityUtil.authUserId());
        return service.create(SecurityUtil.authUserId(), meal);
    }

    public void update(int userId, Meal meal) {
        assureIdConsistent(meal, userId);
        log.info("edit {} user {}", meal, userId);
        service.update(SecurityUtil.authUserId(), meal);
    }

    public List<MealTo> getTosWithFilter(LocalDate startDate, LocalDate finishDate,
                                         LocalTime startTime, LocalTime finishTime) {
        int userId = SecurityUtil.authUserId();
        int calPerDay = SecurityUtil.authUserCaloriesPerDay();
        log.info("getTosWithFilter user {}", SecurityUtil.authUserId());
        log.info("startDate {}, finishDate {}, startTime {}, finishTime {}", startDate, finishDate, startTime, finishTime);
        return service.getTosWithFilter(userId, calPerDay, startDate, finishDate, startTime, finishTime);
    }
}