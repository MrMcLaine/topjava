package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int MEAL_ID_FIRST = START_SEQ + 3;
    public static final int MEAL_ID_SECOND = START_SEQ + 4;
    public static final int MEAL_ID_THIRD = START_SEQ + 5;
    public static final int MEAL_ID_FOURTH = START_SEQ + 6;
    public static final int MEAL_ID_FIFTH = START_SEQ + 7;
    public static final int MEAL_ID_SIXTH = START_SEQ + 8;
    public static final int MEAL_ID_SEVENTH = START_SEQ + 9;


    public static final Meal mealFirst = new Meal(MEAL_ID_FIRST, LocalDateTime.of(2020, Month.JANUARY,
            30, 10, 0, 0), "Завтрак", 500);
    public static final Meal mealSecond = new Meal(MEAL_ID_SECOND, LocalDateTime.of(2020, Month.JANUARY,
            30, 13, 0, 0), "Обед", 1000);
    public static final Meal mealThird = new Meal(MEAL_ID_THIRD, LocalDateTime.of(2020, Month.JANUARY,
            30, 20, 0), "Ужин", 500);
    public static final Meal mealFourth = new Meal(MEAL_ID_FOURTH, LocalDateTime.of(2020, Month.JANUARY,
            31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal mealFifth = new Meal(MEAL_ID_FIFTH, LocalDateTime.of(2020, Month.JANUARY,
            31, 10, 0), "Завтрак", 1000);
    public static final Meal mealSixth = new Meal(MEAL_ID_SIXTH, LocalDateTime.of(2020, Month.JANUARY,
            31, 13, 0), "Обед", 500);
    public static final Meal mealSeventh = new Meal(MEAL_ID_SEVENTH, LocalDateTime.of(2020, Month.JANUARY,
            31, 20, 0), "Ужин", 410);
    public static final List<Meal> MEAL_LIST = Arrays.asList(mealFirst, mealSecond, mealThird, mealFourth,
            mealFifth, mealSixth, mealSeventh);

    //to get & update
    public static void assertMatchToGet(Meal actual, Meal... expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("registered", "roles").isEqualTo(expected);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("registered", "roles").isEqualTo(expected);
    }

    //to get all
    public static void assertMatch(List<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("description", "calories").isEqualTo(expected);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(mealFirst);
        updated.setCalories(499);
        updated.setDateTime(LocalDateTime.of(2020, Month.JANUARY,
                30, 10, 1));
        updated.setDescription("Поздний завтрак");
        return updated;
    }

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2022, Month.OCTOBER,
                23, 10, 0, 0), "Supper", 777);
    }
}
