<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
    <link href="/css/css/theme.css" rel="stylesheet">
    <link href="/css/css/google.css" rel="stylesheet">
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
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.12.0/css/all.css">
    <title>Google Calendar</title>
    <meta charset="utf-8" />
    <style>
        form {
            margin: 10px;
        }

        fieldset {
            width: fit-content;
        }

        input {
            margin-block: 10px;
        }
        [data-theme="light"] {
            --event-text-color: black; /* Adjusted for light theme */
        }

        [data-theme="dark"] {
            --event-text-color: black; /* Adjusted for dark theme */
        }

        #content, .input-title {
            color: var(--event-text-color);
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
<h2>Create events through <i class="fab fa-google"></i>oogle Calendar</h2>
<div class="alert alert-dismissible alert-primary" style="text-align: center;">
    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    <strong>To use Google Calendar authorize yourself with a Google account to create events. After you have set up your event, click on schedule and then click on refresh to see your new created events!</strong>
</div>

<!--Add buttons to initiate auth sequence and sign out-->
<button id="authorize_button" onclick="handleAuthClick()">Authorize</button>
<button id="signout_button" onclick="handleSignoutClick()">Sign Out</button>
<br />
<form id="event_form">
    <fieldset>
        <input type="text" name="title" id="title" placeholder="Add Title" class="input-title" />
        <br />
        <textarea type="text" name="desc" id="desc" placeholder="Add Description" class="input-title"></textarea>
        <br />
        <label>Date</label>
        <input type="date" name="date" id="date" />
        <div>
            <label>Start Time</label>
            <input type="time" name="st" id="st" />
            <label>End Time</label>
            <input type="time" name="et" id="et" />
        </div>
        <button type="button" onclick="addEvent()">Schedule</button>
    </fieldset>
</form>

<!-- Link to View Google Calendar -->
<span id="calendarLink" style="display: none;">View your <a href="https://calendar.google.com/calendar/" target="_blank" rel="noopener noreferrer">Google Calendar</a>.</span>

<pre id="content" style="white-space: pre-wrap;"></pre>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous">
</script>
<script src="/css/css/index.js" type="text/javascript"></script>
<script src="/css/css/theme.js"></script>
<script async defer src="https://apis.google.com/js/api.js" onload="gapiLoaded()"></script>
<script async defer src="https://accounts.google.com/gsi/client" onload="gisLoaded()"></script>
</body>

</html>