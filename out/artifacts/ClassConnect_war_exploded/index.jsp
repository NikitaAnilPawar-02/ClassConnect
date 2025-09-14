<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gurukrupa Classes | Home</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            scroll-behavior: smooth; /* smooth scroll */
        }

        .hero {
            background: linear-gradient(rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0.6)),
            url('./images/classroom.jpg') center/cover no-repeat;
            color: white;
            text-align: center;
            padding: 100px 20px 80px 20px; /* extra top padding for fixed navbar */
        }

        .hero h1 {
            font-size: 3rem;
            font-weight: bold;
        }

        .section {
            padding: 60px 20px;
        }

        .btn-login {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            text-decoration: none;
            transition: 0.3s;
        }

        .btn-login:hover {
            background-color: #0056b3;
        }

        .card img {
            height: 200px;
            object-fit: cover;
        }

        .navbar a {
            color: white !important;
        }
        .navbar a:hover {
            color: #007bff !important;
        }
    </style>
</head>
<body>

<!-- Fixed Navbar -->
<nav class="navbar navbar-expand-lg bg-dark fixed-top">
    <div class="container">
        <a class="navbar-brand" href="#">Gurukrupa Classes</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item"><a class="nav-link" href="#home">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="#about">About</a></li>
                <li class="nav-item"><a class="nav-link" href="#whyus">Why Choose Us</a></li>
                <li class="nav-item"><a class="nav-link" href="#location">Location</a></li>
                <li class="nav-item"><a class="nav-link" href="login.jsp">Login</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- Hero Section -->
<section class="hero" id="home">
    <h1>Welcome to Gurukrupa Classes</h1>
    <p>Your gateway to quality education for 8th to 10th standard students.</p>
    <a href="login.jsp" class="btn-login">Login</a>
</section>

<!-- About Section -->
<section class="section text-center" id="about">
    <h2>About Gurukrupa Classes</h2>
    <p>Gurukrupa Classes provides quality education to students from
        8th to 10th standard. We are known for experienced teachers, a
        student-friendly environment, and a strong focus on academics. Our
        coaching institute helps students achieve their best potential while
        maintaining discipline and curiosity.</p>
</section>

<!-- Cards Section -->
<section class="section bg-light" id="whyus">
    <div class="container">
        <h2 class="text-center mb-4">Why Choose Us?</h2>
        <div class="row">
            <div class="col-md-4">
                <div class="card">
                    <img src="./images/books.jpg" class="card-img-top" alt="Subjects">
                    <div class="card-body">
                        <h5 class="card-title">Comprehensive Subjects</h5>
                        <p class="card-text">We provide coaching for Mathematics,
                            Science, English, and more.</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <img src="./images/teacher.jpg" class="card-img-top" alt="Teachers">
                    <div class="card-body">
                        <h5 class="card-title">Experienced Teachers</h5>
                        <p class="card-text">Learn from highly qualified and
                            experienced educators.</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <img src="./images/student.jpg" class="card-img-top" alt="Success">
                    <div class="card-body">
                        <h5 class="card-title">Student Success</h5>
                        <p class="card-text">Our students consistently achieve top
                            ranks in their exams.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- Location Section -->
<section class="section text-center" id="location">
    <h2>Our Location</h2>
    <p>
        <strong>Ambegaon Pathar, Suvarnayug Nagar, Pune, Maharashtra 411043</strong>
    </p>
    <iframe width="100%" height="300" frameborder="0" style="border: 0"
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d562.561979222544!2d73.84609567442907!3d18.463228086121838!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3bc2ebe85dc56e27%3A0x827c58344de90ebe!2sSAMARTH%20PUBLICITY!5e0!3m2!1sen!2sin!4v1742148277744!5m2!1sen!2sin"
            allowfullscreen> </iframe>
</section>

<!-- Footer -->
<footer class="text-center p-3 bg-dark text-white"> &copy; 2025 Gurukrupa Classes. All rights reserved. </footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
