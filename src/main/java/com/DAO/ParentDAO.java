package com.DAO;
import com.entity.Parent;

public interface ParentDAO {
	public Parent login(String parentEmail, String parentPassword);
	public Parent getParentById(int parentId);
	public boolean updateParent(Parent parent);
	Parent getParentByStudentId(int studentId);
	public boolean addParent(Parent parent);
	public boolean updatePassword(String parentEmail, String newPassword);
	boolean checkEmailExists(String email);
}
