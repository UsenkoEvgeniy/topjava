package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertThrows;
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
    @Autowired
    private MealService mealService;

    @Test
    public void get() {
        Meal meal = mealService.get(MEAL1_ID, USER_ID);
        assertMatch(meal, UserMeal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.get(111111, USER_ID));
    }

    @Test
    public void getAnotherUserNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void delete() {
        mealService.delete(MEAL1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.delete(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> allFiltered = mealService.getBetweenInclusive(LocalDate.of(2020, 10, 18), LocalDate.of(2020, 10, 18), USER_ID);
        assertMatch(allFiltered, UserMeal3, UserMeal2, UserMeal1);
    }

    @Test
    public void getAll() {
        List<Meal> all = mealService.getAll(ADMIN_ID);
        assertMatch(all, AdminMeal3, AdminMeal2, AdminMeal1);
    }

    @Test
    public void update() {
        Meal updated = mealService.get(MEAL1_ID, USER_ID);
        updated.setDescription("updated meal");
        updated.setCalories(145);
        updated.setDateTime(LocalDateTime.of(2020, 1, 18, 11, 45));
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void updateOtherUserMeal() {
        Meal updated = mealService.get(MEAL1_ID, USER_ID);
        updated.setDescription("отредактировано админом");
        assertThrows(NotFoundException.class, () -> mealService.update(updated, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal meal = mealService.create(new Meal(UserMeal1.getDateTime(), "Завтрак админа", 2000), ADMIN_ID);
        assertMatch(meal, mealService.get(MEAL1_ID + 10, ADMIN_ID));
    }

    @Test
    public void duplicateDateTimeCreate() {
        Meal meal = new Meal(LocalDateTime.of(2020, 10, 18, 11, 0), "User обед повтор", 1001);
        assertThrows(DataAccessException.class, () ->
                mealService.create(meal, USER_ID));
    }
}