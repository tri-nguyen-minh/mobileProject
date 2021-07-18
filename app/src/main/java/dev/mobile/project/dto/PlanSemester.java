package dev.mobile.project.dto;

import java.io.Serializable;

public class PlanSemester implements Serializable {
    private int planSemesterId;
    private String planSemesterName, studentId, semesterId;
    private boolean isComplete;

    public PlanSemester(int planSemesterId, String planSemesterName, String studentId, String semesterId, boolean isComplete) {
        this.planSemesterId = planSemesterId;
        this.planSemesterName = planSemesterName;
        this.studentId = studentId;
        this.semesterId = semesterId;
        this.isComplete = isComplete;
    }

    public PlanSemester() {
    }

    public int getPlanSemesterId() {
        return planSemesterId;
    }

    public void setPlanSemesterId(int planSemesterId) {
        this.planSemesterId = planSemesterId;
    }

    public String getPlanSemesterName() {
        return planSemesterName;
    }

    public void setPlanSemesterName(String planSemesterName) {
        this.planSemesterName = planSemesterName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(String semesterId) {
        this.semesterId = semesterId;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
