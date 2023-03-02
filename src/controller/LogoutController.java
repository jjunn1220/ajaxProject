package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.SqlConnection;

public class LogoutController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session= req.getSession();
		
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = resp.getWriter();
		if(session!=null)session.invalidate();
		writer.println("<script>alert('로그아웃 되었습니다.'); location.href='main.jsp';</script>");

	}
}
