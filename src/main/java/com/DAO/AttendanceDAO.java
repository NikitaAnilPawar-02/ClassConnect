package com.DAO;

import java.util.List;

import com.entity.Attendance;

public interface AttendanceDAO {

	public List<Attendance> getStudentAttendance(int studentId);
	public List<Attendance> getAttendanceByStandardAndDate(int standard,
			String date);
	public boolean updateAttendance(int attendanceId, String status);
	public boolean deleteAttendance(int studentId, String date);
}
