package project.goalforge.scope;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.goalforge.models.Task;

import java.sql.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Find tasks by user ID
    List<Task> findByUserId(Long userId);
    //Find tasks between due dates
    List<Task> findByUserIdAndDueDateBetween(Long userId, Date startDate, Date endDate);
    //Find tasks based on if they want notifications or not
    List<Task> findByPriorityAndWantsNotifications(String priority, boolean wantsNotifications);
    //Find tasks based on due date and if they want notifications or not
    List<Task> findByPriorityAndDueDateAndWantsNotifications(String priority, Date dueDate, boolean wantsNotifications);
    //Count tasks by status
    long countByUserIdAndTaskStatus(Long userId, String taskStatus);

    // Custom query example using JPQL
    @Query("SELECT t FROM Task t WHERE t.userId = :userId AND t.taskStatus = :taskStatus")
    List<Task> findTasksByUserIdAndStatus(Long userId, String taskStatus);
}



