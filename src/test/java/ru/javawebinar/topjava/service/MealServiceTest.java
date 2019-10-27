package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.now(), "Гречка", 700);
        Meal created = service.create(newMeal, UserTestData.USER_ID);
        newMeal.setId(created.getId());
        assertMatch(newMeal, created);
        assertMatch(service.get(created.getId(), UserTestData.USER_ID), newMeal);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateCreate() throws Exception {
        service.create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 00, 00), "Ужин2", 550), UserTestData.USER_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER1_MEAL6.getId(), UserTestData.USER_ID);
        assertMatch(service.getAll(UserTestData.USER_ID), USER1_MEAL1, USER1_MEAL2, USER1_MEAL3, USER1_MEAL4, USER1_MEAL5);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(1, UserTestData.USER_ID);
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(USER1_MEAL6.getId(), UserTestData.USER_ID);
        assertMatch(meal, USER1_MEAL6);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, UserTestData.USER_ID);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(USER1_MEAL1);
        updated.setDateTime(LocalDateTime.of(2015, Month.JUNE, 30, 22, 00, 00));
        updated.setDescription("Торт");
        updated.setCalories(250);
        service.update(updated, UserTestData.USER_ID);
        assertMatch(service.get(USER1_MEAL1.getId(), UserTestData.USER_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(UserTestData.USER_ID);
        assertMatch(all, USER1_MEAL1, USER1_MEAL2, USER1_MEAL3, USER1_MEAL4, USER1_MEAL5, USER1_MEAL6);
    }

    @Test
    public void getBetweenDates() throws Exception {
        List<Meal> all = service.getBetweenDates(USER1_MEAL1.getDate(), USER1_MEAL1.getDate(), UserTestData.USER_ID);
        assertMatch(all, USER1_MEAL1, USER1_MEAL2, USER1_MEAL3);
    }

    @Test(expected = NotFoundException.class)
    public void getByOtherUser() throws Exception {
        service.get(USER1_MEAL2.getId(), UserTestData.ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateByOtherUser() throws Exception {
        Meal updated = new Meal(USER1_MEAL1);
        updated.setDateTime(LocalDateTime.of(2015, Month.JUNE, 30, 22, 00, 00));
        updated.setDescription("Торт");
        updated.setCalories(250);
        service.update(updated, UserTestData.ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteByOtherUser() throws Exception {
        service.delete(USER1_MEAL6.getId(), UserTestData.ADMIN_ID);
    }
}