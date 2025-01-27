<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoalForge - Login</title>
    <link href="/css/css/home-page.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" integrity="sha512-..." crossorigin="anonymous" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootswatch@5.3.2/dist/morph/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(to right, #008DDA, #41C9E2, #ACE2E1);
        }

        .login-container {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        .login-form {
            max-width: 800px;
            width: 100%;
            padding: 40px;
        }

        .logo-container {
            width: 100%;
            text-align: center;
            margin-bottom: 40px;
        }

        .logoBig {
            width: 600px;
            height: auto;
        }

        .create-account-link a {
            color: white;
        }

        @media (max-width: 768px) {
            .login-form {
                padding: 20px;
            }

            .logoBig {
                width: 120px;
            }
        }
    </style>
</head>

<body>
<header>
    <h1><img src="https://i.imgur.com/d2LY1dA.png" class="logo"></h1>
    <nav>
        <a href="/"><i class="fas fa-home"></i> Home</a>
        <a href="/login" class="active"><i class="fas fa-portrait"></i> Login</a>
        <a href="/signup"><i class="fas fa-sign-in-alt"></i> Sign-Up</a>
    </nav>
</header>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-6 d-none d-md-block">
            <div class="login-container">
                <div class="logo-container">
                    <img src="https://i.imgur.com/d2LY1dA.png" alt="Logo" class="logoBig">
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="login-container">
                <div class="login-form">
                    <h5 class="mb-4 text-center">
                        <#if logoutMessage?exists>
                            <div class="success-message">
                                <h2 class="text-dark">${logoutMessage}</h2>
                            </div>
                        </#if>
                        <#if errorMessage?exists>
                            <div class="error-message">
                                <h3 class="text-dark">${errorMessage}</h3>
                            </div>
                        </#if>
                        <#if message??>
                            <div>
                                <h4 class="text-dark">${message}</h4>
                            </div>
                        </#if>
                        Goal Forge - Login to Your Account
                    </h5>
                    <form action="/login" method="post">
                        <div class="mb-3">
                            <label for="email" class="form-label">Email Address</label>
                            <input type="email" class="form-control" id="email" name="email" required>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <div class="input-group">
                                <input type="password" class="form-control" id="password" name="password"
                                       required>
                                <button class="btn btn-outline-secondary" type="button" id="showPasswordBtn">Show
                                    Password</button>
                            </div>
                        </div>
                        <div class="text-center">
                            <a href="/forgot_password">Forgot Password?</a>
                        </div>
                        <div class="text-center">
                            <button type="submit" class="btn btn-dark">Login <i class="bi bi-send"></i> </button>
                            <#if error?exists>
                                <div class="error-message">
                                    <p class="text-dark">${error}</p>
                                </div>
                            </#if>
                        </div>
                    </form>
                    <div class="mt-3 text-center create-account-link">
                        <p>Don't have an account? <a href="signup" style="color: white;">Create One</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Page Footer -->
<footer class="container mt-4 border-top pt-3">
    <p class="text-center">&#169; 2024 GoalForge USA, All rights reserved.</p>
</footer>

<script>
    const passwordInput = document.getElementById('password');
    const showPasswordBtn = document.getElementById('showPasswordBtn');

    showPasswordBtn.addEventListener('click', () => {
        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            showPasswordBtn.textContent = 'Hide Password';
        } else {
            passwordInput.type = 'password';
            showPasswordBtn.textContent = 'Show Password';
        }
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-dYUxMbk3oPmZE9KfNVCjzxxqDsoz5rwNzowz5sueWnG7sAVBPTtgr/CgkdqNjqTx" crossorigin="anonymous">
</script>

</body>
</html>