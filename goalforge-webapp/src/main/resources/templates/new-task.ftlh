<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>New-Task</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">

    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
          rel="stylesheet">

    <!-- Bootswatch Theme (Morph) -->
    <link href="https://cdn.jsdelivr.net/npm/bootswatch@5.3.2/dist/morph/bootstrap.min.css"
          rel="stylesheet">

    <link href="/css/css/theme.css" rel="stylesheet">
    <style>
        .container {
            margin-top: 50px;
        }
        .form-group {
            margin-bottom: 20px;
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

<div class="container">
    <h2>New Task</h2>
    <form action="/save" method="post">
        <!-- Task Name -->
        <div class="form-group">
            <label for="taskName">Task Name/Title:</label>
            <input type="text" id="taskName" name="taskName" class="form-control" required>
        </div>

        <!-- Task Description -->
        <div class="form-group">
            <label for="taskDescription">Description:</label>
            <textarea id="taskDescription" name="taskDescription" class="form-control" rows="5"></textarea>
        </div>

        <!-- Due Date -->
        <div class="form-group">
            <label for="dueDate">Due Date:</label>
            <input type="date" id="dueDate" name="dueDate" class="form-control" required>
        </div>

        <!-- Priority -->
        <div class="form-group">
            <label for="priority">Priority:</label>
            <select id="priority" name="priority" class="form-control" required>
                <option value="High">High</option>
                <option value="Medium">Medium</option>
                <option value="Low">Low</option>
            </select>
        </div>

        <!-- Task Status -->
        <div class="form-group">
            <label for="taskStatus">Status:</label>
            <select id="taskStatus" name="taskStatus" class="form-control" required>
                <option value="Not Started">Not Started</option>
                <option value="In Progress">In Progress</option>
                <option value="Completed">Completed</option>
            </select>
        </div>

        <!-- Task Category -->
        <div class="form-group">
            <label for="category">Category:</label>
            <select id="category" name="category" class="form-control" required>
                <#list categories as category>
                    <option value="${category}">${category}</option>
                </#list>
            </select>
        </div>

        <!-- Task Notifications -->
        <div class="form-group">
            <label for="notifications">Notifications:</label>
            <input id="notifications" class="form-check-input" type="checkbox" name="notifications">
        </div>

        <button type="submit" class="btn btn-outline-success">Save Task</button>
        <button type="button" class="btn btn-outline-danger" onclick="window.location.href='/dashboard-page'">Cancel</button>
    </form>
</div>

<!-- Page Footer -->
<footer class="container mt-4 border-top pt-3">
    <p class="text-center">&#169; 2024 GoalForge USA, All rights reserved.</p>
</footer>

<!-- Bootstrap JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<script src="/css/css/theme.js"></script>

</body>
</html>