package project.goalforge.controller;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.goalforge.Utility.RandomString;
import project.goalforge.Utility.UserNotFoundException;
import project.goalforge.Utility.Utility;
import project.goalforge.service.UserService;
import project.goalforge.models.User;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class ForgotPasswordController {
    private final UserService userService; //Service for user operations
    private final JavaMailSender javaMailSender; //Java mail sender service for email operations

    @Autowired //Injection of services with dependency
    public ForgotPasswordController(UserService userService, JavaMailSender javaMailSender){
        this.userService = userService;
        this.javaMailSender = javaMailSender;
    }
    //View and return password recovery page
    @GetMapping("/forgot_password")
    public String showForgotPasswordForm(){
        return "password_recovery";
    }

    //Handle form submission password recovery page
    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");  //Update user with a generated reset token
        String token = RandomString.make(30); //Generate a unique token for password reset
        System.out.println("Generated token: " + token);

        try {
            userService.updateResetPasswordToken(token, email); //Update user with the reset token
            User user = userService.getUserByEmail(email); //Fetch user details given their email
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token; //Generate password reset link with the reset token generated
            System.out.println("Reset password link: " + resetPasswordLink); //Debugging in console

            sendMail(email, resetPasswordLink, user.getFirstName(), token); //We use the sendEmail method with the user's email, a password reset link , and their first name and token attached with it
            model.addAttribute("message", "We have sent a reset password link to your email. Please check inbox your inbox or spam.");
        } catch (UserNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "password_recovery";
    }
    //We get a view of the password change page based on if the token received from the http request matches with the token sent when sending the email
    @GetMapping("/reset_password")
    public String showResetPasswordForm(HttpServletRequest request, Model model) {
        String token = request.getParameter("token"); //Here we request for the token that was sent to see if it matches with the token received
        User user = userService.getByResetPasswordToken(token);
        if (user == null) {
            model.addAttribute("error", "Invalid token. Please try again or request a new password reset.");
            return "password_recovery"; //If the token does not match, we send the user back to the password recovery page, otherwise we give them the new password change page
        }
        model.addAttribute("token", token);
        return "password_change";
    }

    //Handle form submission from password change page
    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token"); //Check if the token received is valid with the one sent
        String password = request.getParameter("password"); //Receive password that is entered by the user
        User user = userService.getByResetPasswordToken(token);
        if (user == null) {
            model.addAttribute("error", "Invalid token. Please try again or request a new password reset.");
            return "password_recovery";
        }
        userService.updatePassword(user, password); //If token matches, user is updated with the new password entered
        model.addAttribute("message", "Your password has been successfully updated.");
        return "redirect:/login"; // Redirect to login page
    }

    //Method responsible for sending the email with generated token, user's name, and email
    private void sendMail(String recipientEmail, String resetPasswordLink, String firstName, String token) {
        //We create variable to recipientEmail to establish later in the method
        final String emailToRecipient = recipientEmail;
        // Generate a unique random string as the identifier
        String uniqueIdentifier = generateRandomString(12);
        //We combine the generated token with email header to generate a unique email everytime
        final String emailSubject = "Password Link Recovery - " + uniqueIdentifier;

        // Add the unique identifier to the reset password link
        String resetPasswordLinkWithIdentifier = resetPasswordLink + "&id=" + uniqueIdentifier;

        //Here we construct the message for what the email's content will have
        final String emailMessage = "<p>Hello " + firstName + ",</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + resetPasswordLinkWithIdentifier + "\">Reset Password Link</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request for password change.</p>";
        //Method that uses java mail service email operations to send email
        javaMailSender.send(new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMsgHelperObj = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMsgHelperObj.setTo(emailToRecipient);
                mimeMsgHelperObj.setText(emailMessage, true);
                mimeMsgHelperObj.setSubject(emailSubject);
            }
        });
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

