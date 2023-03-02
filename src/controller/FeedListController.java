package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Feed;
import model.FeedInfo;
import model.SqlConnection;

public class FeedListController extends HttpServlet {
	 @Override
	 protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 resp.setContentType("application/json");
		 resp.setCharacterEncoding("UTF-8");
		 int page;
		 if(req.getParameter("page")==null) page=1;
		 else{page= Integer.parseInt(req.getParameter("page"));}
		 PrintWriter out=resp.getWriter();
		 SqlConnection sqlConn = new SqlConnection();
			 try {
				 List<Feed> list = (List<Feed>) sqlConn.getFeed(page);
				 int maxCnt = sqlConn.getMaxCnt();
				 out.println("{\"feed\":[");
				 for(int i=0; i <list.size();i++) {
					 Feed bean = list.get(i);
					 if(i!=0)out.print(",");
					 out.println("{\"num\":"+bean.getNum()
						+",\"name\":\""+bean.getName()
						+"\",\"writer\":\""+bean.getWriter()
						+"\",\"addTime\":\""+bean.getAddTime()
						+"\",\"page\":"+page
						+",\"maxCnt\":"+maxCnt+"}");
				 }
				 out.println("]}");
			 } catch (SQLException e) {
				 e.printStackTrace();
			 }
			 out.close();
	 }
	 @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 resp.setContentType("application/json");
		 resp.setCharacterEncoding("UTF-8");
		 PrintWriter out=resp.getWriter();
		 int page=Integer.parseInt(req.getParameter("page"));
		 String key=req.getParameter("key");
		 String word=req.getParameter("word");
		 SqlConnection sqlConn = new SqlConnection();
		try {
			List<Feed> list = (List<Feed>) sqlConn.getFeed(page,key,word);
			int maxCnt = sqlConn.getMaxCnt(key,word);
			out.println("{\"feed\":[");
			 for(int i=0; i <list.size();i++) {
				 Feed bean = list.get(i);
				 if(i!=0)out.print(",");
				 out.println("{\"num\":"+bean.getNum()
					+",\"name\":\""+bean.getName()
					+"\",\"writer\":\""+bean.getWriter()
					+"\",\"addTime\":\""+bean.getAddTime()
					+"\",\"page\":"+page
					+",\"maxCnt\":"+maxCnt+"}");
			 }
			 out.println("]}");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.close();
	}
}
