package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Feed;
import model.Member;
import model.SqlConnection;

public class JoinController extends HttpServlet {

	SqlConnection sqlConn = new SqlConnection();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		boolean same=false;
		String id=req.getParameter("id");
		PrintWriter out = resp.getWriter();
		try {
			same= sqlConn.getSameId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.println("{\"userSame\":[");
		out.println("{\"same\":"+same+"}");
		out.println("]}");
		out.close();
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id=req.getParameter("id");
		String pw=req.getParameter("password");
		String re=req.getParameter("passwordCheck");
		String name=req.getParameter("name");
		String fr = req.getParameter("fr");
		String se = req.getParameter("se");
		String th = req.getParameter("th");
		String phone=fr+"-"+se+"-"+th;
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
			//여기서 회원가입 진행
		try {
			sqlConn.setMember(id, pw, name, phone);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.close();
	}
}
