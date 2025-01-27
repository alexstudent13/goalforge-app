package project.goalforge.controller;

import freemarker.template.SimpleScalar;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.goalforge.models.User;
import project.goalforge.service.UserService;
import java.util.concurrent.TimeUnit;

@Controller
public class SettingsController {

    private final UserService userService; //Service for user operations

    //Injection of service with dependency
    public SettingsController(UserService userService) {
        this.userService = userService;
    }

    //View and return settings page
    @GetMapping("/settings-page")
    public String showSettingsPage(Model model) {
        User loggedInUser = userService.getCurrentUser();
        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect to login-page if user not authenticated
        }
        model.addAttribute("user", loggedInUser);
        return "settings-page";
    }

    // Handle Change Name Form Submission
    @PostMapping("/change-name")
    public String changeName(@RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             RedirectAttributes redirectAttributes) throws InterruptedException {
        User loggedInUser = userService.getCurrentUser();
        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect to login-page if user not authenticated
        }
        loggedInUser.setFirstName(firstName); // Update the first name
        loggedInUser.setLastName(lastName); // Update the last name
        userService.updateUser(loggedInUser); // Save the updated user

        redirectAttributes.addFlashAttribute("message", new SimpleScalar("Changed name successfully."));
        TimeUnit.SECONDS.sleep(1); // Wait for 1 second

        return "redirect:/settings-page"; // Redirect to the settings page
    }

    // Handle Change Email Form Submission
    @PostMapping("/change-email")
    public String changeEmail(@RequestParam("newEmail") String newEmail,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) throws InterruptedException {
        User loggedInUser = userService.getCurrentUser();
        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect to login-page if user not authenticated
        }
        // Update the email using UserService
        userService.updateUserEmail(loggedInUser.getUserID(), newEmail);

        redirectAttributes.addFlashAttribute("message", new SimpleScalar("Changed email successfully. Login using new email!"));
        TimeUnit.SECONDS.sleep(1); // Wait for 1 second

        // Logout the user
        userService.logout();
        session.invalidate(); // Invalidate the session to ensure a new session is created after login

        return "redirect:/login"; // Redirect to the login page after email change
    }

    // Handle Change Password Form Submission
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 RedirectAttributes redirectAttributes) throws InterruptedException {
        User loggedInUser = userService.getCurrentUser();
        String redirectUrl = "redirect:/login"; // Default redirect URL

        if (loggedInUser != null) {
            // Check if the current password matches before updating
            User authenticatedUser = userService.authenticateUser(loggedInUser.getEmail(), currentPassword);
            if (authenticatedUser != null) {
                // Update the password
                userService.updateCurrentPassword(loggedInUser, newPassword);

                // Log success message
                System.out.println("Password changed successfully for user: " + loggedInUser.getEmail());

                redirectAttributes.addFlashAttribute("message", new SimpleScalar("Changed password successfully."));
                redirectUrl = "redirect:/settings-page";
            } else {
                // Log error message
                System.out.println("Current password is incorrect for user: " + loggedInUser.getEmail());

                redirectAttributes.addFlashAttribute("errorMessage", new SimpleScalar("Unable to change password. Wrong Current Password entered!"));
                redirectUrl = "redirect:/settings-page";
            }
        } else {
            // Log user not logged in
            System.out.println("User not logged in");
        }

        TimeUnit.SECONDS.sleep(1); // Wait for 1 second

        System.out.println("Redirecting to: " + redirectUrl); // Log the redirect URL
        return redirectUrl; // Redirect to the appropriate page based on authentication result
    }


    // Handle Delete Account Form Submission
    @PostMapping("/delete-account")
    public String deleteAccount(@RequestParam("confirmDelete") String confirmDelete) {
        User loggedInUser = userService.getCurrentUser();
        if (loggedInUser != null) {
            userService.deleteUser(loggedInUser.getUserID()); // Delete the user's account
            userService.logout(); // Logout the user after deletion
        }
        return "redirect:/login"; // Redirect to login after account deletion
    }
}