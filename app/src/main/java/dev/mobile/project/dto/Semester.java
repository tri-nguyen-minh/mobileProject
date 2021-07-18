package dev.mobile.project.dto;

import java.io.Serializable;
import java.util.Date;

public class Semester implements Serializable {
    private String semesterId, semesterName, startDate, endDate;
    private boolean isComplete;

    public Semester(String semesterId, String semesterName, String startDate, String endDate, boolean isComplete) {
        this.semesterId = semesterId;
        this.semesterName = semesterName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isComplete = isComplete;
    }

    public Semester() {
    }

    public String getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(String semesterId) {
        this.semesterId = semesterId;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
