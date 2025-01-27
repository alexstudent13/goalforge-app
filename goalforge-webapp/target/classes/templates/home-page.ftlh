<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Goal Forge - Home Page</title>
    <link href="/css/css/home-page.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" integrity="sha512-..." crossorigin="anonymous" />
</head>
<body>
<header>
    <h1><img src="https://i.imgur.com/d2LY1dA.png" class="logo"></h1>
    <nav>
        <a href="/" class="active"><i class="fas fa-home"></i> Home</a>
        <a href="/login"><i class="fas fa-portrait"></i> Login</a>
        <a href="/signup"><i class="fas fa-sign-in-alt"></i> Sign-Up</a>
    </nav>
</header>

<section class="home">
    <h1 class="title">Goal-Forge</h1>
</section>

<section class="about">
    <h1>What is Goal Forge?</h1>
    <br>
    <p>Welcome to Goal-Forge, your ultimate task management/agenda-planning platform!
        Here at Goal-Forge, we provide our users with the power to efficiently
        organize their day-to-day tasks and stay on top on their schedules.</p>
    <br>
    <p>With the power of Goal-Forge, you can seamlessly create tasks, set due dates, and prioritize your activities.
        Our intuitive interface makes it easy to manage your to-dos, ensuring that nothing slips between the cracks.
        Whether you are a student, professional, or simply someone who needs helps with keeping a track of your daily
        activities, Goal-Forge is your go-to tool for staying organize and productive.</p>
    <br>
    <p>
        Key Features of Goal-Forge include:
        1. Task Creation: Easily Create Tasks with detailed descriptions and due dates
        2. Agenda View: Get a comprehensive view of your upcoming tasks and events.
        3. Priority Management: Prioritize tasks based on urgency and importance
        4. Flexible Scheduling: Plan tasks for specific days, weeks, months, or even long-term goals
        5. Notifications; Stay informed with reminders and notifications for approaching deadlines, so you are always a step ahead.
    </p>
    <br>
    <p>
        Join Goal-Forge today and let's forge your path to success together!
    </p>
    <br>
    <p class="text-center">&#169; 2024 GoalForge USA, All rights reserved.</p>
</section>

<script>
    const title = document.querySelector('.title')

    document.addEventListener('scroll', function() {
        let value = window.scrollY
        title.style.marginTop = value * 1.1 + 'px'
    })
</script>

</body>
</html>
