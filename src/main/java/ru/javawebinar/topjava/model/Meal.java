package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.web.MealServlet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Meal {
    private LocalDateTime dateTime;

    private String description;

    private int calories;

    private String uuid;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        Objects.requireNonNull(dateTime, "dateTime must not be null");
        Objects.requireNonNull(description, "description must not be null");
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.uuid = String.valueOf(MealServlet.mealCounter.getAndIncrement());
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public String getUuid() {
        return uuid;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
}
