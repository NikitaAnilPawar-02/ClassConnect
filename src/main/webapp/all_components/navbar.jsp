<nav class="navbar navbar-expand-lg navbar-light bg-light mb-4">
	<div class="container-fluid">
		<a class="navbar-brand" href="admin/dashboard.jsp">Dashboard</a>
		<div class="ms-auto">
			<span class="me-3">Welcome, Admin</span>
			<button type="button" class="btn btn-outline-danger"
				data-bs-toggle="modal" data-bs-target="#logoutModal">
				Logout</button>
		</div>
	</div>
</nav>
<div class="modal fade" id="logoutModal" tabindex="-1"
	aria-labelledby="logoutModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="logoutModalLabel">
					<i class="bi bi-box-arrow-right"></i> Confirm Logout
				</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<p>Are you sure you want to log out?</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">Cancel</button>
				<a href="../LogoutServlet" class="btn btn-danger">Logout</a>
			</div>
		</div>
	</div>
</div>
