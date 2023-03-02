package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlConnection {
	Connection conn;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public void getConnection() {
		String url="jdbc:mysql://localhost:3306/lecture";
		String user=System.getenv("MYSQL_USER");
		String password=System.getenv("MYSQL_PW");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			if(conn==null || conn.isClosed())
			conn=DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void updateOne(int num, String name, String content) throws SQLException {
		//여기에 수정 코드 넣어라
		String sql="update feed set name=?,content=?,addTime=now() where num=?";
		try {
			getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, content);
			pstmt.setInt(3, num);
			pstmt.executeUpdate();
		}finally {
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
	}
	public void deleteComment(int num) throws SQLException {
		String sql="delete from comment where feedNum="+num;
		try {
			getConnection();
			stmt=conn.createStatement();
			stmt.executeUpdate(sql);
			
		}finally {
			if(stmt!=null) stmt.close();
			if(conn!=null) conn.close();
		}
	}
	public void deleteOne(int num) throws SQLException {
		deleteComment(num);
		String sql="delete from feed where num="+num;
		try {
			getConnection();
			stmt=conn.createStatement();
			stmt.executeUpdate(sql);
			
		}finally {
			if(stmt!=null) stmt.close();
			if(conn!=null) conn.close();
		}
	}
	public List<Comment> getComment(int num) throws SQLException {
		String sql="select * from comment where feedNum ="+num+" order by ref,step asc";
		List<Comment> list=null;
		list=new ArrayList<Comment>();
		try {
			getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				Comment beanComment = new Comment();
				beanComment.setRef(rs.getInt("ref"));
				beanComment.setStep(rs.getInt("step"));
				beanComment.setWriter(rs.getString("writer"));
				beanComment.setContent(rs.getString("content"));
				beanComment.setAddTime(rs.getDate("addTime"));
				beanComment.setNum(rs.getInt("feedNum"));
				list.add(beanComment);
			}
		}finally {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			if(conn!=null) conn.close();
		}
		return list;
	}
	public void addReComment(int ref,String user, String content, int num) throws SQLException {
		String sql="insert into comment (ref,step,writer,content,addTime,feedNum) ";
		sql+="values(?,((select ifnull(max(b.step),0) from comment b)+1),?,?,now(),?)";
		try {
			getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, ref);
			pstmt.setString(2, user);
			pstmt.setString(3, content);
			pstmt.setInt(4, num);
			pstmt.executeUpdate();
		}finally {
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
	}
	
	public void addComment(String user, String content, int num) throws SQLException {
		String sql="insert into comment (ref,step,writer,content,addTime,feedNum) ";
		sql+="values(((select ifnull(max(a.ref),0) from comment a)+1),0,?,?,now(),?)";
		try {
			getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, user);
			pstmt.setString(2, content);
			pstmt.setInt(3, num);
			pstmt.executeUpdate();
		}finally {
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
	}
	
	public Detail getDetail(int num) throws SQLException {
		Detail bean= new Detail();
		String sql="select num,name,content from feed where num="+num;
		try {
			getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next()) {
				bean.setNum(rs.getInt("num"));
				bean.setName(rs.getString("name"));
				bean.setContent(rs.getString("content"));
			}
		}finally {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			if(conn!=null) conn.close();
		}
		return bean;
	}
	
	public void addFeed(String name, String writer, String content) throws SQLException {
		String sql="insert into feed (name, writer, content, addTime) values(?,?,?,now())";
		try {
			getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, writer);
			pstmt.setString(3, content);
			pstmt.executeUpdate();
		}finally {
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
	}
	
	public boolean login(String id, String pw){
		String sql="select pw from member where id='"+id+"'";
		try {
			getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next()) {
				if(rs.getString("pw").equals(pw)) return true;
			}
		} catch (SQLException e) {
			return false;
		}finally {
			try {
				if(rs!=null)rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public void setMember(String id, String pw, String name, String phone) throws SQLException {
		String sql="insert into member (id,pw,name,phone) values (?,?,?,?)";
		try {
			getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.setString(4, phone);
			pstmt.executeUpdate();
		}finally {
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
	}
	public boolean getSameId(String id) throws SQLException{
		String sql="select id from member";
		try {
			getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				if(rs.getString("id").equals(id)) {return false;}
			}
		}finally {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			if(conn!=null) conn.close();
		}
		return true;
	}
	public int getMaxCnt() throws SQLException {
		String sql="select CEILING(count(*)/5) from feed";
		int maxCnt=0;
		try {
			getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next())maxCnt=rs.getInt(1);
			return maxCnt;
		}finally {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			if(conn!=null) conn.close();
		}
	}
	public int getMaxCnt(String key, String word) throws SQLException {
		String sql="select CEILING(count(*)/5) from feed where "+key+" like '%"+word+"%'";
		int maxCnt=0;
		try {
			getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next())maxCnt=rs.getInt(1);
			return maxCnt;
		}finally {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			if(conn!=null) conn.close();
		}
	}
	public List getFeed(int p) throws SQLException {
		int page = 5*(p-1);
		String sql="select * from feed order by num desc limit 5 offset "+page;
		try {
			getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			List<Feed> list=null;
			list=new ArrayList<Feed>();
			while(rs.next()) {
				Feed bean=new Feed();
				bean.setNum(rs.getInt("num"));
				bean.setName(rs.getString("name"));
				bean.setContent(rs.getString("content"));
				bean.setWriter(rs.getString("writer"));
				bean.setAddTime(rs.getDate("addTime"));
				list.add(bean);
			}
			return list;
		}finally {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			if(conn!=null) conn.close();
		}
	}
	public List getFeed(int p, String key, String word) throws SQLException {
		int page = 5*(p-1);
		String sql="select * from feed where "+key+" like '%"+word+"%' order by num desc limit 5 offset "+page;
		try {
			getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			List<Feed> list=null;
			list=new ArrayList<Feed>();
			while(rs.next()) {
				Feed bean=new Feed();
				bean.setNum(rs.getInt("num"));
				bean.setName(rs.getString("name"));
				bean.setContent(rs.getString("content"));
				bean.setWriter(rs.getString("writer"));
				bean.setAddTime(rs.getDate("addTime"));
				list.add(bean);
			}
			return list;
		}finally {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			if(conn!=null) conn.close();
		}
	}
}
