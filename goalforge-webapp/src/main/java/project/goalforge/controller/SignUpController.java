package project.goalforge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.goalforge.models.User;
import project.goalforge.service.UserService;

@Controller
public class SignUpController {
    @Autowired //Service for user operations
    private UserService userService;

    //View and return sign up page
    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "signup-page";
    }

    //Handle form submission of signing up
    @PostMapping("/signup")
    public String processSignUp(User user) {
        // Check if the user already exists in the database
        if (userService.getUserByEmail(user.getEmail()) != null) {
            // User already exists, redirect back to signup page with an error message
            return "redirect:/signup?error=exists";
        }

        // Save the user to the database without modifying the password
        userService.registerUser(user);

        // Redirect to the login page
        return "redirect:/login";
    }

}
