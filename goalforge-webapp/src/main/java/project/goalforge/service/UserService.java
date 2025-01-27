package project.goalforge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.goalforge.Utility.UserNotFoundException;
import project.goalforge.models.User;
import project.goalforge.scope.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private User authenticatedUser;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public void updateUserEmail(Long userId, String newEmail) {
        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresent(user -> {
            user.setEmail(newEmail);
            userRepository.save(user);
        });
    }
    public User getUserByID(Long ID){
        return userRepository.findByUserID(ID);
    }

    public void registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        } else {
            user.setSignUpDate(new Timestamp(System.currentTimeMillis()));
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);

            // Set additional fields
            user.setGender(user.getGender());
            user.setDateOfBirth(user.getDateOfBirth());

            userRepository.save(user);
        }
    }

    public User updateUser(User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(updatedUser.getUserID());
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());

            // Update additional fields
            existingUser.setGender(updatedUser.getGender());
            existingUser.setDateOfBirth(updatedUser.getDateOfBirth());

            return userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public void deleteUser(Long userID) {
        userRepository.deleteById(userID);
    }

    public User authenticateUser(String email, String password) {
        // Log the email for debugging
        System.out.println("Email entered: " + email);

        // Retrieve user from the database
        User user = userRepository.findByEmail(email);

        if (user != null) {
            // Log the retrieved user for debugging
            System.out.println("Retrieved User: " + user.getEmail());

            // Check if the password matches
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Log successful authentication
                System.out.println("Authentication successful for user: " + user.getEmail());
                authenticatedUser = user; // Store the authenticated user
                return user; // Return the authenticated user
            } else {
                // Log password mismatch
                System.out.println("Password mismatch for user: " + user.getEmail());
            }
        } else {
            // Log user not found
            System.out.println("User not found for email: " + email);
        }

        return null; // Return null if authentication fails
    }

    public User getCurrentUser() {
        if (authenticatedUser != null) {
            // Log the authenticated user's information
            System.out.println("Current authenticated user: " + authenticatedUser.getEmail());
            return authenticatedUser;
        }

        // Log if no authenticated user is available
        System.out.println("No authenticated user found.");
        return null;
    }

    public void logout() {
        authenticatedUser = null;
        SecurityContextHolder.clearContext();
    }

    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException{
        User user = userRepository.findByEmail(email);
        if(user != null){
            user.setResetPasswordToken(token);
            System.out.println("Storing token for user " + user.getEmail() + ": " + token);
            userRepository.save(user);
        } else{
            throw new UserNotFoundException("Could not find any user with the email" + email);
        }
    }

    public User getByResetPasswordToken(String resetPasswordToken){
        System.out.println("Token received in getByResetPasswordToken: " + resetPasswordToken);
        return userRepository.findByResetPasswordToken(resetPasswordToken);
    }

    public void updatePassword(User user, String newPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    public void updateCurrentPassword(User user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}