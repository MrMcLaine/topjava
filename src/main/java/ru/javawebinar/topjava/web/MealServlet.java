package ru.javawebinar.topjava.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/")
public class MealServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

/*        PrintWriter out = resp.getWriter();
        out.print("<h1>Hello Servlet</h1>");*/

        req.setAttribute("key", "Value");
        req.getRequestDispatcher("meals.jsp").forward(req, resp);

    }
}
