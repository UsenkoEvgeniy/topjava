package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryMealRepository implements MealRepository {
    private final AtomicLong id = new AtomicLong(0);

    public InMemoryMealRepository(){
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        save(new Meal(null, LocalDateTime.of(2020, Month.FEBRUARY, 1, 7, 50), "Завтрак", 300));
        save(new Meal(null, LocalDateTime.of(2020, Month.FEBRUARY, 1, 13, 40), "Обед", 1090));
        save(new Meal(null, LocalDateTime.of(2020, Month.FEBRUARY, 1, 20, 25), "Ужин", 510));
    }

    @Override
    public Collection<Meal> getAll() {
        return data.values();
    }

    @Override
    public Meal save(Meal meal) {
        Long tempId = meal.getId();
        if (tempId == null || tempId > id.get() || tempId <= 0) {
            tempId = id.incrementAndGet();
            meal.setId(tempId);
        }
        data.put(tempId, meal);
        return meal;
    }

    @Override
    public void deleteById(Long id) {
        data.remove(id);
    }

    @Override
    public Meal getById(Long id) {
        return data.get(id);
    }
}
