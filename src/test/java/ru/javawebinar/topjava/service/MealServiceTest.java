package ru.javawebinar.topjava.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.CodeTimer;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertThrows;
import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private static final Logger log = getLogger(MealServiceTest.class);
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    public CodeTimer timer = new CodeTimer();

    @Autowired
    private MealService service;

    @Test
    public void delete() {
        timer.startTimer();
        service.delete(MEAL1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID, USER_ID));
        timer.stopTimer();
        log.info("Time for delete test " + timer.getExecutionTime() + " in nano seconds.");
        timer.resetTimer();
    }

    @Test
    public void deleteNotFound() {
        timer.startTimer();
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
        timer.stopTimer();
        log.info("Time for deleteNotFound test " + timer.getExecutionTime() + " in nano seconds.");
        timer.resetTimer();
    }

    @Test
    public void deleteNotOwn() {
        timer.startTimer();
        assertThrows(NotFoundException.class, () -> service.delete(MEAL1_ID, ADMIN_ID));
        timer.stopTimer();
        log.info("Time for deleteNotOwn test " + timer.getExecutionTime() + " in nano seconds.");
        timer.resetTimer();
    }

    @Test
    public void create() {
        timer.startTimer();
        Meal created = service.create(getNew(), USER_ID);
        int newId = created.id();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        newMeal.setUser(created.getUser());
        MEAL_MATCHER.assertMatch(created, newMeal);
        Meal mealTemp = service.get(newId, USER_ID);
        mealTemp.setUser(newMeal.getUser());
        MEAL_MATCHER.assertMatch(mealTemp, newMeal);
        timer.stopTimer();
        log.info("Time for create test " + timer.getExecutionTime() + " in nano seconds.");
        timer.resetTimer();
    }

    @Test
    public void duplicateDateTimeCreate() {
        timer.startTimer();
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, meal1.getDateTime(), "duplicate", 100), USER_ID));
        timer.stopTimer();
        log.info("Time for duplicateDateTimeCreate test " + timer.getExecutionTime() + " in nano seconds.");
        timer.resetTimer();
    }

    @Test
    public void get() {
        timer.startTimer();
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        actual.setUser(adminMeal1.getUser());
        MEAL_MATCHER.assertMatch(actual, adminMeal1);
        timer.stopTimer();
        log.info("Time for get test " + timer.getExecutionTime() + " in nano seconds.");
        timer.resetTimer();
    }

    @Test
    public void getNotFound() {
        timer.startTimer();
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
        timer.stopTimer();
        log.info("Time for getNotFound test " + timer.getExecutionTime() + " in nano seconds.");
        timer.resetTimer();
    }

    @Test
    public void getNotOwn() {
        timer.startTimer();
        assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID, ADMIN_ID));
        timer.stopTimer();
        log.info("Time for getNotOwn test " + timer.getExecutionTime() + " in nano seconds.");
        timer.resetTimer();
    }

    @Test
    public void update() {
        timer.startTimer();
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        updated.setUser(service.get(MEAL1_ID, USER_ID).getUser());
        MEAL_MATCHER.assertMatch(service.get(MEAL1_ID, USER_ID), getUpdated());
        timer.stopTimer();
        log.info("Time for update test " + timer.getExecutionTime() + " in nano seconds.");
        timer.resetTimer();
    }

    @Test
    public void updateNotOwn() {
        timer.startTimer();
        assertThrows(NotFoundException.class, () -> service.update(meal1, ADMIN_ID));
        MEAL_MATCHER.assertMatch(service.get(MEAL1_ID, USER_ID), meal1);
        timer.stopTimer();
        log.info("Time for updateNotOwn test " + timer.getExecutionTime() + " in nano seconds.");
        timer.resetTimer();
    }

    @Test
    public void getAll() {
        timer.startTimer();
        MEAL_MATCHER.assertMatch(service.getAll(USER_ID), meals);
        timer.stopTimer();
        log.info("Time for getAll test " + timer.getExecutionTime() + " in nano seconds.");
        timer.resetTimer();
    }

    @Test
    public void getBetweenInclusive() {
        timer.startTimer();
        MEAL_MATCHER.assertMatch(service.getBetweenInclusive(
                        LocalDate.of(2020, Month.JANUARY, 30),
                        LocalDate.of(2020, Month.JANUARY, 30), USER_ID),
                meal3, meal2, meal1);
        timer.stopTimer();
        log.info("Time for getBetweenInclusive test " + timer.getExecutionTime() + " in nano seconds.");
        timer.resetTimer();
    }

    @Test
    public void getBetweenWithNullDates() {
        timer.startTimer();
        MEAL_MATCHER.assertMatch(service.getBetweenInclusive(null, null, USER_ID), meals);
        timer.stopTimer();
        log.info("Time for getBetweenWithNullDates test " + timer.getExecutionTime() + " in nano seconds.");
        timer.resetTimer();
    }
}