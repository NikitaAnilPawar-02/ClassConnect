package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.DB.DBConnect;

public class PaymentDAOImpl implements PaymentDAO {
	@Override
	public boolean createPaymentRecord(int studentId, Integer parentId,
			double amount, String razorpayPaymentId) {
		boolean isCreated = false;
		try (Connection conn = DBConnect.getConn()) {

			String checkPaymentSQL = "SELECT paymentId, amountDue, amountPaid FROM payments WHERE studentId = ?";
			PreparedStatement checkPaymentPs = conn
					.prepareStatement(checkPaymentSQL);
			checkPaymentPs.setInt(1, studentId);
			ResultSet rs = checkPaymentPs.executeQuery();

			if (rs.next()) {
				int paymentId = rs.getInt("paymentId");
				double existingDue = rs.getDouble("amountDue");
				double existingPaid = rs.getDouble("amountPaid");

				String updateQuery = "UPDATE payments SET paymentStatus = 'Pending' WHERE paymentId = ?";
				PreparedStatement psUpdate = conn.prepareStatement(updateQuery);
				psUpdate.setInt(1, paymentId);

				int rowsAffected = psUpdate.executeUpdate();
				isCreated = (rowsAffected > 0);
				psUpdate.close();

			} else {
				String sql = "INSERT INTO payments (studentId, parentId, amountDue, amountPaid, paymentStatus, razorpayPaymentId, paymentDate) VALUES (?, ?, 0, 0, 'Pending', ?, NOW())";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, studentId);
				if (parentId != null) {
					ps.setInt(2, parentId);
				} else {
					ps.setNull(2, java.sql.Types.INTEGER);
				}
				ps.setString(3, razorpayPaymentId);

				int rowCount = ps.executeUpdate();
				isCreated = (rowCount > 0);
				ps.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isCreated;
	}

	@Override
	public boolean updatePaymentStatus(int studentId, Integer parentId,
			double amountPaid, String razorpayPaymentId) {
		boolean updated = false;
		try (Connection conn = DBConnect.getConn()) {
			
			String getDueQuery = "SELECT paymentId, amountDue, amountPaid FROM payments WHERE studentId = ?";
			PreparedStatement psGet = conn.prepareStatement(getDueQuery);
			psGet.setInt(1, studentId);
			ResultSet rs = psGet.executeQuery();

			if (rs.next()) {
				int paymentId = rs.getInt("paymentId");
				double dueAmount = rs.getDouble("amountDue");
				double paidAmount = rs.getDouble("amountPaid");

				double newPaidAmount = paidAmount + amountPaid;
				double newDueAmount = Math.max(0, dueAmount - amountPaid);
				String newStatus = (newDueAmount == 0) ? "Success" : "Pending";

				String updateQuery = "UPDATE payments SET amountPaid = ?, amountDue = ?, paymentStatus = ?, razorpayPaymentId = ?, paymentDate = NOW() WHERE paymentId = ?";
				PreparedStatement psUpdate = conn.prepareStatement(updateQuery);
				psUpdate.setDouble(1, newPaidAmount);
				psUpdate.setDouble(2, newDueAmount);
				psUpdate.setString(3, newStatus);
				psUpdate.setString(4, razorpayPaymentId);
				psUpdate.setInt(5, paymentId);

				int rowsAffected = psUpdate.executeUpdate();
				updated = (rowsAffected > 0);
				psUpdate.close();
			}

			rs.close();
			psGet.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return updated;
	}
}
