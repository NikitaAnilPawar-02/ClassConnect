package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.DB.DBConnect;
import com.entity.Attendance;

public class AttendanceDAOImpl implements AttendanceDAO {

	@Override
	public List<Attendance> getStudentAttendance(int studentId) {
		List<Attendance> attendanceList = new ArrayList<>();
		String query = "SELECT attendanceId, studentId, date, status FROM attendance WHERE studentId = ? ORDER BY date DESC";

		try (Connection con = DBConnect.getConn();
				PreparedStatement stmt = con.prepareStatement(query)) {

			stmt.setInt(1, studentId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Attendance attendance = new Attendance();
					attendance.setAttendanceId(rs.getInt("attendanceId"));
					attendance.setStudentId(rs.getInt("studentId"));
					attendance.setDate(rs.getDate("date").toLocalDate());
					attendance.setStatus(rs.getString("status"));
					attendanceList.add(attendance);
				}
				System.out.println("Fetched " + attendanceList.size()
						+ " attendance records for student ID: " + studentId);

			}
		} catch (SQLException e) {
			System.err.println(
					"Error fetching attendance records: " + e.getMessage());
			e.printStackTrace();
		}
		return attendanceList;
	}

	@Override
	public List<Attendance> getAttendanceByStandardAndDate(int standard,
			String dateStr) {
		List<Attendance> attendanceList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DBConnect.getConn();
			String sql = "SELECT a.attendanceId, a.studentId, s.studentName, a.date, a.status "
					+ "FROM attendance a "
					+ "JOIN student s ON a.studentId = s.studentId "
					+ "WHERE s.studentStandard = ? AND a.date = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, standard);
			ps.setString(2, dateStr);
			rs = ps.executeQuery();

			while (rs.next()) {
				Attendance att = new Attendance();
				att.setAttendanceId(rs.getInt("attendanceId"));
				att.setStudentId(rs.getInt("studentId"));
				att.setStudentName(rs.getString("studentName"));
				att.setDate(LocalDate.parse(rs.getString("date"),
						DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				att.setStatus(rs.getString("status"));
				attendanceList.add(att);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		return attendanceList;
	}

	@Override
	public boolean updateAttendance(int attendanceId, String status) {
		String query = "UPDATE attendance SET status = ? WHERE attendanceId = ?";
		try (Connection con = DBConnect.getConn();
				PreparedStatement stmt = con.prepareStatement(query)) {

			stmt.setString(1, status);
			stmt.setInt(2, attendanceId);

			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println(
					"Error updating attendance record: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteAttendance(int studentId, String date) {
		boolean success = false;
		try (Connection con = DBConnect.getConn()) {
			String sql = "DELETE FROM attendance WHERE studentId = ? AND date = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, studentId);
			ps.setString(2, date);

			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				success = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}
}
