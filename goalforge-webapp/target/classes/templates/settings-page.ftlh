<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoalForge - Settings</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <!-- Bootswatch Theme (Morph) -->
    <link href="https://cdn.jsdelivr.net/npm/bootswatch@5.3.2/dist/morph/bootstrap.min.css" rel="stylesheet">
    <link href="/css/css/theme.css" rel="stylesheet">
    <style>
        .box {
            display: grid;
            background-color: aliceblue;
            border-radius: 16px;
            padding: 20px 0px;
        }
        .container {
            padding: 20px;
        }
        .tbl-col {
            display: flex;
            padding: 10px 0px;
        }
        .tbl-col span {
            transition: transform 0.5s;
        }
        .tbl-col:hover span {
            transform: translateX(10px);
        }
        .tbl-col a {
            transition: transform 0.5s;
        }
        .tbl-col:hover a {
            font-weight: 600;
        }
        .inner-box {
            padding-left: 40%;
            padding-right: 40%;
        }
        .dropdown {
            display: none;
            padding: 10px;
        }
        .show {
            display: block;
        }
    </style>
</head>
<body>

<!-- Navigation Bar -->
<nav class="navbar navbar-expand-lg navbar-light bg-dark" data-bs-theme="dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="/dashboard-page"><i class="bi bi-person-fill"></i> Goal Forge </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="/dashboard-page"><i class="bi bi-house-door"></i> Home </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/progress-dashboard"><i class="bi bi-calendar3"></i> Progress Dashboard </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/googlecalendar-page"><i class="bi bi-google"></i> Google Calendar</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/viewall-tasks"><i class="bi bi-list-task"></i> View All Tasks </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/settings-page"><i class="bi bi-person-fill-gear"></i> Settings </a>
                </li>
            </ul>
            <form class="d-flex">
                <button class="btn btn-outline-secondary" type="button" id="theme-toggle">
                    <i class="bi" id="theme-icon"></i> Toggle Theme
                </button>
            </form>
            <form class="d-flex" action="/logout" method="post">
                <button class="btn btn-outline-danger me-2" type="submit"><i class="bi bi-door-open"></i> Logout </button>
            </form>
        </div>
    </div>
</nav>

<!-- settings box -->
<div class="container">
    <h2>Settings</h2>
    <#if errorMessage??>
        <div class="alert alert-dismissible alert-danger">
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            <strong>Oops!</strong> <p class="text-dark">${errorMessage}</p>
        </div>
    </#if>
    <#if message??>
        <div class="alert alert-dismissible alert-success">
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            <strong>${message}</strong>
        </div>
    </#if>
    <div class="box">
        <div class="inner-box">
            <!-- Change Name -->
            <div class="tbl-col">
                <a href="#" onclick="toggleDropdown('changeNameDropdown', event)">Change Name</a>
                <span>&nbsp></span>
                <div id="changeNameDropdown" class="dropdown">
                    <form action="/change-name" method="post">
                        <div class="form-group">
                            <label for="firstName">First Name:</label>
                            <input type="text" id="firstName" name="firstName" required>
                        </div>
                        <div class="form-group">
                            <label for="lastName">Last Name:</label>
                            <input type="text" id="lastName" name="lastName" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Save</button>
                    </form>
                </div>
            </div>

            <!-- Change Email -->
            <div class="tbl-col">
                <a href="#" onclick="toggleDropdown('changeEmailDropdown', event)">Change Email</a>
                <span>&nbsp></span>
                <div id="changeEmailDropdown" class="dropdown">
                    <form action="/change-email" method="post">
                        <div class="form-group">
                            <label for="newEmail">New Email:</label>
                            <input type="email" id="newEmail" name="newEmail" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Save</button>
                    </form>
                </div>
            </div>

            <!-- Change Password Dropdown -->
            <div class="tbl-col">
                <a href="#" onclick="toggleDropdown('changePasswordDropdown', event)">Change Password</a>
                <span>&nbsp></span>
                <div id="changePasswordDropdown" class="dropdown">
                    <form action="/change-password" method="post">
                        <div class="form-group">
                            <label for="currentPassword">Current Password:</label>
                            <input type="password" id="currentPassword" name="currentPassword" required>
                        </div>
                        <div class="form-group">
                            <label for="newPassword">New Password:</label>
                            <input type="password" id="newPassword" name="newPassword" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Change Password</button>
                    </form>
                </div>
            </div>

            <!-- Delete Account Dropdown -->
            <div class="tbl-col">
                <a href="#" onclick="toggleDropdown('deleteAccountDropdown', event)">Delete Account</a>
                <span>&nbsp></span>
                <div id="deleteAccountDropdown" class="dropdown">
                    <form action="/delete-account" method="post">
                        <div class="form-group">
                            <label for="confirmDelete">Confirm Deletion:</label>
                            <input type="checkbox" id="confirmDelete" name="confirmDelete" required>
                        </div>
                        <button type="submit" class="btn btn-danger">Delete Account</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Page Footer -->
<footer class="container mt-4 border-top pt-3">
    <p class="text-center">&#169; 2024 GoalForge USA, All rights reserved.</p>
</footer>

<!-- Bootstrap JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<!-- JavaScript for Dropdown Toggle -->
<script>
    function toggleDropdown(dropdownId, event) {
        event.preventDefault(); // Prevent the default link behavior

        const dropdown = document.getElementById(dropdownId);

        // Close any open dropdown
        const openDropdown = document.querySelector('.dropdown.show');
        if (openDropdown && openDropdown !== dropdown) {
            openDropdown.classList.remove('show');
        }

        dropdown.classList.toggle('show');
    }
</script>
<script src="/css/css/theme.js"></script>

</body>
</html>