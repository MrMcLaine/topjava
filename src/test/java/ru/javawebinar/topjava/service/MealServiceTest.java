package ru.javawebinar.topjava.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest extends TestCase {

    @Autowired
    private MealService service;

    @Test
    public void testGet() {
        Meal meal = service.get(MEAL_ID_FIRST, USER_ID);
        assertMatch(meal, MEAL_LIST.get(0));
    }

    @Test
    public void testDelete() {
        service.delete(MEAL_ID_FIRST, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_FIRST, USER_ID));
    }

    @Test
    public void testGetBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(
                        LocalDate.of(2020, Month.JANUARY, 29),
                        LocalDate.of(2020, Month.FEBRUARY, 1), USER_ID),
                MEAL_LIST);
    }

    @Test
    public void testGetAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, MEAL_LIST);
    }

    @Test
    public void testUpdate() {
        Meal mealUpdate = getUpdated();
        service.update(mealUpdate, USER_ID);
        assertMatch(service.get(MEAL_ID_FIRST, USER_ID), getUpdated());
    }

    @Test
    public void testCreate() {
        int userTest_ID = 999999;
        Meal created = service.create(MealTestData.getNew(), userTest_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, userTest_ID), newMeal);
    }

    //delete, update, get not found
    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.delete(MEAL_ID_FIRST, USER_ID_NOT_FOUND));
    }

    @Test
    public void updateNotFound() {
        Meal mealUpdate = getUpdated();
        assertThrows(NotFoundException.class, () ->
                service.update(mealUpdate, USER_ID_NOT_FOUND));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(MEAL_ID_FIRST, USER_ID_NOT_FOUND));
    }

    // duplicateDateTimeCreate
    @Test
    public void duplicateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(MEAL_DUPLICATE.getDateTime(),
                        MEAL_DUPLICATE.getDescription(),
                        MEAL_DUPLICATE.getCalories()), USER_ID));
    }


}