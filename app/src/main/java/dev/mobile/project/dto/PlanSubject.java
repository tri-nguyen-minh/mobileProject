package dev.mobile.project.dto;

import java.io.Serializable;

public class PlanSubject implements Serializable {
    private int planSubjectId, planSemesterId, priority, progress;
    private String subjectId;
    private boolean isComplete;

    public PlanSubject(int planSubjectId, int planSemesterId, int priority, int progress, String subjectId, boolean isComplete) {
        this.planSubjectId = planSubjectId;
        this.planSemesterId = planSemesterId;
        this.priority = priority;
        this.progress = progress;
        this.subjectId = subjectId;
        this.isComplete = isComplete;
    }

    public PlanSubject() {
    }

    public int getPlanSubjectId() {
        return planSubjectId;
    }

    public void setPlanSubjectId(int planSubjectId) {
        this.planSubjectId = planSubjectId;
    }

    public int getPlanSemesterId() {
        return planSemesterId;
    }

    public void setPlanSemesterId(int planSemesterId) {
        this.planSemesterId = planSemesterId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
