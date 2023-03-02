package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.SqlConnection;

public class CommentController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SqlConnection sqlConn = new SqlConnection();
		String comment2=req.getParameter("commentContent");
		String writer=req.getParameter("commentWriter");
		int num=Integer.parseInt(req.getParameter("num"));
		int ref=Integer.parseInt(req.getParameter("ref"));
		try {
			sqlConn.addReComment(ref,writer, comment2, num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
