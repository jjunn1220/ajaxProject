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

public class AddController extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String newTitle;
		String choise=req.getParameter("choise");
		String title=req.getParameter("title");
		String content=req.getParameter("content");
		String writer=req.getParameter("writer");
		SqlConnection sqlConn=new SqlConnection();
		resp.setContentType("text/html; charset=UTF-8");
		if(choise.equals("question")) {
			newTitle="[문의사항]  "+title;
		}
		else {
			newTitle="[이용후기]  "+title;
		}
		try {
			sqlConn.addFeed(newTitle, writer, content);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
