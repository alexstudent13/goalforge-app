package project.goalforge.controller;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import project.goalforge.models.Task;
import project.goalforge.models.User;
import project.goalforge.service.TaskService;
import project.goalforge.service.UserService;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class TaskController {
    private final TaskService taskService; //Service for task operations
    private final UserService userService; //Service for user operations
    private final JavaMailSender javaMailSender; //Java mail sender service for email operations

    @Autowired //Injection of services with dependency
    public TaskController(TaskService taskService, UserService userService, JavaMailSender javaMailSender){
        this.taskService = taskService;
        this.userService = userService;
        this.javaMailSender = javaMailSender;
    }

    //View and return view all tasks page
    @GetMapping("/viewall-tasks")
    public String viewAllTasks(@RequestParam(name = "category", required = false) String category, Model model) {
        List<String> categories = Arrays.asList("Fitness", "Education", "Home", "Finance", "Health", "Travel", "Social", "Hobby", "Relationships", "Business");
        model.addAttribute("categories", categories);

        List<Task> tasks;
        if (category != null && !category.isEmpty()) {
            tasks = taskService.getAllTasks().stream()
                    .filter(task -> task.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
            model.addAttribute("selectedCategory", category);
        } else {
            tasks = taskService.getAllTasks();
        }
        model.addAttribute("tasks", tasks);

        return "viewall-tasks";
    }

    //View and return view all tasks page based on category picked
    @GetMapping("/view-by-category")
    public String viewTasksByCategory(@RequestParam(name = "category", required = false) String category,
                                      @RequestParam(name = "priority", required = false) String priority,
                                      Model model) {
        List<Task> tasks;

        // Check if category or priority is selected
        if (category != null && !category.isEmpty()) {
            tasks = taskService.getAllTasks().stream()
                    .filter(task -> task.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
            model.addAttribute("selectedCategory", category);
        } else if (priority != null && !priority.isEmpty()) {
            tasks = taskService.getAllTasks().stream()
                    .filter(task -> task.getPriority().equalsIgnoreCase(priority))
                    .collect(Collectors.toList());
            model.addAttribute("selectedPriority", priority);
        } else {
            // No filter applied, show all tasks
            tasks = taskService.getAllTasks();
        }

        //Add tasks to model based on category/priority picked
        model.addAttribute("tasks", tasks);

        // Add categories and priorities to the model
        List<String> categories = Arrays.asList("Fitness", "Education", "Home", "Finance", "Health", "Travel", "Social", "Hobby", "Relationships", "Business");
        List<String> priorities = Arrays.asList("High", "Medium", "Low");

        model.addAttribute("categories", categories);
        model.addAttribute("priorities", priorities);

        return "viewall-tasks";
    }

    //View and return tasks by priority
    @GetMapping("/view-by-priority")
    public String viewTasksByPriority(@RequestParam(name = "priority") String priority, Model model) {
        //List out all the tasks based on their priority, and then we add the tasks based on their priority picked to the model
        List<Task> tasks = taskService.getAllTasks().stream()
                .filter(task -> task.getPriority().equalsIgnoreCase(priority))
                .collect(Collectors.toList());
        model.addAttribute("tasks", tasks);
        model.addAttribute("selectedPriority", priority);

        // Add priorities to the model
        List<String> priorities = Arrays.asList("High", "Medium", "Low");
        model.addAttribute("priorities", priorities);

        // Add categories to the model
        List<String> categories = Arrays.asList("Fitness", "Education", "Home", "Finance", "Health", "Travel", "Social", "Hobby", "Relationships", "Business");
        model.addAttribute("categories", categories);

        return "viewall-tasks";
    }

    //View and return the new task page
    @GetMapping("/new-task")
    public String showNewTaskPage(Model model) {
        //Add categories to the model for viewing in the new task page
        List<String> categories = Arrays.asList("Fitness", "Education", "Home", "Finance", "Health", "Travel", "Social", "Hobby", "Relationships", "Business");
        model.addAttribute("categories", categories);
        //Add the new task to the model
        model.addAttribute("task", new Task());
        return "new-task";
    }

    //Handle form submission of the new task submission
    @PostMapping("/save")
    public String saveTask(@ModelAttribute("task") Task task,
                           @RequestParam(defaultValue = "off") String notifications,
                           RedirectAttributes redirectAttributes) {
        // Log the actual value received from the checkbox for debugging
        System.out.println("Checkbox value: " + notifications);

        task.setWantsNotifications("on".equals(notifications));

        System.out.println("Notifications enabled: " + task.isWantsNotifications());

        // Save the task
        Task savedTask = taskService.saveTask(task);

        // Generate the task URL using the task ID
        String taskUrl = "/task-details/" + savedTask.getTaskId();

        // Check if the user wants notifications
        if (savedTask.isWantsNotifications()) {
            //We get the user's email based on their ID
            String userEmail = taskService.getUserEmailByUserId(savedTask.getUserId());
            System.out.println("Sending email to: " + userEmail);
            //We use the sendTaskEmail method with user's email, a task url link, and the task's name with token
            sendTaskEmail(userEmail, taskUrl, savedTask.getTaskName(), "Token Info");
        } else {
            System.out.println("Notifications are disabled for this task.");
        }

        //Create the task with the generated url and save the task to the task service class
        savedTask.setTaskUrl(taskUrl);
        taskService.saveTask(savedTask);

        redirectAttributes.addFlashAttribute("successMessage", "Task created successfully!");
        return "redirect:/dashboard-page";
    }

    //View and return progress dashboard page
    @GetMapping("/progress-dashboard")
    public String showProgressDashboard(Model model) {
        //We get the current user's tasks if they are logged in and count the tasks by their status
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            long notStarted = taskService.countTasksByStatus("Not Started");
            long inProgress = taskService.countTasksByStatus("In Progress");
            long completed = taskService.countTasksByStatus("Completed");
            long totalTasks = notStarted + inProgress + completed;

            // If there are no tasks, set default values for each status
            if (totalTasks == 0) {
                notStarted = 0;
                inProgress = 0;
                completed = 0;
                totalTasks = 1; // Set totalTasks to 1 to avoid division by zero
            }

            //We add the tasks and their status to the model
            model.addAttribute("notStarted", notStarted);
            model.addAttribute("inProgress", inProgress);
            model.addAttribute("completed", completed);
            model.addAttribute("totalTasks", totalTasks);
            return "progress-dashboard";
        }
        return "redirect:/login"; // Redirect to login page if not authenticated
    }

    //View and return the task details of a task
    @GetMapping("/task-details/{taskId}")
    public String showTaskDescription(@PathVariable Long taskId, Model model) {
        //Verify if user is logged in to view their task's details
        User loggedInUser = userService.getCurrentUser();
        if (loggedInUser != null) {
            //We get the task based on their ID using the task service class
            Task task = taskService.getTaskById(taskId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + taskId));
            //We add the task retrieved to the model for viewing
            model.addAttribute("task", task);
            return "task-details";
        } else {
            return "redirect:/login"; // Redirect to your login page if not authenticated
        }
    }

    //View and return edit form of a task
    @GetMapping("/edit/{taskId}")
    public String showEditTaskForm(@PathVariable Long taskId, Model model) {
        //Verify if user is logged in to edit their task
        User loggedInUser = userService.getCurrentUser();
        if (loggedInUser != null) {
            //We get the task based on their ID using the task service class
            Task task = taskService.getTaskById(taskId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + taskId));
            //We add the task retrieved to the model for viewing
            model.addAttribute("task", task);

            // Added the list of categories to the model for rendering the dropdown
            List<String> categories = Arrays.asList("Fitness", "Education", "Home", "Finance", "Health", "Travel", "Social", "Hobby", "Relationships", "Business");
            model.addAttribute("categories", categories);

            return "edit-task";
        } else {
            return "redirect:/login"; // Redirect to your login page if not authenticated
        }
    }

    //Handle form submission of editing task
    @PostMapping("/update")
    public String updateTask(@ModelAttribute("task") Task updatedTask, RedirectAttributes redirectAttributes, Model model) {
        // Get the existing task from the database
        Task existingTask = taskService.getTaskById(updatedTask.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + updatedTask.getTaskId()));

        // Update the existing task with the new information
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setPriority(updatedTask.getPriority());
        existingTask.setTaskStatus(updatedTask.getTaskStatus());
        existingTask.setCategory(updatedTask.getCategory());

        // Save the updated task
        taskService.saveTask(existingTask);

        // Added the list of categories to the model for rendering the dropdown
        List<String> categories = Arrays.asList("Fitness", "Education", "Home", "Finance", "Health", "Travel", "Social", "Hobby", "Relationships", "Business");
        model.addAttribute("categories", categories);

        redirectAttributes.addFlashAttribute("successMessage", "Task updated successfully!");
        return "redirect:/dashboard-page";
    }

    //View and return the page based on tasks deleted
    @GetMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable Long taskId, RedirectAttributes redirectAttributes) {
        //We delete the tasks based on their ID using the task service class
        taskService.deleteTaskById(taskId);
        //We then redirect the user to a new page viewing with their tasks deleted
        redirectAttributes.addFlashAttribute("successMessage", "Task deleted successfully!");
        return "redirect:/dashboard-page";
    }

    //Handle form submission for deleting tasks
    @PostMapping("/delete-multiple")
    public ResponseEntity<?> deleteMultipleTasks(@RequestBody Map<String, List<Long>> requestBody) {
        //List the tasks out for user to view based on their id
        List<Long> taskIds = requestBody.get("taskIds");
        //Delete the task based on their ID
        taskService.deleteTasksByIds(taskIds);
        return ResponseEntity.ok("Selected tasks deleted successfully!");
    }

    //Method responsible for sending the email with generated task link, task's name, and user's email
    public void sendTaskEmail(String recipientEmail, String taskLink, String taskName, String token) {
        try {
            // Generate a unique random string as the identifier
            String uniqueIdentifier = generateRandomString(12);

            //Generate new email message
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            //Set the recipientEmail to the email that we got when saving a new task
            helper.setTo(recipientEmail);
            //Set the subject of the email with the unique token generated to generate a unique email everytime
            helper.setSubject("Goal-Forge: Task Notification! - " + uniqueIdentifier);
            //Inject the task's name and link to the email's body content
            helper.setText(buildEmailContent(taskName, taskLink), true);  // Assuming HTML content

            //Method that uses java mail service email operations to send email
            javaMailSender.send(message);
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            System.out.println("Error while sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Here we build the email's body content
    private String buildEmailContent(String taskName, String taskLink) {
        //Set the base url to be of the website
        String baseURL = "http://goalforged.com";
        //Extend from the base url with the task's link to view the task
        String fullTaskLink = baseURL + taskLink;
        return "<p>You signed up for notifications for task: " + taskName + "</p>" +
                "<p>Check out your task details here: <a href='" + fullTaskLink + "'>View Task</a></p>";
    }
    //Method responsible for creating a random string of specified length with random characters and numbers
    private String generateRandomString(int length) {
        // Generate a random string of specified length using SecureRandom and Base64 encoding
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}

