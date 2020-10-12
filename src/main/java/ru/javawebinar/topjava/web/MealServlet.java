package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealRestController = (new ClassPathXmlApplicationContext("spring/spring-app.xml")).getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "filter":
                log.info("getAllFiltered");
                String strStartDate = request.getParameter("startDate");
                String strEndDate = request.getParameter("endDate");
                String strStartTime = request.getParameter("startTime");
                String strEndTime = request.getParameter("endTime");

                LocalDate startDate = LocalDate.MIN;
                LocalDate endDate = LocalDate.MAX;
                LocalTime startTime = LocalTime.MIN;
                LocalTime endTime = LocalTime.MAX;
                if (!strStartDate.isEmpty()){
                    startDate = LocalDate.parse(strStartDate);
                }
                if (!strEndDate.isEmpty()){
                    endDate = LocalDate.parse(strEndDate);
                }
                if (!strStartTime.isEmpty()) {
                    startTime = LocalTime.parse(strStartTime);
                }
                if (!strEndTime.isEmpty()) {
                    endTime = LocalTime.parse(strEndTime);
                }
                request.setAttribute("meals",
                        mealRestController.getAllFiltered(startDate, startTime, endDate, endTime));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
            case "all":
            default:
                String id = request.getParameter("id");

                Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.parseInt(request.getParameter("calories")),
                        authUserId());

                log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
                if (meal.isNew()) {
                    mealRestController.create(meal);
                } else {
                    mealRestController.update(meal, meal.getId());
                }
                response.sendRedirect("meals");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, authUserId()) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
