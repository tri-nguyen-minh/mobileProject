package dev.mobile.project.dto;

import java.io.Serializable;

public class Topic implements Serializable {
    private int topicId;
    private String topicName, topicDescription, subjectId;

    public Topic(int topicId, String topicName, String topicDescription, String subjectId) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.topicDescription = topicDescription;
        this.subjectId = subjectId;
    }

    public Topic() {
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}
