package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.DB.DBConnect;
import com.entity.Parent;
import com.utils.PasswordUtil;

public class ParentDAOImpl implements ParentDAO {
	@Override
	public Parent login(String parentEmail, String parentPassword) {
		String query = "SELECT * FROM parent WHERE parentEmail = ?";
		try (Connection con = DBConnect.getConn();
				PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setString(1, parentEmail);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String storedPassword = rs.getString("parentPassword");
				if (PasswordUtil.checkPassword(parentPassword,
						storedPassword)) {
					Parent parent = new Parent();
					parent.setParentId(rs.getInt("parentId"));
					parent.setParentName(rs.getString("parentName"));
					parent.setParentEmail(rs.getString("parentEmail"));
					parent.setStudentId(rs.getInt("studentId"));
					return parent;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addParent(Parent parent) {
		String query = "INSERT INTO parent (parentName, parentPassword, parentEmail, parentPhnNo, relationToStudent, studentId) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection con = DBConnect.getConn();
				PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, parent.getParentName());
			ps.setString(2,
					PasswordUtil.hashPassword(parent.getParentPassword()));
			ps.setString(3, parent.getParentEmail());
			ps.setString(4, parent.getParentPhnNo());
			ps.setString(5, parent.getRelationToStudent());
			ps.setInt(6, parent.getStudentId());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updatePassword(String parentEmail, String newPassword) {
		String query = "UPDATE parent SET parentPassword = ? WHERE parentEmail = ?";
		try (Connection con = DBConnect.getConn();
				PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, newPassword);
			ps.setString(2, parentEmail);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkEmailExists(String email) {
		String query = "SELECT COUNT(*) FROM parent WHERE parentEmail = ?";
		try (Connection con = DBConnect.getConn();
				PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Parent getParentById(int parentId) {
		Parent parent = null;
		String query = "SELECT * FROM parent WHERE parentId = ?";
		try (Connection con = DBConnect.getConn();
				PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setInt(1, parentId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				parent = new Parent();
				parent.setParentId(rs.getInt("parentId"));
				parent.setParentName(rs.getString("parentName"));
				parent.setParentEmail(rs.getString("parentEmail"));
				parent.setParentPhnNo(rs.getString("parentPhnNo"));
				parent.setRelationToStudent(rs.getString("relationToStudent"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return parent;
	}

	@Override
	public Parent getParentByStudentId(int studentId) {
		Parent parent = null;
		String query = "SELECT * FROM parent WHERE studentId = ?";
		try (Connection con = DBConnect.getConn();
				PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setInt(1, studentId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				parent = new Parent();
				parent.setParentId(rs.getInt("parentId"));
				parent.setStudentId(rs.getInt("studentId"));
				parent.setParentName(rs.getString("parentName"));
				parent.setParentEmail(rs.getString("parentEmail"));
				parent.setParentPhnNo(rs.getString("parentPhnNo"));
				parent.setRelationToStudent(rs.getString("relationToStudent"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return parent;
	}

	@Override
	public boolean updateParent(Parent parent) {
		boolean updated = false;
		String query = "UPDATE parent SET parentName = ?, parentEmail = ?, parentPhnNo = ?, relationToStudent = ? WHERE studentId = ?";

		try (Connection con = DBConnect.getConn();
				PreparedStatement stmt = con.prepareStatement(query)) {

			stmt.setString(1, parent.getParentName());
			stmt.setString(2, parent.getParentEmail());
			stmt.setString(3, parent.getParentPhnNo());
			stmt.setString(4, parent.getRelationToStudent());
			stmt.setInt(5, parent.getStudentId());

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				updated = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return updated;
	}
}