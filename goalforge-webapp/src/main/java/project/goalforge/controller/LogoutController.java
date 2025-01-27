package project.goalforge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.goalforge.service.UserService;

@Controller
public class LogoutController {

    private final UserService userService; //Service for user operations

    @Autowired //Injection of services with dependency
    public LogoutController(UserService userService) {
        this.userService = userService;
    }

    //Handle form submission of logout button that redirects the user back to the login page
    @PostMapping("/logout")
    public String processLogout(RedirectAttributes redirectAttributes) {
        try {
            userService.logout();
            redirectAttributes.addFlashAttribute("logoutMessage", "You have successfully logged out.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error occurred during logout.");
        }
        return "redirect:/login"; // Redirect to the login page
    }
}
