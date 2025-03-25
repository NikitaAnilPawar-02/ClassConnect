package com.admin.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.DB.DBConnect;
import com.entity.Payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/ViewFeeStatusServlet")
public class ViewFeeStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Payment> feeRecords = new ArrayList<>();

		try (Connection conn = DBConnect.getConn()) {
			String sql = "SELECT s.studentId, s.studentName, s.studentStandard, "
					+ "COALESCE(p.amountDue, 0) AS amountDue, "
					+ "COALESCE(p.amountPaid, 0) AS amountPaid, "
					+ "COALESCE(p.paymentStatus, 'Pending') AS paymentStatus, "
					+ "pr.parentName, p.paymentDate " + "FROM student s "
					+ "LEFT JOIN payments p ON s.studentId = p.studentId "
					+ "LEFT JOIN parent pr ON s.studentId = pr.studentId "
					+ "ORDER BY s.studentStandard, s.studentName";

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Payment record = new Payment();
				record.setStudentId(rs.getInt("studentId"));
				record.setStudentName(rs.getString("studentName"));
				record.setStudentStandard(rs.getInt("studentStandard"));
				record.setAmountDue(rs.getDouble("amountDue"));
				record.setAmountPaid(rs.getDouble("amountPaid"));
				record.setPaymentStatus(rs.getString("paymentStatus"));
				record.setParentName(rs.getString("parentName"));

				java.sql.Timestamp paymentTimestamp = rs
						.getTimestamp("paymentDate");
				if (paymentTimestamp != null) {
					record.setPaymentDate(
							new java.util.Date(paymentTimestamp.getTime()));
				}

				feeRecords.add(record);
			}

			System.out.println(
					"Total Records Retrieved in Servlet: " + feeRecords.size());

			request.setAttribute("feeRecords", feeRecords);

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Forwarding request to JSP...");
		request.getRequestDispatcher("/admin/viewFees.jsp").forward(request,
				response);
	}
}
