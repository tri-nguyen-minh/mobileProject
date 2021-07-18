package dev.mobile.project.DBHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.project.dto.PlanSubject;
import dev.mobile.project.dto.Topic;

public class TopicDBHelper {

    private static final String TABLE_NAME = "Topics";
    public static final String KEY_TOPIC_ID = "TopicId";
    public static final String KEY_TOPIC_NAME = "TopicName";
    public static final String KEY_TOPIC_DESCRIPTION = "TopicDescription";
    public static final String KEY_SUBJECT_ID = "SubjectId";

    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;

    public TopicDBHelper(DatabaseHelper databaseHelper) {
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

    public long createTopic(Topic topic) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TOPIC_ID, topic.getTopicId());
        values.put(KEY_TOPIC_NAME, topic.getTopicName());
        values.put(KEY_TOPIC_DESCRIPTION, topic.getTopicDescription());
        values.put(KEY_SUBJECT_ID, topic.getSubjectId());

        long result = db.insert(TABLE_NAME, null, values);
        helper.closeDatabase(db);
        return result;
    }

    public List<Topic> getAllTopicsBySubjectId(String subjectId) {
        List<Topic> lists = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " +
                "" + KEY_SUBJECT_ID + " = '" + subjectId + "'";

        db = helper.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);
        Topic topic = null;
        if (cursor.moveToFirst()) {
            do {
                topic = new Topic();
                topic.setTopicId(cursor.getInt(cursor.getColumnIndex(KEY_TOPIC_ID)));
                topic.setTopicName(cursor.getString(cursor.getColumnIndex(KEY_TOPIC_NAME)));
                topic.setTopicDescription(cursor.getString(cursor.getColumnIndex(KEY_TOPIC_DESCRIPTION)));
                topic.setSubjectId(cursor.getString(cursor.getColumnIndex(KEY_SUBJECT_ID)));
                lists.add(topic);
            } while (cursor.moveToNext());
        }

        helper.closeDatabase(db);
        return lists;
    }
}
