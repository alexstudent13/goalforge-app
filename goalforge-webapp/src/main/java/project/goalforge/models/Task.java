package project.goalforge.models;

import jakarta.persistence.*;
import java.sql.Date;

//Mark class as a JPA entity using Entity annotation
@Entity
//Specify the table in the database to which it will map to using Table annotation
@Table(name = "Task")
public class Task {
    //Mark this field as the primary key with an auto-increment strategy using ID and GeneratedValue annotation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Extends from Users Table
    @Column(name = "TaskID")
    private Long taskId;

    //Column annotation for storing user ID
    @Column(name = "UserID")
    private Long userId;

    //Column annotation for storing task name
    @Column(name = "TaskName")
    private String taskName;

    //Column annotation for storing task description
    @Column(name = "TaskDescription")
    private String taskDescription;

    //Column annotation for storing task creation date
    @Column(name = "TaskCreationDate")
    private Date taskCreationDate;

    //Column annotation for storing task url
    @Column(name = "TaskURL")
    private String taskUrl;

    //Column annotation for storing task due date
    @Column(name = "DueDate")
    private Date dueDate;

    //Column annotation for storing task priority
    @Column(name = "Priority")
    private String priority;

    //Column annotation for storing task status
    @Column(name = "TaskStatus")
    private String taskStatus;

    //Column annotation for storing task category
    @Column(name = "Category")
    private String category;

    //Column annotation for storing if user wants notifications
    @Column(name = "wants_notifications")
    private boolean wantsNotifications;

    /*
    Getters and setters for the created columns such as task id, user id, task name, task description,
    task creation date, task url, due date, task priority, task status, task category, and if user
    wants notifications enabled or not
    */
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Date getTaskCreationDate() {
        return taskCreationDate;
    }

    public void setTaskCreationDate(Date taskCreationDate) {
        this.taskCreationDate = taskCreationDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTaskUrl() {
        return taskUrl;
    }

    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }

    public boolean isWantsNotifications() {
        return wantsNotifications;
    }

    public void setWantsNotifications(boolean wantsNotifications) {
        this.wantsNotifications = wantsNotifications;
    }
}
