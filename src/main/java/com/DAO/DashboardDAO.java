package com.DAO;

public interface DashboardDAO {
	int getTotalStudents();
	double getCollectedFees();
	double getPendingFees();
	int getUpcomingNotices();
}
