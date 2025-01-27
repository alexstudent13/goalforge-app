<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoalForge - Progress Dashboard</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
          rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootswatch@5.3.2/dist/morph/bootstrap.min.css"
          rel="stylesheet">
    <link href="/css/css/theme.css" rel="stylesheet">
    <style>
        .status-icon {
            width: 1.5rem;
            height: 1.5rem;
            vertical-align: bottom;
        }
        .progress-bar {
            transition: width 1s ease-in-out;
        }
        .box {
            padding: 40px;
            background-color: #f8f9fa;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
            margin-bottom: 40px;
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

<div class="container mt-4">
    <h1>Progress Dashboard</h1>
    <div class="row">
        <div class="col-md-4">
            <div class="box">
                <h4>Not Started <i class="bi bi-x-circle-fill text-danger status-icon"></i></h4>
                <div class="progress">
                    <div class="progress-bar bg-danger" role="progressbar"
                         style="width: ${totalTasks?has_content?then((notStarted / totalTasks * 100)?string['0.##'] + '%', '0%')};"                         aria-valuenow="${notStarted}"
                         aria-valuemin="0" aria-valuemax="100">
                        ${notStarted}
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="box">
                <h4>In Progress <i class="bi bi-arrow-repeat text-warning status-icon"></i></h4>
                <div class="progress">
                    <div class="progress-bar bg-warning" role="progressbar"
                         style="width: ${totalTasks?has_content?then((inProgress / totalTasks * 100)?string['0.##'] + '%', '0%')};"
                         aria-valuenow="${inProgress}"
                         aria-valuemin="0" aria-valuemax="100">
                        ${inProgress}
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="box">
                <h4>Completed <i class="bi bi-check-circle-fill text-success status-icon"></i></h4>
                <div class="progress">
                    <div class="progress-bar bg-success" role="progressbar"
                         style="width: ${totalTasks?has_content?then((completed / totalTasks * 100)?string['0.##'] + '%', '0%')};"
                         aria-valuenow="${completed}"
                         aria-valuemin="0" aria-valuemax="100">
                        ${completed}
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

<!-- Bootstrap JavaScript -->
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous">
</script>

<script src="/css/css/theme.js"></script>
</body>
</html>