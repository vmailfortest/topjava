package ru.javawebinar.topjava.util;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> daysCalories = new HashMap<>();
        mealList.forEach(
                x -> daysCalories.merge(x.getDateTime().toLocalDate(), x.getCalories(), Integer::sum)
        );

        List<UserMealWithExceed> result = new ArrayList<>();
        mealList.forEach(x -> {
            if (TimeUtil.isBetween(x.getDateTime().toLocalTime(), startTime, endTime)) {
                result.add(new UserMealWithExceed(x.getDateTime(), x.getDescription(), x.getCalories(),
                        daysCalories.get(x.getDateTime().toLocalDate()) < caloriesPerDay));
            }
        });
        return result;
    }

    public static List<UserMealWithExceed>  getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> daysCalories = mealList.stream()
                .collect(Collectors.groupingBy(x -> x.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(x -> TimeUtil.isBetween(x.getDateTime().toLocalTime(), startTime, endTime))
                .map(x -> new UserMealWithExceed(x.getDateTime(), x.getDescription(), x.getCalories(), daysCalories.get(x.getDateTime().toLocalDate()) < caloriesPerDay))
                .collect(Collectors.toList());
    }
}
