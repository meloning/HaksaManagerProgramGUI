package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DBManager.DBManager;
import DTO.StudentDTO;

public class StudentDAO {
	Statement stmt=null;
	ResultSet rs=null;
	
	ArrayList<StudentDTO> arrStd= null;
	
	public StudentDAO() {}
	
	public int Insert(StudentDTO stdDTO) {
		int result=0;
		try {
			stmt=DBManager.getConnection().createStatement();
			String sql="INSERT INTO student VALUES('"+stdDTO.getId()+"','"+stdDTO.getName()+"','"+stdDTO.getDepartmentId()+"','"+stdDTO.getAddress()+"')";
			result=stmt.executeUpdate(sql);
			System.out.println("Insert 실행 완료~");
			return result;
		}catch(SQLException e) {
			System.out.println("SQL 실행에러!(Insert)->"+e.getMessage());
		}finally {
			DBManager.close(stmt);
		}
		return result;
	}
	
	public int Update(StudentDTO stdDTO) {
		int result=0;
		try {
			stmt=DBManager.getConnection().createStatement();
			String sql="UPDATE student SET name='"+stdDTO.getName()+"',departmentId='"+stdDTO.getDepartmentId()+"',address='"+stdDTO.getAddress()+"' WHERE id='"+stdDTO.getId()+"'";
			result=stmt.executeUpdate(sql);
			System.out.println("Update 실행 완료~");
			return result;
		}catch(SQLException e) {
			System.out.println("SQL 실행에러!(Update)->"+e.getMessage());
		}finally {
			DBManager.close(stmt);
		}
		return result;
	}
	
	public int Delete(String id) {
		int result=0;
		try {
			stmt=DBManager.getConnection().createStatement();
			String sql="DELETE FROM student WHERE id='"+id+"'";
			result=stmt.executeUpdate(sql);
			System.out.println("Delete 실행 완료~");
			return result;
		}catch(SQLException e) {
			System.out.println("SQL 실행에러!(Delete)->"+e.getMessage());
		}finally {
			DBManager.close(stmt);
		}
		return result;
	}
	public ArrayList SelectAll() {
		try {
			stmt=DBManager.getConnection().createStatement();
			rs = stmt.executeQuery("select s.id, s.name, d.name as departmentName, s.address " + 
					"from student s, department d " + 
					"where s.departmentId=d.id");
			arrStd=new ArrayList<StudentDTO>();
			while(rs.next()) {
				StudentDTO stdTemp = new StudentDTO();
				stdTemp.setId(rs.getString("id"));
				stdTemp.setName(rs.getString("name"));
				stdTemp.setDepartmentId(rs.getString("departmentName"));
				stdTemp.setAddress(rs.getString("address"));
				arrStd.add(stdTemp);
			}
			return arrStd;
		}catch(SQLException e) {
			System.out.println("SQL 실행에러!(executeQuery)->"+e.getMessage());
		}finally {
			DBManager.close(stmt,rs);
		}
		return null;
	}
	
	public StudentDTO SelectById(String id) {
		StudentDTO stdTemp=null;
		try {
			stmt=DBManager.getConnection().createStatement();
			rs = stmt.executeQuery("SELECT * FROM student WHERE id='"+id+"'");

			if(rs.next()) {
				stdTemp = new StudentDTO();
				stdTemp.setId(rs.getString("id"));
				stdTemp.setName(rs.getString("name"));
				stdTemp.setDepartmentId(rs.getString("departmentId"));
				stdTemp.setAddress(rs.getString("address"));
			}
			return stdTemp;
		}catch(SQLException e) {
			System.out.println("SQL 실행에러!(executeQuery)->"+e.getMessage());
		}finally {
			DBManager.close(stmt,rs);
		}
		return null;
	}
}
