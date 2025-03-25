package com.entity;
import java.time.LocalDate;
public class Student {
	private int studentId, studentStandard;
	public String studentName, studentEmail, studentPassword, studentPhnNo,
			studentGender, studentAddress;
	public Double studentFee;
	public LocalDate studentDOB;
	private Parent parent;

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentEmail() {
		return studentEmail;
	}
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	public String getStudentPassword() {
		return studentPassword;
	}
	public void setStudentPassword(String studentPassword) {
		this.studentPassword = studentPassword;
	}
	public String getStudentPhnNo() {
		return studentPhnNo;
	}
	public void setStudentPhnNo(String studentPhnNo) {
		this.studentPhnNo = studentPhnNo;
	}
	public String getStudentGender() {
		return studentGender;
	}
	public void setStudentGender(String studentGender) {
		this.studentGender = studentGender;
	}
	public String getStudentAddress() {
		return studentAddress;
	}
	public void setStudentAddress(String studentAddress) {
		this.studentAddress = studentAddress;
	}
	public int getStudentStandard() {
		return studentStandard;
	}
	public void setStudentStandard(int studentStandard) {
		this.studentStandard = studentStandard;
	}
	public Double getStudentFee() {
		return studentFee;
	}
	public void setStudentFee(Double studentFee) {
		this.studentFee = studentFee;
	}
	public LocalDate getStudentDOB() {
		return studentDOB;
	}
	public void setStudentDOB(LocalDate studentDOB) {
		this.studentDOB = studentDOB;
	}
	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", studentName="
				+ studentName + ", studentEmail=" + studentEmail
				+ ", studentPassword=" + studentPassword + ", studentPhnNo="
				+ studentPhnNo + ", studentGender=" + studentGender
				+ ", studentAddress=" + studentAddress + ", studentStandard="
				+ studentStandard + ", studentFee=" + studentFee
				+ ", studentDOB=" + studentDOB + "]";
	}

}
