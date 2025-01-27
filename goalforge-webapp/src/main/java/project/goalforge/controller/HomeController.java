package project.goalforge.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.goalforge.models.Task;
import project.goalforge.models.User;
import project.goalforge.service.TaskService;
import project.goalforge.service.UserService;

import java.util.List;

@Controller
public class HomeController {

    private final UserService userService; //Service for user operations
    private final TaskService taskService; //Service for task operations
    @Autowired //Injection of services with dependency
    public HomeController(UserService userService, TaskService taskService){
        this.userService = userService;
        this.taskService = taskService;
    }

    //View home page of the website, and we set it to the root of the page so that it appears first when accessing the page
    @GetMapping("/")
    public String showHomePage(){
        return "home-page";
    }

    //View and return of login page
    @GetMapping("/login")
    public String login(Model model, @RequestParam(name = "error", required = false) String error) {
        model.addAttribute("error", error); //Here we get the parameters of a new user and verify if the user's credentials exist
        model.addAttribute("user", new User());
        return "login-page";
    }

    //Handle form submission of the login page
    @PostMapping("/login")
    public String processLogin(User user, RedirectAttributes redirectAttributes) {
        // Log the user details for debugging
        System.out.println("User email: " + user.getEmail());
        System.out.println("User password: " + user.getPassword());
        //We get the user based on their email and password, and then we verify to see if user exists in the database

        User authenticatedUser = userService.authenticateUser(user.getEmail(), user.getPassword());

        // Log the result of authentication
        System.out.println("Authenticated User: " + authenticatedUser);

        //If user is not authenticated that are returned back to the login page, if they are authenticated they are taken to the dashboard page
        if (authenticatedUser != null) {
            return "redirect:/dashboard-page"; // Redirect to dashboard page
        } else {
            redirectAttributes.addAttribute("error", "Invalid credentials. Please try again.");
            return "redirect:/login"; // Redirect back to login page with error message
        }
    }

    //View and return dashboard page
    @GetMapping("/dashboard-page")
    public String showDashboard(Model model) {
        //We get the current user that is logged in and present their dashboard page based on that
        User loggedInUser = userService.getCurrentUser();
        if (loggedInUser != null) {
            // Add the user object to the model
            model.addAttribute("user", loggedInUser);

            // Get tasks due within 3 days
            List<Task> tasks = taskService.getTasksDueThisWeek();
            model.addAttribute("tasks", tasks);

            return "dashboard-page";
        } else {
            // Redirect to the login page if the user is not authenticated
            return "redirect:/login";
        }
    }

    //View and return google calendar page
    @GetMapping("/googlecalendar-page")
    public String showCalendar(Model model){
        //We verify if the user is authenticated to view the Google Calendar page
        User loggedInUser = userService.getCurrentUser();
        if (loggedInUser != null) {
            // Add the user object to the model
            model.addAttribute("user", loggedInUser);
            return "googlecalendar-page";
        } else {
            // Redirect to the login page if the user is not authenticated
            return "redirect:/login";
        }
    }
}