package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.SqlConnection;

public class UpdateController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SqlConnection sqlConn=new SqlConnection();
		int num=Integer.parseInt(req.getParameter("num"));
		String title=req.getParameter("title");
		String content=req.getParameter("content");
		try {
			sqlConn.updateOne(num, title, content);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
