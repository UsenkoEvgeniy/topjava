package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, ConcurrentHashMap<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        SecurityUtil.setAuthUserId(2);
        for (int i = 0; i < 14; i++) {
            Meal meal = MealsUtil.meals.get(i);
            meal.setDescription("User " + authUserId() + " " + meal.getDescription());
            save(meal, authUserId());
            if (i == 6) {
                SecurityUtil.setAuthUserId(1);
            }
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.putIfAbsent(userId, new ConcurrentHashMap<>());
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }
        if (get(meal.getId(), userId) == null) {
            return null;
        }
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete id={}, userId={}", id, userId);
        if (get(id, userId) == null) {
            return false;
        }
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get id={} userId={}", id, userId);
        return repository.get(userId).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll userId={}", userId);
        return repository.get(userId).values()
                .stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllFiltered(LocalDate startDate, LocalDate endDate, int userId) {
        log.info("getAllFiltered userId{}", userId);
        return repository.get(userId).values().stream()
                .filter(mealTo -> mealTo.getDateTime().toLocalDate().compareTo(startDate) >= 0 && mealTo.getDateTime().toLocalDate().compareTo(endDate) <= 0)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

