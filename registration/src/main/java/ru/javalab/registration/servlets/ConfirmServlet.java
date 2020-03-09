package ru.javalab.registration.servlets;

import org.springframework.context.ApplicationContext;
import ru.javalab.registration.services.ConfirmService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/confirm/*"})
public class ConfirmServlet extends HttpServlet {

    private ConfirmService confirmService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        ApplicationContext springContext = (ApplicationContext) context.getAttribute("springContext");
        confirmService = springContext.getBean(ConfirmService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        confirmService.isConfirmed(req.getPathInfo().substring(1));
        resp.sendRedirect("/signUp");
    }


}
