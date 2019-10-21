package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL6_ID = START_SEQ + 2;

    public static final Meal MEAL1 = new Meal(START_SEQ + 7, LocalDateTime.of(2015, Month.MAY, 31, 20, 00, 00), "Ужин", 510);
    public static final Meal MEAL2 = new Meal(START_SEQ + 6, LocalDateTime.of(2015, Month.MAY, 31, 13, 00, 00), "Обед", 500);
    public static final Meal MEAL3 = new Meal(START_SEQ + 5, LocalDateTime.of(2015, Month.MAY, 31, 10, 00, 00), "Завтрак", 1000);
    public static final Meal MEAL4 = new Meal(START_SEQ + 4, LocalDateTime.of(2015, Month.MAY, 30, 20, 00, 00), "Ужин", 500);
    public static final Meal MEAL5 = new Meal(START_SEQ + 3, LocalDateTime.of(2015, Month.MAY, 30, 13, 00, 00), "Обед", 1000);
    public static final Meal MEAL6 = new Meal(START_SEQ + 2, LocalDateTime.of(2015, Month.MAY, 30, 10, 00, 00), "Завтрак", 500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    private static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
