package com.DB;
import java.sql.*;

public class DBConnect {
	private static Connection conn;

	public static Connection getConn() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("MySQL JDBC Driver loaded successfully.");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/classconnect", "root", "1234");
			if (conn != null) {
				System.out.println("Database connection established.");
			}
		} catch (ClassNotFoundException e) {
			System.out
					.println("MySQL JDBC Driver not found: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Database connection error: " + e.getMessage());
		}
		return conn;
	}

	public static void main(String[] args) throws SQLException {
		Connection con = DBConnect.getConn();
		if (con != null) {
			System.out.println("Connection is successful!");
		} else {
			System.out.println("Connection failed.");
		}
	}
}