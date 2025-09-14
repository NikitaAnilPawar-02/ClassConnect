package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.DB.DBConnect;

public class DashboardDAOImpl implements DashboardDAO {

	@Override
	public int getTotalStudents() {
		int count = 0;
		try (Connection conn = DBConnect.getConn();
				PreparedStatement ps = conn
						.prepareStatement("SELECT COUNT(*) FROM student");
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public double getCollectedFees() {
		double total = 0;
		try (Connection conn = DBConnect.getConn();
				PreparedStatement ps = conn.prepareStatement(
						"SELECT SUM(amountPaid) FROM payments");
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				total = rs.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	@Override
	public double getPendingFees() {
		double total = 0;
		try (Connection conn = DBConnect.getConn();
				PreparedStatement ps = conn.prepareStatement(
						"SELECT SUM(amountDue) FROM payments");
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				total = rs.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	@Override
	public int getUpcomingNotices() {
		int count = 0;
		try (Connection conn = DBConnect.getConn();
				PreparedStatement ps = conn.prepareStatement(
						"SELECT COUNT(*) FROM notices WHERE date_posted >= CURDATE()");
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
