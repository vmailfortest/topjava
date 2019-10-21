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
        assertMatch(service.get(created.getId(), UserTestData.USER_ID), newMeal);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateCreate() throws Exception {
        service.create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 00, 00), "Ужин2", 550), UserTestData.USER_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL6.getId(), UserTestData.USER_ID);
        assertMatch(service.getAll(UserTestData.USER_ID), MEAL1, MEAL2, MEAL3, MEAL4, MEAL5);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(1, UserTestData.USER_ID);
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL6_ID, UserTestData.USER_ID);
        assertMatch(meal, MEAL6);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, UserTestData.USER_ID);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDateTime(LocalDateTime.of(2015, Month.JUNE, 30, 22, 00, 00));
        updated.setDescription("Торт");
        updated.setCalories(250);
        service.update(updated, UserTestData.USER_ID);
        assertMatch(service.get(MEAL1.getId(), UserTestData.USER_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(UserTestData.USER_ID);
        assertMatch(all, MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6);
    }

    @Test(expected = NotFoundException.class)
    public void getByOtherUser() throws Exception {
        service.get(1, UserTestData.ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateByOtherUser() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDateTime(LocalDateTime.of(2015, Month.JUNE, 30, 22, 00, 00));
        updated.setDescription("Торт");
        updated.setCalories(250);
        service.update(updated, UserTestData.ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteByOtherUser() throws Exception {
        service.delete(MEAL6.getId(), UserTestData.ADMIN_ID);
    }
}