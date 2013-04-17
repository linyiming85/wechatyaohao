package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class TestJDBC {
	 public static void main(String[] args) {  
		  ResultSet rs = null;  
		  Statement stmt = null;  
		  Connection conn = null;  
		  try {  
		   Class.forName("oracle.jdbc.driver.OracleDriver");  
		   //new oracle.jdbc.driver.OracleDriver();   
		   conn = DriverManager.getConnection("jdbc:oracle:thin:@10.1.251.156:1527:unibss", "unibss", "unibss");  
		   stmt = conn.createStatement();  
		   rs = stmt.executeQuery("select * from APPLY_QUERY_LOG");  
		   while(rs.next()) {  
		    System.out.println(rs.getString("weixin_name")+"1");  
		    //System.out.println(rs.getInt("deptno"));   
		   }  
		  } catch (ClassNotFoundException e) {  
		   e.printStackTrace();  
		  } catch (SQLException e) {  
		   e.printStackTrace();  
		  } finally {  
		   try {  
		    if(rs != null) {  
		     rs.close();  
		     rs = null;  
		    }  
		    if(stmt != null) {  
		     stmt.close();  
		     stmt = null;  
		    }  
		    if(conn != null) {  
		     conn.close();  
		     conn = null;  
		    }  
		   } catch (SQLException e) {  
		    e.printStackTrace();  
		   }  
		  }  
		 }  
		// 将结果级转成List
		private static List transtoStrsList(ResultSet rs) throws SQLException
		{
			List ls = new ArrayList();
			int cols = rs.getMetaData().getColumnCount();
			while (rs.next())
			{
				String strs[] = new String[cols];
				for (int i=1; i<=cols; i++)
				{

					strs[i-1] = rs.getString(i);
				}
				ls.add(strs);
			}
			return ls;

		}
		// 查询得到List String[]格式
		public static List getStrsList(String sql)
		{
			List ls = new ArrayList();
			Connection conn = null;

			ResultSet rs = null;
			Statement stmt = null;			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");  
				   //new oracle.jdbc.driver.OracleDriver();   
				   conn = DriverManager.getConnection("jdbc:oracle:thin:@10.1.251.156:1527:unibss", "unibss", "unibss");  
				   stmt = conn.createStatement();  
				   rs = stmt.executeQuery(sql);  
				ls = transtoStrsList(rs);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new RuntimeException("执行查询方法异常");
			}
			finally
			{
				try {  
				    if(rs != null) {  
				     rs.close();  
				     rs = null;  
				    }  
				    if(stmt != null) {  
				     stmt.close();  
				     stmt = null;  
				    }  
				    if(conn != null) {  
				     conn.close();  
				     conn = null;  
				    }  
				   } catch (SQLException e) {  
				    e.printStackTrace();  
				   }  
			}
			return ls;
		}
		

// 查询得到List String[]格式
public static String getString(String sql)
{
	String ls = new String();
	Connection conn = null;

	ResultSet rs = null;
	Statement stmt = null;			try
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		   //new oracle.jdbc.driver.OracleDriver();   
		   conn = DriverManager.getConnection("jdbc:oracle:thin:@10.1.251.156:1527:unibss", "unibss", "unibss");  
		   stmt = conn.createStatement();  
		   rs = stmt.executeQuery(sql);  
		ls = ((String[])transtoStrsList(rs).get(0))[0];
	}
	catch (Exception e)
	{
		e.printStackTrace();
		throw new RuntimeException("执行查询方法异常");
	}
	finally
	{
		try {  
		    if(rs != null) {  
		     rs.close();  
		     rs = null;  
		    }  
		    if(stmt != null) {  
		     stmt.close();  
		     stmt = null;  
		    }  
		    if(conn != null) {  
		     conn.close();  
		     conn = null;  
		    }  
		   } catch (SQLException e) {  
		    e.printStackTrace();  
		   }  
	}
	return ls;
}

//执行sql
public static void exq(String sql)
{
	Connection conn = null;

	ResultSet rs = null;
	Statement stmt = null;			try
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		   //new oracle.jdbc.driver.OracleDriver();   
		   conn = DriverManager.getConnection("jdbc:oracle:thin:@10.1.251.156:1527:unibss", "unibss", "unibss");  
		   stmt = conn.createStatement();  
		   stmt.executeQuery(sql);  
		
	}
	catch (Exception e)
	{
		e.printStackTrace();
		throw new RuntimeException("执行查询方法异常");
	}
	finally
	{
		try {  
		    if(rs != null) {  
		     rs.close();  
		     rs = null;  
		    }  
		    if(stmt != null) {  
		     stmt.close();  
		     stmt = null;  
		    }  
		    if(conn != null) {  
		     conn.close();  
		     conn = null;  
		    }  
		   } catch (SQLException e) {  
		    e.printStackTrace();  
		   }  
	}
}
}