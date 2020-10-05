package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public interface MealRepository {
    ConcurrentHashMap<Long, Meal> data = new ConcurrentHashMap<>();

    Collection<Meal> getAll();

    Meal save(Meal meal);

    void deleteById(Long id);

    Meal getById(Long id);
}
