package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MapStorage implements Storage {
    private ConcurrentMap<String, Meal> storage = new ConcurrentHashMap<>();

    @Override
    public void save(Meal meal) {
        storage.put(meal.getUuid(), meal);
    }

    @Override
    public Meal get(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public void update(Meal meal) {
        storage.put(meal.getUuid(), meal);
    }

    @Override
    public void delete(String uuid) {
        storage.remove(uuid);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void setStorage(List<Meal> storage) {
        for (Meal meal : storage) {
            save(meal);
        }
    }
}
