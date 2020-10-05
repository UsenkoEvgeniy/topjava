package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealRepository mealRepo;
    private int caloriesPerDay = 2000;

    @Override
    public void init() throws ServletException {
        mealRepo = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        String strId = req.getParameter("id");
        log.debug("get received with action {} with id {}", action, strId);

        if ("delete".equals(action)) {
            log.debug("Delete with id: {}", strId);
            mealRepo.deleteById(Long.parseLong(strId));
            resp.sendRedirect("meals");
        } else if ("edit".equals(action) || "insert".equals(action)) {
            if (strId != null && !strId.isEmpty()) {
                req.setAttribute("meal", mealRepo.getById((Long.parseLong(strId))));
            }
            getServletContext().getRequestDispatcher("/addMeal.jsp").forward(req, resp);
        }
        if (!resp.isCommitted()) {
            List<MealTo> mealList = MealsUtil.filteredByStreams(mealRepo.getAll(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
            req.setAttribute("mealList", mealList);
            req.setAttribute("formatter", DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm"));
            getServletContext().getRequestDispatcher("/meals.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String strId = req.getParameter("id");
        String strDateTime = req.getParameter("dateTime");
        String description = req.getParameter("description");
        String strCalories = req.getParameter("calories");
        log.debug("Save or update with id: {} date: {} description: {} calories: {}", strId, strDateTime, description, strCalories);

        Long id;
        try {
            id = Long.parseLong(strId);
        } catch (NumberFormatException e) {
            id = null;
        }
        Meal meal = new Meal(id, LocalDateTime.parse(strDateTime), description, Integer.parseInt(strCalories));
        mealRepo.save(meal);
        resp.sendRedirect("meals");
    }
}
