package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.InMemoryRepoImpl;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet  extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealRepository mealRepo = new InMemoryRepoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        List<MealTo> mealList = MealsUtil.filteredByStreams(mealRepo.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("mealList", mealList);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/meals.jsp");
        rd.forward(req, resp);
    }
}
