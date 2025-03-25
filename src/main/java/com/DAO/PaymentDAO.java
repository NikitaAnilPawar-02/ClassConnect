package com.DAO;

public interface PaymentDAO {
	public boolean createPaymentRecord(int studentId, Integer parentId,
			double amount, String razorpayPaymentId);
	public boolean updatePaymentStatus(int studentId, Integer parentId,
			double amountPaid, String razorpayPaymentId);
}
