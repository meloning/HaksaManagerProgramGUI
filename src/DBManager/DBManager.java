package DBManager;

import java.sql.*;
import java.util.*;

import DTO.StudentDTO;

public class DBManager {
	//DB/Table 연결 모음.
	static String url=null;
	static String id=null;
	static String pw=null;
	
	static {
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			//url="jdbc:mysql://localhost:3306/sampledb?useSSL=true";
			//id="root";
			//pw="1234";
		
			url="jdbc:oracle:thin:@localhost:1521:myoracle";
			id="hr";
			pw="1234";
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e) {
			System.out.println("DB드라이버 에러!->"+e.getMessage());
		}
	}
	
	public DBManager(){}
	
	public static Connection getConnection() {
		Connection conn=null;
		try {
			conn=DriverManager.getConnection(url,id,pw);
		}catch(SQLException e) {
			System.out.println("SQL실행 에러->"+e.getMessage());
		}
		return conn;
	}
	
	public static void close(Statement stmt) {
		try {
			stmt.close();
		}catch(SQLException e) {
			System.out.println("SQL 실행오류(Close)->"+e.getMessage());
		}
	}
	
	public static void close(Statement stmt,ResultSet rs) {
		try {
			if(rs!=null)rs.close();
			if(stmt!=null)stmt.close();
		}catch(SQLException e) {
			System.out.println("SQL 실행오류(Close)->"+e.getMessage());
		}
	}
	
}
