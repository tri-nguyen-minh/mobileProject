package dev.mobile.project.dto;

import java.io.Serializable;

public class PlanTopic implements Serializable {
    private int PlanTopicId, TopicId, PlanSubjectId, Progress;
    private boolean IsComplete;

    public PlanTopic(int planTopicId, int topicId, int planSubjectId, int progress, boolean isComplete) {
        PlanTopicId = planTopicId;
        TopicId = topicId;
        PlanSubjectId = planSubjectId;
        Progress = progress;
        IsComplete = isComplete;
    }

    public PlanTopic() {
    }

    public int getPlanTopicId() {
        return PlanTopicId;
    }

    public void setPlanTopicId(int planTopicId) {
        PlanTopicId = planTopicId;
    }

    public int getTopicId() {
        return TopicId;
    }

    public void setTopicId(int topicId) {
        TopicId = topicId;
    }

    public int getPlanSubjectId() {
        return PlanSubjectId;
    }

    public void setPlanSubjectId(int planSubjectId) {
        PlanSubjectId = planSubjectId;
    }

    public int getProgress() {
        return Progress;
    }

    public void setProgress(int progress) {
        Progress = progress;
    }

    public boolean isComplete() {
        return IsComplete;
    }

    public void setComplete(boolean complete) {
        IsComplete = complete;
    }
}
