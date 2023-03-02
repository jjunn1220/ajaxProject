package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.SqlConnection;

public class LoginController extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		String id=req.getParameter("id");
		String pw=req.getParameter("password");
		SqlConnection sqlConn=new SqlConnection();
		PrintWriter out = resp.getWriter();
		boolean login;
		login = sqlConn.login(id, pw);
		out.println("{\"userLogin\":[");
		out.println("{\"login\":"+login+"}");
		out.println("]}");
		out.close();
	}
}
