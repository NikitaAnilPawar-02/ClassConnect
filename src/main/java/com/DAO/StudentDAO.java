package com.DAO;

import java.sql.SQLException;
import java.util.List;

import com.entity.Parent;
import com.entity.Student;

public interface StudentDAO {
	public Student login(String studentEmail, String studentPassword);
	public boolean addStudent(Student student, Parent parent)
			throws SQLException;
	public boolean updatePassword(String studentEmail, String newPassword);
	List<Student> getAllStudents();
	public List<Student> getStudentsByClass(int standard);
	public Student getStudentById(int studentId);
	boolean updateStudent(Student student);
	boolean deleteStudent(int studentId);
	boolean checkEmailExists(String email);
}
