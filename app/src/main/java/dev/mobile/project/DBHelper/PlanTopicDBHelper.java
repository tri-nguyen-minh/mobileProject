package dev.mobile.project.DBHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import dev.mobile.project.dto.PlanSemester;
import dev.mobile.project.dto.PlanTopic;

public class PlanTopicDBHelper {
    private static final String TABLE_NAME = "PlanTopic";
    public static final String KEY_PLAN_TOPIC_ID = "PlanTopicId";
    public static final String KEY_TOPIC_ID = "TopicId";
    public static final String KEY_PLAN_SUBJECT_ID = "PlanSubjectId";
    public static final String KEY_PROGRESS = "Progress";
    public static final String KEY_STATUS = "IsComplete";

    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;

    public PlanTopicDBHelper(DatabaseHelper databaseHelper) {
        helper = databaseHelper;
    }

    public int getCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        db = helper.getReadableDatabase();
        cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        helper.closeDatabase(db);
        return count;
    }

    public long createTopic(PlanTopic topic) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLAN_TOPIC_ID, topic.getPlanTopicId());
        values.put(KEY_TOPIC_ID, topic.getTopicId());
        values.put(KEY_PLAN_SUBJECT_ID, topic.getPlanSubjectId());
        values.put(KEY_PROGRESS, topic.getProgress());
        values.put(KEY_STATUS, topic.isComplete());

        long result = db.insert(TABLE_NAME, null, values);
        helper.closeDatabase(db);
        return result;
    }

    public PlanTopic getPlanTopicByPlanTopicId(int planTopicId) {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                + KEY_PLAN_TOPIC_ID + " = " + planTopicId;

        db = helper.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);

        PlanTopic topic = null;
        if (cursor.moveToFirst()) {

            topic = new PlanTopic();
            topic.setPlanTopicId(cursor.getInt(cursor.getColumnIndex(KEY_PLAN_TOPIC_ID)));
            topic.setPlanSubjectId(cursor.getInt(cursor.getColumnIndex(KEY_PLAN_SUBJECT_ID)));
            topic.setTopicId(cursor.getInt(cursor.getColumnIndex(KEY_TOPIC_ID)));
            topic.setProgress(cursor.getInt(cursor.getColumnIndex(KEY_PROGRESS)));
            int status = cursor.getInt(cursor.getColumnIndex(KEY_STATUS));
            topic.setComplete(status == 1);
        }

        helper.closeDatabase(db);
        return topic;
    }

    public PlanTopic getPlanTopicByTopicAndPlanSubject(int topicId, int planSubjectId) {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                + KEY_TOPIC_ID + " = " + topicId + " AND " + KEY_PLAN_SUBJECT_ID + " = " + planSubjectId;

        db = helper.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);

        PlanTopic topic = null;
        if (cursor.moveToFirst()) {
            
            topic = new PlanTopic();
            topic.setPlanTopicId(cursor.getInt(cursor.getColumnIndex(KEY_PLAN_TOPIC_ID)));
            topic.setPlanSubjectId(cursor.getInt(cursor.getColumnIndex(KEY_PLAN_SUBJECT_ID)));
            topic.setTopicId(cursor.getInt(cursor.getColumnIndex(KEY_TOPIC_ID)));
            topic.setProgress(cursor.getInt(cursor.getColumnIndex(KEY_PROGRESS)));
            int status = cursor.getInt(cursor.getColumnIndex(KEY_STATUS));
            topic.setComplete(status == 1);
        }

        helper.closeDatabase(db);
        return topic;
    }
}
