<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%
    if (session == null || session.getAttribute("userType") == null) {
        response.sendRedirect("../login.jsp");
        return;
    }

    String userType = (String) session.getAttribute("userType");
    if (!"Admin".equals(userType)) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>
<%@ page import="java.sql.*, java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ClassConnect: Notices</title>
<%@include file="/all_components/allCSS.css"%>
</head>
<body>

	<!-- Sidebar -->
	<%@include file="/all_components/sidebar.jsp"%>

	<!-- Main Content -->
	<div class="content">
		<!-- Navbar -->
		<%@include file="/all_components/navbar.jsp"%>

		<div class="container mt-4">
			<h2 class="text-center">Notices</h2>
			<hr>

			<%
            String successMsg = (String) session.getAttribute("successMsg");
            String errorMsg = (String) session.getAttribute("errorMsg");

            if (successMsg != null) {
        %>
			<div
				class="alert alert-success alert-dismissible fade show text-center"
				role="alert">
				<%= successMsg %>
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>
			<%
                session.removeAttribute("successMsg");
            } else if (errorMsg != null) {
        %>
			<div
				class="alert alert-danger alert-dismissible fade show text-center"
				role="alert">
				<%= errorMsg %>
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>
			<%
                session.removeAttribute("errorMsg");
            }
        %>

			<%
            List<Map<String, String>> notices = new ArrayList<>();
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conn = com.DB.DBConnect.getConn();
                String sql = "SELECT * FROM notices";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    Map<String, String> notice = new HashMap<>();
                    notice.put("id", rs.getString("noticeId"));  
                    notice.put("title", rs.getString("title"));
                    notice.put("description", rs.getString("description"));
                    notice.put("date_posted", rs.getString("date_posted"));
                    notices.add(notice);
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMsg", "Error fetching notices from the database.");
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        %>


			<div class="row mt-4">
				<div class="col-md-12">
					<div class="card shadow">
						<div class="card-header">Manage Notices</div>
						<div class="card-body">
						
							<form action="AddNoticeServlet" method="POST">
								<div class="mb-3">
									<label for="noticeTitle" class="form-label">Notice
										Title</label> <input type="text" class="form-control" id="noticeTitle"
										name="noticeTitle" required>
								</div>
								<div class="mb-3">
									<label for="noticeDescription" class="form-label">Notice
										Description</label>
									<textarea class="form-control" id="noticeDescription"
										name="noticeDescription" rows="3" required></textarea>
								</div>
								<button type="submit" class="btn btn-primary">Add
									Notice</button>
							</form>

							<h5 class="mt-4">Existing Notices</h5>
							<table class="table table-bordered mt-3">
								<thead>
									<tr>
										<th>Title</th>
										<th>Description</th>
										<th>Date Posted</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<% if (notices.isEmpty()) { %>
									<tr>
										<td colspan="4" class="text-center">No Notices Found</td>
									</tr>
									<% } else { %>
									<% for (Map<String, String> notice : notices) { %>
									<tr>
										<td><%= notice.get("title") %></td>
										<td><%= notice.get("description") %></td>
										<td><%= notice.get("date_posted") %></td>
										<td><a
											href="DeleteNoticeServlet?id=<%= notice.get("id") %>"
											class="btn btn-danger btn-sm">Delete</a></td>
									</tr>
									<% } %>
									<% } %>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
