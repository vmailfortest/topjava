package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {
    void save(Meal meal);

    Meal get(String uuid);

    void update(Meal meal);

    void delete(String uuid);

    List<Meal> getAll();

    void setStorage(List<Meal> storage);
}
