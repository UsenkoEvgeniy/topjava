package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public List<MealTo> getAll(int userId, int userCalories) {
        return MealsUtil.getTos(repository.getAll(userId), userCalories);
    }

    public Meal get(int id, int userId) {
        Meal result = repository.get(id, userId);
        if (result == null) throw new NotFoundException("Not found");
        return result;
    }

    public void delete(int id, int userId) {
        if (!repository.delete(id, userId)) throw new NotFoundException("Not found");
    }

    public Meal create(Meal meal, int userId) {
        Meal result = repository.save(meal, userId);
        if (result == null) throw new NotFoundException("Not found");
        return result;
    }

    public void update(Meal meal, int userId) {
        if (repository.save(meal, userId) == null) throw new NotFoundException("Null in update");
    }

    public List<MealTo> getAllFiltered(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, int userId, int userCalories) {
        return repository.getAllFiltered(startDate, startTime, endDate, endTime, userId, userCalories);
    }
}