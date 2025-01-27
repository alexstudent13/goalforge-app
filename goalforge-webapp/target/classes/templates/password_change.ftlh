<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoalForge - Password Change</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootswatch@5.3.2/dist/morph/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div>
    <h2>Reset Your Password</h2>
</div>
<form action="/reset_password" method="post" style="max-width: 350px; margin: 0 auto;">
    <input type="hidden" name="token" value="${token}" />
    <div class="border border-secondary rounded p-3">
        <div class="input-group">
            <input type="password" name="password" id="password" class="form-control"
                   placeholder="Enter your new password" required autofocus />
            <button type="button" id="togglePassword" class="btn btn-outline-secondary" onclick="togglePasswordVisibility()">
                <i id="eyeIcon" class="bi bi-eye"></i>
            </button>
        </div>
        <p>
            <input type="password" id="confirmPassword" class="form-control" placeholder="Confirm your new password"
                   required oninput="checkPasswordMatch()" />
        </p>
        <p class="text-center">
            <input type="submit" value="Change Password" class="btn btn-success" />
        </p>
    </div>
</form>

<!-- Page Footer -->
<footer class="container mt-4 border-top pt-3">
    <p class="text-center">&#169; 2024 GoalForge USA, All rights reserved.</p>
</footer>

<!-- Javascript Code -->
<script>
    function checkPasswordMatch() {
        var password = document.getElementById("password");
        var confirmPassword = document.getElementById("confirmPassword");
        if (password.value != confirmPassword.value) {
            confirmPassword.setCustomValidity("Passwords do not match!");
        } else {
            confirmPassword.setCustomValidity("");
        }
    }

    function togglePasswordVisibility() {
        var passwordInput = document.getElementById("password");
        var eyeIcon = document.getElementById("eyeIcon");

        if (passwordInput.type === "password") {
            passwordInput.type = "text";
            eyeIcon.classList.remove("bi-eye");
            eyeIcon.classList.add("bi-eye-slash");
        } else {
            passwordInput.type = "password";
            eyeIcon.classList.remove("bi-eye-slash");
            eyeIcon.classList.add("bi-eye");
        }
    }
</script>

</body>
</html>
