package project.goalforge.service;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import project.goalforge.models.Task;
import project.goalforge.scope.TaskRepository;

@Service
public class NotificationService {
    private final TaskRepository taskRepository;
    private final JavaMailSender javaMailSender;
    private final TaskService taskService;
    public NotificationService (TaskRepository taskRepository, TaskService taskService, JavaMailSender javaMailSender){
        this.taskRepository = taskRepository;
        this.javaMailSender = javaMailSender;
        this.taskService = taskService;
    }
    // High priority: Daily at 12 AM
    @Scheduled(cron = "0 0 0 * * ?")
    public void sendDailyHighPriorityNotifications() {
        List<Task> tasks = taskRepository.findByPriorityAndWantsNotifications("High", true);
        tasks.forEach(task -> {
            String userEmail = taskService.getUserEmailByUserId(task.getUserId());
            sendTaskNotificationEmail(userEmail, "/task-details/" + task.getTaskId(), task.getTaskName());
        });
    }

    // Medium priority: Two days before due date at 12 AM
    @Scheduled(cron = "0 0 0 * * ?")
    public void sendPreDueMediumPriorityNotifications() {
        LocalDate twoDaysBefore = LocalDate.now().plusDays(2);
        List<Task> tasks = taskRepository.findByPriorityAndDueDateAndWantsNotifications("Medium", Date.valueOf(twoDaysBefore), true);
        tasks.forEach(task -> {
            String userEmail = taskService.getUserEmailByUserId(task.getUserId());
            sendTaskNotificationEmail(userEmail, "/task-details/" + task.getTaskId(), task.getTaskName());
        });
    }

    // Low priority: One day before due date at 12 AM
    @Scheduled(cron = "0 0 0 * * ?")
    public void sendPreDueLowPriorityNotifications() {
        LocalDate oneDayBefore = LocalDate.now().plusDays(1);
        List<Task> tasks = taskRepository.findByPriorityAndDueDateAndWantsNotifications("Low", Date.valueOf(oneDayBefore), true);
        tasks.forEach(task -> {
            String userEmail = taskService.getUserEmailByUserId(task.getUserId());
            sendTaskNotificationEmail(userEmail, "/task-details/" + task.getTaskId(), task.getTaskName());
        });
    }

    private void sendTaskNotificationEmail(String recipientEmail, String taskLink, String taskName) {
        try {
            // Generate a unique random string as the identifier
            String uniqueIdentifier = generateRandomString(12);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipientEmail);
            helper.setSubject("Goal-Forge: Your task is due soon! - " + uniqueIdentifier);
            helper.setText(buildEmailContent(taskName, taskLink), true);
            javaMailSender.send(message);
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            System.out.println("Error while sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String buildEmailContent(String taskName, String taskLink) {
        String baseURL = "http://localhost:8080";
        String fullTaskLink = baseURL + taskLink;
        return "<p>You have an upcoming task due: " + taskName + "</p>" +
                "<p>Check out your task here: <a href='" + fullTaskLink + "'>View Task</a></p>";
    }
    private String generateRandomString(int length) {
        // Generate a random string of specified length using SecureRandom and Base64 encoding
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
