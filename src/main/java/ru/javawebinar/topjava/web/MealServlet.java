package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.model.Meals;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet  extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        PrintWriter out=resp.getWriter();
        out.println("<a href='index.html'>Home</a>");
        out.println("<h1>Meals</h1>");

        List<MealTo> mealList = MealsUtil.filteredByStreams(Meals.getData(), LocalTime.MIN, LocalTime.MAX, 2000);

        out.print("<table border='1'");
        out.print("<tr><th>Date</th><th>Description</th><th>Calories</th></tr>");
        DateTimeFormatter tableDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for(MealTo meal :mealList){
            out.print("<tr style=\"color:" + (meal.isExcess()?"red":"green") + "\"><td>"+tableDate.format(meal.getDateTime())
                    + "</td><td>"+meal.getDescription() + "</td><td>" + meal.getCalories() + "</td></tr>");
        }
        out.print("</table>");

        out.close();
    }
}
