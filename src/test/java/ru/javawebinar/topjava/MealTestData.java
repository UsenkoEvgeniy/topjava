package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL1_ID = START_SEQ + 2;

    public static final Meal USER_MEAL1 = new Meal(MEAL1_ID, LocalDateTime.of(2020, 10, 18, 1, 0), "User завтрак", 500);
    public static final Meal USER_MEAL2 = new Meal(MEAL1_ID + 1, LocalDateTime.of(2020, 10, 18, 11, 0), "User обед", 1000);
    public static final Meal USER_MEAL3 = new Meal(MEAL1_ID + 2, LocalDateTime.of(2020, 10, 18, 19, 0), "User ужин", 1000);
    public static final Meal USER_MEAL4 = new Meal(MEAL1_ID + 3, LocalDateTime.of(2020, 10, 19, 10, 0), "User Еда на граничное значение", 100);
    public static final Meal USER_MEAL5 = new Meal(MEAL1_ID + 4, LocalDateTime.of(2020, 10, 19, 11, 0), "User завтрак", 1000);
    public static final Meal USER_MEAL6 = new Meal(MEAL1_ID + 5, LocalDateTime.of(2020, 10, 19, 12, 0), "User обед", 500);
    public static final Meal USER_MEAL7 = new Meal(MEAL1_ID + 6, LocalDateTime.of(2020, 10, 19, 18, 0), "User ужин", 410);
    public static final Meal ADMIN_MEAL1 = new Meal(MEAL1_ID + 7, LocalDateTime.of(2020, 10, 19, 9, 0), "Admin завтрак", 410);
    public static final Meal ADMIN_MEAL2 = new Meal(MEAL1_ID + 8, LocalDateTime.of(2020, 10, 19, 11, 30), "Admin обед", 410);
    public static final Meal ADMIN_MEAL3 = new Meal(MEAL1_ID + 9, LocalDateTime.of(2020, 10, 19, 18, 30), "Admin ужин", 410);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
