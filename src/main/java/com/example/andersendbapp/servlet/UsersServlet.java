package com.example.andersendbapp.servlet;

import com.example.andersendbapp.DAO.UserDAOImpl;
import com.example.andersendbapp.model.User;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

public class UsersServlet extends HttpServlet {
    private UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null ? "All" : action) {
            case "delete":
                deleteUser(request, response);
                break;
            case "add":
                addUser(request, response);
                break;
            case "update":
                updateUser(request, response);
                break;
            default:
                showAllUsers(request, response);
        }

    }

    private void showAllUsers(HttpServletRequest request, HttpServletResponse response) {

        List<User> users = userDAO.findall();
        request.setAttribute("user", users);
        try {
            request.getRequestDispatcher("/users.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)  {

        request.setAttribute(
                "user",
                userDAO.findById(Long.parseLong(request.getParameter("id"))).get());

        try {
            request.getRequestDispatcher("/add-user.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("user", new User());
        request.getRequestDispatcher("/add-user.jsp").forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        userDAO.delete(Long.parseLong(request.getParameter("id")));
            response.sendRedirect("user");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        User  user = new User();
            user.setFirstName( request.getParameter("firstname"));
        user.setLastName( request.getParameter("lastname"));
           user.setAge(Integer.parseInt(request.getParameter("age")));
        if (!request.getParameter("id").trim().equals("0")) {
            user.setId(Long.parseLong(request.getParameter("id").trim()));
            userDAO.update(user);
        } else {
            userDAO.saveUser(user);
        }
        response.sendRedirect("user");
        }
        
        
        
    }

