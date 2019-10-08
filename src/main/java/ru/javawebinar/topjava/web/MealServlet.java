package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.MapStorage;
import ru.javawebinar.topjava.MealsTestData;
import ru.javawebinar.topjava.Storage;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private Storage storage;
    private static final Logger log = getLogger(MealServlet.class);
    public static final AtomicInteger mealCounter = new AtomicInteger();

    @Override
    public void init(ServletConfig config) {
        storage = new MapStorage();
        storage.setStorage(MealsTestData.generateMeals());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        LocalDateTime date = LocalDateTime.parse(request.getParameter("date"), TimeUtil.getFormatter());
        String desc = request.getParameter("desc");
        Integer cal = Integer.parseInt(request.getParameter("cal"));

        Meal meal;
        if (uuid == null) {
            meal = new Meal(date, desc, cal);
            storage.save(meal);
            log.debug("Save meal");
        } else {
            meal = storage.get(uuid);
            meal.setDateTime(date);
            meal.setDescription(desc);
            meal.setCalories(cal);
            storage.update(meal);
            log.debug("Update meal " + uuid);
        }

        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("Open meals.jsp");

        String action = request.getParameter("action");
        String uuid = request.getParameter("uuid");

        if (action == null) {
            List<MealTo> mealsTo = MealsUtil.getFiltered(storage.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("meals", mealsTo);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        Meal meal;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("meals");
                return;
            case "edit":
                meal = storage.get(uuid);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("editMeal.jsp").forward(request, response);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }


    }
}
