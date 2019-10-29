package ru.javawebinar.topjava.service;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.NoResultException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static Map<String, Long> tests = new HashMap();
    Instant start;
    Instant finish;

    @ClassRule
    public static TestWatcher classWatcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
        }

        @Override
        protected void finished(Description description) {
            System.out.println("TESTS EXECUTION RESULTS:");
            for (Map.Entry element : tests.entrySet()) {
                System.out.println(element.getKey() + " : " + element.getValue() + "ms");
            }
        }
    };

    @Rule
    public TestWatcher watcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            start = Instant.now();
        }

        @Override
        protected void finished(Description description) {
            finish = Instant.now();
            long duration = Duration.between(start, finish).toMillis();
            tests.put(name.getMethodName(), duration);
            System.out.println("Test execution time: " + duration);
        }
    };

    @Rule
    public final TestName name = new TestName();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Autowired
    private MealService service;

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    public void deleteNotFound() throws Exception {
        exception.expect(NotFoundException.class);
        service.delete(1, USER_ID);
    }

    @Test
    public void deleteNotOwn() throws Exception {
        exception.expect(NotFoundException.class);
        service.delete(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = getCreated();
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(newMeal, created);
        assertMatch(service.getAll(USER_ID), newMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void get() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
    }

    @Test
    public void getNotFound() throws Exception {
        exception.expect(NoResultException.class);
        service.get(1, USER_ID);
    }

    @Test
    public void getNotOwn() throws Exception {
        exception.expect(NoResultException.class);
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void updateNotFound() throws Exception {
        exception.expect(NotFoundException.class);
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void getBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    }
}