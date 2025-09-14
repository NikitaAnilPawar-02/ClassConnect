 package com.DB;

 import java.sql.Connection;

 public class DBTest {
 public static void main(String[] args) {
 Connection con = DBConnect.getConn();
 if (con != null) {
 System.out.println("Connection is successful!");
 } else {
 System.out.println("Connection failed.");
 }
 }
 }
