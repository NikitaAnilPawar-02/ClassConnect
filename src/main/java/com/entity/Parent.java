package com.entity;

public class Parent {
	private int parentId, studentId;;
	public String parentName, parentPassword, parentEmail, parentPhnNo,
			relationToStudent;
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getParentPassword() {
		return parentPassword;
	}
	public void setParentPassword(String parentPassword) {
		this.parentPassword = parentPassword;
	}
	public String getParentEmail() {
		return parentEmail;
	}
	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}
	public String getParentPhnNo() {
		return parentPhnNo;
	}
	public void setParentPhnNo(String parentPhnNo) {
		this.parentPhnNo = parentPhnNo;
	}
	public String getRelationToStudent() {
		return relationToStudent;
	}
	public void setRelationToStudent(String relationToStudent) {
		this.relationToStudent = relationToStudent;
	}
	@Override
	public String toString() {
		return "Parent [parentId=" + parentId + ", studentId=" + studentId
				+ ", parentName=" + parentName + ", parentPassword="
				+ parentPassword + ", parentEmail=" + parentEmail
				+ ", parentPhnNo=" + parentPhnNo + ", relationToStudent="
				+ relationToStudent + "]";
	}
}
