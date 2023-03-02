package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Comment;
import model.Detail;
import model.Feed;
import model.SqlConnection;

public class DetailController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out=resp.getWriter();
		SqlConnection sqlConn=new SqlConnection();
		Detail bean = new Detail();
		List<Comment> list=null;
		list=new ArrayList<Comment>();
		int num=Integer.parseInt(req.getParameter("num"));
		try {
			bean = sqlConn.getDetail(num);
			list=sqlConn.getComment(num);
			
			out.println("{\"detail\":[");
			out.println("{\"num\":"+bean.getNum()
					+",\"title\":\""+bean.getName()
					+"\",\"content\":\""+bean.getContent()
					+"\"}],");
			out.println("\"comment\":[");
			for(int i=0; i <list.size();i++) {
				 Comment comment = list.get(i);
				 if(i!=0)out.print(",");
				 out.println("{\"ref\":"+comment.getRef()
					+",\"step\":"+comment.getStep()
					+",\"writer\":\""+comment.getWriter()
					+"\",\"addTime\":\""+comment.getAddTime()
					+"\",\"content\":\""+comment.getContent()+"\"}");
			 }
			out.println("]}");
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.close();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SqlConnection sqlConn = new SqlConnection();
		String commentContent=req.getParameter("commentContent");
		String commentWriter=req.getParameter("commentWriter");
		int num = Integer.parseInt(req.getParameter("num"));
		try {
			sqlConn.addComment(commentWriter, commentContent, num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
}
