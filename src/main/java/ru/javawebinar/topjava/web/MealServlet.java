package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;import ru.javawebinar.topjava.web.meal.MealRestController;
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

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private MealRestController mealController;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealController = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

            Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));

            if (request.getParameter("id").isEmpty()) {
                mealController.create(meal);
            } else {
                mealController.update(getId(request), meal);
            }
            response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "filter":
                LocalDate startDate = request.getParameter("startDate") == null ||
                        request.getParameter("startDate").isEmpty() ?
                        null : LocalDate.parse(request.getParameter("startDate"));
                LocalDate finishDate = request.getParameter("finishDate") == null ||
                        request.getParameter("finishDate").isEmpty() ?
                        null : LocalDate.parse(request.getParameter("finishDate"));
                LocalTime startTime = request.getParameter("startTime") == null ||
                        request.getParameter("startTime").isEmpty() ?
                        null : LocalTime.parse(request.getParameter("startTime"));
                LocalTime finishTime = request.getParameter("finishTime") == null ||
                        request.getParameter("finishTime").isEmpty() ?
                        null : LocalTime.parse(request.getParameter("finishTime"));
                request.setAttribute("meals", mealController.getTosWithFilter(startDate, finishDate, startTime, finishTime)
                );
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "delete":
                int id = getId(request);
                mealController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                request.setAttribute("meals",
                        mealController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }

}
