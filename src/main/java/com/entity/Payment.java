package com.entity;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Payment {
	private int paymentId, studentId, parentId, studentStandard;
	private Date paymentDate;
	private String studentName, parentName, paymentStatus, razorpayPaymentId;
	private double amountDue, amountPaid;
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getStudentStandard() {
		return studentStandard;
	}
	public void setStudentStandard(int studentStandard) {
		this.studentStandard = studentStandard;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getRazorpayPaymentId() {
		return razorpayPaymentId;
	}
	public void setRazorpayPaymentId(String razorpayPaymentId) {
		this.razorpayPaymentId = razorpayPaymentId;
	}
	public double getAmountDue() {
		return amountDue;
	}
	public void setAmountDue(double amountDue) {
		this.amountDue = amountDue;
	}
	public double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", studentId=" + studentId
				+ ", parentId=" + parentId + ", studentStandard="
				+ studentStandard + ", paymentDate=" + paymentDate
				+ ", studentName=" + studentName + ", parentName=" + parentName
				+ ", paymentStatus=" + paymentStatus + ", razorpayPaymentId="
				+ razorpayPaymentId + ", amountDue=" + amountDue
				+ ", amountPaid=" + amountPaid + "]";
	}
	public String getFormattedPaymentDate() {
		if (paymentDate == null) {
			return "N/A";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
		return sdf.format(paymentDate);
	}
}