package com.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.DB.DBConnect;
import com.entity.Parent;
import com.entity.Student;
import com.utils.PasswordUtil;

public class StudentDAOImpl implements StudentDAO {

	@Override
	public Student login(String studentEmail, String studentPassword) {
		String query = "SELECT * FROM student WHERE studentEmail = ?";
		try (Connection con = DBConnect.getConn();
				PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setString(1, studentEmail);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String storedPassword = rs.getString("studentPassword");
				if (PasswordUtil.checkPassword(studentPassword,
						storedPassword)) {
					Student student = new Student();
					student.setStudentId(rs.getInt("studentId"));
					student.setStudentName(rs.getString("studentName"));
					student.setStudentEmail(rs.getString("studentEmail"));
					student.setStudentStandard(rs.getInt("studentStandard"));
					student.setStudentFee(rs.getDouble("studentFee"));
					return student;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addStudent(Student student, Parent parent)
			throws SQLException {
		Connection con = null;
		PreparedStatement psStudent = null, psParent = null, psPayment = null;
		ResultSet rs = null;
		boolean isAdded = false;

		try {
			con = DBConnect.getConn();
			con.setAutoCommit(false);

			String checkStudentEmailQuery = "SELECT studentEmail FROM student WHERE studentEmail = ?";
			PreparedStatement psCheckStudentEmail = con
					.prepareStatement(checkStudentEmailQuery);
			psCheckStudentEmail.setString(1, student.getStudentEmail());
			ResultSet rsStudentEmail = psCheckStudentEmail.executeQuery();

			if (rsStudentEmail.next()) {
				throw new SQLException("Duplicate entry for studentEmail: "
						+ student.getStudentEmail());
			}

		
			String checkParentEmailQuery = "SELECT parentEmail FROM parent WHERE parentEmail = ?";
			PreparedStatement psCheckParentEmail = con
					.prepareStatement(checkParentEmailQuery);
			psCheckParentEmail.setString(1, parent.getParentEmail());
			ResultSet rsParentEmail = psCheckParentEmail.executeQuery();

			if (rsParentEmail.next()) {
				throw new SQLException("Duplicate entry for parentEmail: "
						+ parent.getParentEmail());
			}

			String studentQuery = "INSERT INTO student (studentName, studentEmail, studentPassword, studentPhnNo, studentGender, studentDOB, studentAddress, studentStandard, studentFee) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			psStudent = con.prepareStatement(studentQuery,
					Statement.RETURN_GENERATED_KEYS);
			psStudent.setString(1, student.getStudentName());
			psStudent.setString(2, student.getStudentEmail());
			psStudent.setString(3,
					PasswordUtil.hashPassword(student.getStudentPassword()));
			psStudent.setString(4, student.getStudentPhnNo());
			psStudent.setString(5, student.getStudentGender());
			psStudent.setDate(6, Date.valueOf(student.getStudentDOB()));
			psStudent.setString(7, student.getStudentAddress());
			psStudent.setInt(8, student.getStudentStandard());
			psStudent.setDouble(9, student.getStudentFee());

			int studentInserted = psStudent.executeUpdate();
			rs = psStudent.getGeneratedKeys();
			int studentId = 0;

			if (rs.next()) {
				studentId = rs.getInt(1);
			}

			if (studentInserted > 0 && studentId != 0) {
				String parentQuery = "INSERT INTO parent (parentName, parentPassword, parentEmail, parentPhnNo, relationToStudent, studentId) VALUES (?, ?, ?, ?, ?, ?)";
				psParent = con.prepareStatement(parentQuery,
						Statement.RETURN_GENERATED_KEYS);
				psParent.setString(1, parent.getParentName());
				psParent.setString(2,
						PasswordUtil.hashPassword(parent.getParentPassword()));
				psParent.setString(3, parent.getParentEmail());
				psParent.setString(4, parent.getParentPhnNo());
				psParent.setString(5, parent.getRelationToStudent());
				psParent.setInt(6, studentId);

				int parentInserted = psParent.executeUpdate();
				rs = psParent.getGeneratedKeys();
				int parentId = 0;

				if (rs.next()) {
					parentId = rs.getInt(1);
				}

				if (parentInserted > 0 && parentId != 0) {
					double feeAmount = 0;
					switch (student.getStudentStandard()) {
						case 8 :
							feeAmount = 8000;
							break;
						case 9 :
							feeAmount = 9000;
							break;
						case 10 :
							feeAmount = 10000;
							break;
					}

					String paymentQuery = "INSERT INTO payments (studentId, parentId, amountDue, amountPaid, paymentStatus) VALUES (?, ?, ?, ?, ?)";
					psPayment = con.prepareStatement(paymentQuery);
					psPayment.setInt(1, studentId);
					psPayment.setInt(2, parentId);
					psPayment.setDouble(3, feeAmount);
					psPayment.setDouble(4, 0);
					psPayment.setString(5, "Pending");

					int paymentInserted = psPayment.executeUpdate();

					if (paymentInserted > 0) {
						con.commit();
						isAdded = true;
					}
				}
			}
		} catch (SQLException e) {
			if (con != null) {
				con.rollback();
			}
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (psStudent != null) {
				psStudent.close();
			}
			if (psParent != null) {
				psParent.close();
			}
			if (psPayment != null) {
				psPayment.close();
			}
			if (con != null) {
				con.setAutoCommit(true);
			}
		}
		return isAdded;
	}

	@Override
	public boolean updatePassword(String studentEmail, String newPassword) {
		String query = "UPDATE student SET studentPassword = ? WHERE studentEmail = ?";
		try (Connection con = DBConnect.getConn();
				PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, newPassword);
			ps.setString(2, studentEmail);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkEmailExists(String email) {
		String query = "SELECT COUNT(*) FROM student WHERE studentEmail = ?";
		try (Connection con = DBConnect.getConn();
				PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Student> getAllStudents() {
		List<Student> studentList = new ArrayList<>();
		String query = "SELECT s.*, p.parentName, p.parentEmail, p.parentPhnNo, p.relationToStudent "
				+ "FROM student s "
				+ "LEFT JOIN parent p ON s.studentId = p.studentId";
		try (Connection con = DBConnect.getConn();
				PreparedStatement stmt = con.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			System.out.println("Executing query: " + query);

			while (rs.next()) {
				Student student = new Student();
				student.setStudentId(rs.getInt("studentId"));
				student.setStudentName(rs.getString("studentName"));
				student.setStudentEmail(rs.getString("studentEmail"));
				student.setStudentPhnNo(rs.getString("studentPhnNo"));
				student.setStudentGender(rs.getString("studentGender"));
				student.setStudentDOB(rs.getDate("studentDOB").toLocalDate());
				student.setStudentAddress(rs.getString("studentAddress"));
				student.setStudentStandard(rs.getInt("studentStandard"));
				student.setStudentFee(rs.getDouble("studentFee"));

				Parent parent = new Parent();
				parent.setParentName(rs.getString("parentName"));
				parent.setParentEmail(rs.getString("parentEmail"));
				parent.setParentPhnNo(rs.getString("parentPhnNo"));
				parent.setRelationToStudent(rs.getString("relationToStudent"));
				student.setParent(parent);

				studentList.add(student);
			}

			System.out.println(
					"Number of students fetched: " + studentList.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentList;
	}

	@Override
	public List<Student> getStudentsByClass(int standard) {
		List<Student> students = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnect.getConn();
			String query = "SELECT studentId, studentName, studentGender, studentStandard FROM student WHERE studentStandard = ?";
			ps = con.prepareStatement(query);
			ps.setInt(1, standard);
			rs = ps.executeQuery();

			while (rs.next()) {
				Student student = new Student();
				student.setStudentId(rs.getInt("studentId"));
				student.setStudentName(rs.getString("studentName"));
				student.setStudentGender(rs.getString("studentGender"));
				student.setStudentStandard(rs.getInt("studentStandard"));
				students.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return students;
	}

	@Override
	public Student getStudentById(int studentId) {
		Student student = null;
		String query = "SELECT s.*, p.parentName, p.parentEmail, p.parentPhnNo, p.relationToStudent "
				+ "FROM student s "
				+ "LEFT JOIN parent p ON s.studentId = p.studentId "
				+ "WHERE s.studentId = ?";

		try (Connection con = DBConnect.getConn();
				PreparedStatement stmt = con.prepareStatement(query)) {

			stmt.setInt(1, studentId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				student = new Student();
				student.setStudentId(rs.getInt("studentId"));
				student.setStudentName(rs.getString("studentName"));
				student.setStudentEmail(rs.getString("studentEmail"));
				student.setStudentPhnNo(rs.getString("studentPhnNo"));
				student.setStudentGender(rs.getString("studentGender"));
				student.setStudentDOB(rs.getDate("studentDOB").toLocalDate());
				student.setStudentAddress(rs.getString("studentAddress"));
				student.setStudentStandard(rs.getInt("studentStandard"));
				student.setStudentFee(rs.getDouble("studentFee"));

				Parent parent = new Parent();
				parent.setParentName(rs.getString("parentName"));
				parent.setParentEmail(rs.getString("parentEmail"));
				parent.setParentPhnNo(rs.getString("parentPhnNo"));
				parent.setRelationToStudent(rs.getString("relationToStudent"));

				student.setParent(parent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	@Override
	public boolean updateStudent(Student student) {
		try (Connection con = DBConnect.getConn()) {

			String sql = "UPDATE student SET studentName=?, studentEmail=?, studentPhnNo=?, studentStandard=? WHERE studentId=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, student.getStudentName());
			ps.setString(2, student.getStudentEmail());
			ps.setString(3, student.getStudentPhnNo());
			ps.setInt(4, student.getStudentStandard());
			ps.setInt(5, student.getStudentId());

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteStudent(int studentId) {
		boolean deleted = false;
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DBConnect.getConn();
			conn.setAutoCommit(false);

			String updatePaymentsSQL = "UPDATE payments SET studentId = NULL, parentId = NULL WHERE studentId = ?";
			ps = conn.prepareStatement(updatePaymentsSQL);
			ps.setInt(1, studentId);
			ps.executeUpdate();

			String deleteParentSQL = "DELETE FROM parent WHERE studentId = ?";
			ps = conn.prepareStatement(deleteParentSQL);
			ps.setInt(1, studentId);
			ps.executeUpdate();

			String deleteStudentSQL = "DELETE FROM student WHERE studentId = ?";
			ps = conn.prepareStatement(deleteStudentSQL);
			ps.setInt(1, studentId);
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				deleted = true;
			}

			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException rollbackEx) {
					rollbackEx.printStackTrace();
				}
			}
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return deleted;
	}

}
