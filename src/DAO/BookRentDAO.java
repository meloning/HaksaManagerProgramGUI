package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import DBManager.DBManager;
import DTO.BookRentDTO;
import DTO.StudentDTO;

public class BookRentDAO {
	Statement stmt=null;
	ResultSet rs=null;
	
	ArrayList<BookRentDTO> arrBR= null;
	ArrayList<StudentDTO> arrStd=null;
	
	public BookRentDAO() {}
	
	public ArrayList SelectAll() {
		try {
			stmt=DBManager.getConnection().createStatement();
			rs=stmt.executeQuery("select student.id,student.name,bookrent.bookno, bookrent.rdate "
							+ " from student join bookrent on student.id=bookrent.id");
			arrBR=new ArrayList<BookRentDTO>();
			while(rs.next()) {
				BookRentDTO brTemp = new BookRentDTO();
				brTemp.getStdDto().setId(rs.getString("id"));
				brTemp.getStdDto().setName(rs.getString("name"));
				brTemp.setBookno(rs.getString("bookno"));
				brTemp.setrDate(rs.getString("rDate"));
				arrBR.add(brTemp);
			}
			return arrBR;
		}catch(SQLException e) {
			System.out.println("SQL 실행에러!(executeQuery)->"+e.getMessage());
		}finally {
			DBManager.close(stmt,rs);
		}	
		return null;
	}
	public ArrayList SelectAll(int deptIndex) {
		try {
			String query="";
			
			stmt=DBManager.getConnection().createStatement();
			
			switch(deptIndex){
			case 0://전체
				query=" order by bookrent.no";
				break;
			case 1://전자공학과
				query=" and student.departmentId='EL' ";
				query+=" order by bookrent.no";
				break;
			case 2://컴퓨터공학과
				query=" and student.departmentId='CO' ";
				query+=" order by bookrent.no";
				break;
			case 3://보안학과
				query=" and student.departmentId='SC' ";
				query+=" order by bookrent.no";
				break;
			}
			
			rs=stmt.executeQuery("select student.id,student.name,bookrent.bookno, bookrent.rdate "
							+ " from student join bookrent on student.id=bookrent.id"+query);
			
			arrBR=new ArrayList<BookRentDTO>();
			
			while(rs.next()) {
				BookRentDTO brTemp = new BookRentDTO();
				brTemp.getStdDto().setId(rs.getString("id"));
				brTemp.getStdDto().setName(rs.getString("name"));
				brTemp.setBookno(rs.getString("bookno"));
				brTemp.setrDate(rs.getString("rDate"));
				arrBR.add(brTemp);
			}
			
			return arrBR;
		}catch(SQLException e) {
			System.out.println("SQL 실행에러!(executeQuery)->"+e.getMessage());
		}finally {
			DBManager.close(stmt,rs);
		}	
		return null;
	}
	
	public HashMap<String,String> ShowStatistics() {
		try {
			
			String sql="select substr(rDate,1,4) as quarter,count(*) as quarter_su from bookRent group by substr(rDate,1,4) order by substr(rDate,1,4)";

			stmt=DBManager.getConnection().createStatement();
			rs=stmt.executeQuery(sql);
			HashMap<String,String> Statistics = new HashMap<String,String>();
			while(rs.next()) {
				 Statistics.put(rs.getString("quarter"), rs.getString("quarter_su"));
			}
			return Statistics;
		}catch(SQLException e) {
			System.out.println("SQL 실행에러!(executeQuery)->"+e.getMessage());
		}finally {
			DBManager.close(stmt,rs);
		}
		return null;
	}
	public HashMap<String,String> ShowStatistics(int deptIndex) {
		try {
			String year="";
			String sql="";
			
			switch(deptIndex){
			case 0:
				return null;
			case 1:
				year="2016";
				break;
			case 2:
				year="2017";
				break;
			case 3:
				year="2018";
				break;
			}
			
			sql="select to_char(to_date(substr(rdate,1,6),'YYYYMM'),'q') as quarter, count(*) as quarter_su "+
					"from bookRent where substr(rDate,1,4)='"+year+"'"+
					" group by to_char(to_date(substr(rdate,1,6),'YYYYMM'),'q') "+
					"order by to_char(to_date(substr(rdate,1,6),'YYYYMM'),'q')" ;
			
			stmt=DBManager.getConnection().createStatement();
			rs=stmt.executeQuery(sql);
			HashMap<String,String> Statistics = new HashMap<String,String>();
			while(rs.next()) {
				 Statistics.put(rs.getString("quarter"), rs.getString("quarter_su"));
			}
			return Statistics;
		}catch(SQLException e) {
			System.out.println("SQL 실행에러!(executeQuery)->"+e.getMessage());
		}finally {
			DBManager.close(stmt,rs);
		}
		return null;
	}
}
