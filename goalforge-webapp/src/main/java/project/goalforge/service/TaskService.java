package project.goalforge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.goalforge.models.Task;
import project.goalforge.models.User;
import project.goalforge.scope.TaskRepository;
import project.goalforge.scope.UserRepository;

import java.time.DayOfWeek;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public String getUserEmailByUserId(Long userId) {
        return userRepository.findById(userId)
                .map(User::getEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    }

    public Task saveTask(Task task) {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            task.setUserId(currentUser.getUserID());
            task.setTaskCreationDate(new Date(System.currentTimeMillis()));
            if (task.getTaskStatus() == null) {
                task.setTaskStatus("Not Started");
            }
            return taskRepository.save(task);
        }
        throw new IllegalStateException("User not authenticated or task saving failed");
    }

    public List<Task> getAllTasks() {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            return taskRepository.findByUserId(currentUser.getUserID());
        }
        throw new IllegalStateException("User not authenticated or task retrieval failed");
    }

    public List<Task> getTasksDueThisWeek() {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            // Get the current date and determine the start of the week (Monday)
            LocalDate currentDate = LocalDate.now();
            LocalDate startOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            // Calculate the end of the week (Sunday)
            LocalDate endOfWeek = startOfWeek.plusDays(6);

            // Convert LocalDate to Date for database query
            Date sqlStartDate = Date.valueOf(startOfWeek);
            Date sqlEndDate = Date.valueOf(endOfWeek);

            return taskRepository.findByUserIdAndDueDateBetween(currentUser.getUserID(), sqlStartDate, sqlEndDate);
        }
        throw new IllegalStateException("User not authenticated or task retrieval failed");
    }
    public long countTasksByStatus(String status) {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            return taskRepository.countByUserIdAndTaskStatus(currentUser.getUserID(), status);
        }
        throw new IllegalStateException("User not authenticated");
    }

    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }
    public void deleteTaskById(Long taskId) {
        taskRepository.deleteById(taskId);
    }
    public void deleteTasksByIds(List<Long> taskIds) {
        taskRepository.deleteAllById(taskIds);
    }
}
