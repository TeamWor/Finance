package com.seu.action;

import com.seu.dao.UserDao;
import com.seu.dao.impl.UserDaoImpl;
import com.seu.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Amie on 2017/3/1.
 */
@WebServlet(name = "LoginAction",urlPatterns = {"/servlet/LoginAction"})
public class LoginAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");

        String username = request.getParameter("username");
        String password = request.getParameter("userpwd");
        /*System.out.println(username+"------"+password);*/
        List<User> list;
        UserDao userDao = new UserDaoImpl();
        list = userDao.findSimpleResult("select * from user where username = ?",username);
        if(list.size()!=0) {
            if (password.equals(list.get(0).getPassword())) {
                System.out.println("密码正确");
            } else {
                System.out.println("密码错误");
                PrintWriter out = response.getWriter();
                out.println("登录失败，请检查用户名和密码 ");
                boolean flag = false;
                request.setAttribute("log_flag", flag);
                request.getRequestDispatcher("../html/login.jsp").forward(request, response);
            }
        }
        else
        {
            System.out.println("登录失败，请检查用户名和密码");
            request.getRequestDispatcher("../html/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
