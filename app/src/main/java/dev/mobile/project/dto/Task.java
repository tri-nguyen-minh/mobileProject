package dev.mobile.project.dto;

import java.io.Serializable;

public class Task implements Serializable {
    private int taskId, planTopicId, estimateTime, effortTime, priority;
    private String description, dueDate, createDate;
    private boolean isComplete;

    public Task(int taskId, int planTopicId, int estimateTime, int effortTime, int priority, String description, String dueDate, String createDate, boolean isComplete) {
        this.taskId = taskId;
        this.planTopicId = planTopicId;
        this.estimateTime = estimateTime;
        this.effortTime = effortTime;
        this.priority = priority;
        this.description = description;
        this.dueDate = dueDate;
        this.createDate = createDate;
        this.isComplete = isComplete;
    }

    public Task() {
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getPlanTopicId() {
        return planTopicId;
    }

    public void setPlanTopicId(int planTopicId) {
        this.planTopicId = planTopicId;
    }

    public int getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(int estimateTime) {
        this.estimateTime = estimateTime;
    }

    public int getEffortTime() {
        return effortTime;
    }

    public void setEffortTime(int effortTime) {
        this.effortTime = effortTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
