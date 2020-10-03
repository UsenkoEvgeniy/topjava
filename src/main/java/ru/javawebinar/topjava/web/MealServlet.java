package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet  extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final MealRepository mealRepo = new InMemoryRepoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");

        String action = req.getParameter("action");
        log.debug(action);

        if("delete".equals(action)) {
            log.debug(req.getParameter("id"));
            mealRepo.deleteById(Long.parseLong(req.getParameter("id")));
            resp.sendRedirect("meals");
        } else if("edit".equals(action)) {
            req.setAttribute("meal", mealRepo.getById((Long.parseLong(req.getParameter("id")))));
            RequestDispatcher reqDispAdd = getServletContext().getRequestDispatcher("/addMeal.jsp");
            reqDispAdd.forward(req, resp);
        }
        if (!resp.isCommitted()) {
            List<MealTo> mealList = MealsUtil.filteredByStreams(mealRepo.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
            req.setAttribute("mealList", mealList);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/meals.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String strId = req.getParameter("id");
        String strDateTime = req.getParameter("dateTime");
        String description = req.getParameter("description");
        String strCalories = req.getParameter("calories");
        log.debug(strId);
        log.debug(strDateTime);
        log.debug(description);
        log.debug(strCalories);
        Long id;
        try {
            id = Long.parseLong(strId);
        } catch (NumberFormatException e) {
            id = null;
        }
        try{
            LocalDateTime dateTime = LocalDateTime.parse(strDateTime);
            int calories = Integer.parseInt(strCalories);
            Meal meal = new Meal(id, dateTime, description, calories);
            mealRepo.save(meal);
        }catch (Exception ignored) {
        }
        resp.sendRedirect("meals");
    }
}
