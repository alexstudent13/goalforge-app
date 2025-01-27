<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoalForge - Sign-Up</title>
    <link href="/css/css/signup.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
          integrity="sha512-..." crossorigin="anonymous"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootswatch@5.3.2/dist/morph/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(to right, #008DDA, #41C9E2, #ACE2E1);
        }
    </style>
</head>

<body>
<header style="height: 80px; padding: 10px;">
    <h1><img src="https://i.imgur.com/d2LY1dA.png" class="logo"></h1>
    <nav>
        <a href="/"><i class="fas fa-home"></i> Home</a>
        <a href="/login"><i class="fas fa-portrait"></i> Login</a>
        <a href="/signup" class="active"><i class="fas fa-sign-in-alt"></i> Sign-Up</a>
    </nav>
</header>
<div class="container-center">
    <div class="container-lg">
        <div class="row justify-content-center">
            <div class="col-md-8 glassmorphism-container">
                <h4 class="mb-4"> Goal Forge - Sign Up <i class="bi bi-person-fill-add"></i></h4>
                <form action="/signup" method="post">
                    <div id="errorMessage" class="alert alert-danger" style="display: none;">
                        User with this email already exists!
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="firstName" class="form-label">First Name</label>
                                <input type="text" class="form-control" id="firstName" name="firstName" required>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="lastName" class="form-label">Last Name</label>
                                <input type="text" class="form-control" id="lastName" name="lastName" required>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email" class="form-label">Email Address</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="password" class="form-label">Password</label>
                        <div class="input-group">
                            <input type="password" class="form-control" id="password" name="password"
                                   required>
                            <button class="btn btn-outline-secondary" type="button" id="showPasswordBtn">Show
                                Password
                            </button>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword" class="form-label">Confirm Password</label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
                               required>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="gender" class="form-label">Gender</label>
                                <select class="form-select" style="color: black" id="gender" name="gender" required>
                                    <option selected disabled>Select Gender</option>
                                    <option value="female">Female</option>
                                    <option value="male">Male</option>
                                    <option value="other">Other</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="dateOfBirth" class="form-label">Date of Birth</label>
                                <input type="date" class="form-control" id="dateOfBirth" name="dateOfBirth" required>
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-dark"> Sign Up <i class="bi bi-send"></i></button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Page Footer -->
<footer class="container mt-4 border-top pt-3">
    <p>&#169; 2024 GoalForge USA, All rights reserved.</p>
</footer>

<script>
    // Check if the error query parameter exists in the URL and display the error message if needed
    const urlParams = new URLSearchParams(window.location.search);
    const error = urlParams.get('error');
    if (error === 'exists') {
        document.getElementById('errorMessage').style.display = 'block';
    }
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirmPassword');
    const showPasswordBtn = document.getElementById('showPasswordBtn');

    showPasswordBtn.addEventListener('click', () => {
        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            confirmPasswordInput.type = 'text';
            showPasswordBtn.textContent = 'Hide Password';
        } else {
            passwordInput.type = 'password';
            confirmPasswordInput.type = 'password';
            showPasswordBtn.textContent = 'Show Password';
        }
    });
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-dYUxMbk3oPmZE9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
</script>
</body>