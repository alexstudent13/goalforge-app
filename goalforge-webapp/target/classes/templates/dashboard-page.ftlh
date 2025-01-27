<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoalForge - Dashboard</title>
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
        .card-title {
            margin-bottom: 0.5rem;
        }

        .card-text {
            margin-bottom: 0.2rem;
        }
        .custom-checkbox {
            position: absolute;
            top: 10px;
            right: 10px;
        }
        .welcome-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .welcome-container h1 {
            margin-right: 10px;
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

<!-- Page Header -->
<div class="welcome-container">
    <h1>Welcome, ${user.firstName}!</h1>
    <h1>Today's Date: <span id="todayDate"></span></h1>
</div>

<h3>Tasks Due This Week!</h3>

<div class="button-container d-flex justify-content-between align-items-center mb-3">
    <form class="delete-button-container" id="deleteForm" action="/delete-multiple" method="POST" enctype="multipart/form-data">
        <button type="button" class="btn btn-outline-danger" onclick="deleteSelected()">Delete Selected</button>
    </form>

    <div class="new-task-button">
        <button type="button" class="btn btn-outline-info" onclick="location.href='new-task'">New Task</button>
    </div>
</div>

<h6><strong>Check the box of tasks you want deleted:</strong></h6>

<div class="container">
    <div class="row g-3">
        <!-- Iterate over tasks and create a card for each task -->
        <#list tasks?sort_by(['dueDate']) as task>
            <div class="col-md-4 mb-4">
                <div class="card">
                    <input type="checkbox" class="custom-checkbox" name="taskIds" value="${task.taskId}">
                    <div class="card-body">
                        <h5 class="card-title">Task Name: ${task.taskName}</h5>
                        <p class="card-text">Due Date: ${task.dueDate}</p>
                        <p class="card-text">Priority: ${task.priority}</p>
                        <p class="card-text">Status: ${task.taskStatus}</p>
                        <p class="card-text">Category: ${task.category}</p>
                        <div class="d-flex justify-content-between align-items-center mt-3">
                            <a href="/task-details/${task.taskId}" class="btn btn-primary">View Details</a>
                            <a href="/edit/${task.taskId}" class="btn btn-outline-primary">Edit Task</a>
                        </div>
                    </div>
                </div>
            </div>
        </#list>
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

<!-- Delete task script-->
<script>
    function deleteSelected() {
        const checkboxes = document.querySelectorAll('input[name="taskIds"]:checked');
        if (checkboxes.length === 0) {
            alert("Please select at least one task to delete.");
        } else {
            if (confirm("Are you sure you want to delete the selected tasks?")) {
                const taskIds = Array.from(checkboxes).map(checkbox => checkbox.value);
                fetch('https://goalforged.com/delete-multiple', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ taskIds: taskIds })
                })
                    .then(response => {
                        if (response.ok) {
                            return response.text(); // If the response is successful, return the response text
                        } else {
                            throw new Error('Failed to delete tasks'); // Otherwise, throw an error
                        }
                    })
                    .then(data => {
                        alert(data); // Display the response message
                        window.location.href = '/dashboard-page'; // Redirect after successful operation
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert(error.message); // Display the error message
                    });
            }
        }
    }
    // Function to format the date as "Day, Month Date, Year"
    function formatDate(date) {
        const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
        return date.toLocaleDateString(undefined, options);
    }

    // Function to update the todayDate element with the current date
    function updateTodayDate() {
        const todayDateElement = document.getElementById('todayDate');
        if (todayDateElement) {
            const currentDate = new Date();
            const formattedDate = formatDate(currentDate);
            todayDateElement.textContent = formattedDate;
        }
    }

    // Call the updateTodayDate function when the page loads
    window.addEventListener('DOMContentLoaded', updateTodayDate);
</script>

<script src="/css/css/theme.js"></script>

</body>
</html>
