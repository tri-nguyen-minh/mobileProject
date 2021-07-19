package dev.mobile.project.DBHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.project.dto.PlanSubject;
import dev.mobile.project.dto.Task;

public class TaskDBHelper {

    private static final String TABLE_NAME = "Tasks";
    public static final String KEY_TASK_ID = "TaskId";
    public static final String KEY_PLAN_TOPIC_ID = "PlanTopicId";
    public static final String KEY_TASK_DESCRIPTION = "TaskDescription";
    public static final String KEY_ESTIMATE_TIME = "EstimateTime";
    public static final String KEY_EFFORT_TIME = "EffortTime";
    public static final String KEY_PRIORITY = "Priority";
    public static final String KEY_DUE_DATE = "DueDate";
    public static final String KEY_CREATE_DATE = "CreateDate";
    public static final String KEY_STATUS = "IsComplete";

    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;

    public TaskDBHelper(DatabaseHelper databaseHelper) {
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

    public long createTask(Task task) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK_ID, task.getTaskId());
        values.put(KEY_PLAN_TOPIC_ID, task.getPlanTopicId());
        values.put(KEY_TASK_DESCRIPTION, task.getDescription());
        values.put(KEY_ESTIMATE_TIME, task.getEstimateTime());
        values.put(KEY_EFFORT_TIME, task.getEffortTime());
        values.put(KEY_PRIORITY, task.getPriority());
        values.put(KEY_DUE_DATE, task.getDueDate());
        values.put(KEY_CREATE_DATE, task.getCreateDate());
        values.put(KEY_STATUS, task.isComplete());
        long result = db.insert(TABLE_NAME, null, values);
        helper.closeDatabase(db);
        return result;
    }

    public Task getTaskById(int taskId) {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                + KEY_TASK_ID + " = " + taskId;

        db = helper.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);
        Task task = null;
        if (cursor.moveToFirst()) {
            do {
                task = new Task();
                task.setTaskId(cursor.getInt(cursor.getColumnIndex(KEY_TASK_ID)));
                task.setPlanTopicId(cursor.getInt(cursor.getColumnIndex(KEY_PLAN_TOPIC_ID)));
                task.setEstimateTime(cursor.getInt(cursor.getColumnIndex(KEY_ESTIMATE_TIME)));
                task.setEffortTime(cursor.getInt(cursor.getColumnIndex(KEY_EFFORT_TIME)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(KEY_TASK_DESCRIPTION)));
                task.setDueDate(cursor.getString(cursor.getColumnIndex(KEY_DUE_DATE)));
                task.setCreateDate(cursor.getString(cursor.getColumnIndex(KEY_CREATE_DATE)));
                task.setPriority(cursor.getInt(cursor.getColumnIndex(KEY_PRIORITY)));
                int status = cursor.getInt(cursor.getColumnIndex(KEY_STATUS));
                task.setComplete(status == 1);

            } while (cursor.moveToNext());
        }

        helper.closeDatabase(db);
        return task;
    }

    public List<Task> getAllTaskByPlanTopic(int planTopicId) {
        List<Task> tasks = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                + KEY_PLAN_TOPIC_ID + " = " + planTopicId;

        db = helper.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);
        Task task;
        if (cursor.moveToFirst()) {
            do {
                task = new Task();
                task.setTaskId(cursor.getInt(cursor.getColumnIndex(KEY_TASK_ID)));
                task.setPlanTopicId(cursor.getInt(cursor.getColumnIndex(KEY_PLAN_TOPIC_ID)));
                task.setEstimateTime(cursor.getInt(cursor.getColumnIndex(KEY_ESTIMATE_TIME)));
                task.setEffortTime(cursor.getInt(cursor.getColumnIndex(KEY_EFFORT_TIME)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(KEY_TASK_DESCRIPTION)));
                task.setDueDate(cursor.getString(cursor.getColumnIndex(KEY_DUE_DATE)));
                task.setCreateDate(cursor.getString(cursor.getColumnIndex(KEY_CREATE_DATE)));
                task.setPriority(cursor.getInt(cursor.getColumnIndex(KEY_PRIORITY)));
                int status = cursor.getInt(cursor.getColumnIndex(KEY_STATUS));
                task.setComplete(status == 1);

                tasks.add(task);
            } while (cursor.moveToNext());
        }

        helper.closeDatabase(db);
        return tasks;
    }

    public List<Task> searchTaskByCondition(String description, int priority, boolean status) {
        List<Task> tasks = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                + KEY_TASK_DESCRIPTION + " LIKE '%" + description + "%' AND "
                + KEY_PRIORITY + " = " + priority + " AND "
                + KEY_STATUS + " = " + status;

        db = helper.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);
        Task task;
        if (cursor.moveToFirst()) {
            do {
                task = new Task();
                task.setTaskId(cursor.getInt(cursor.getColumnIndex(KEY_TASK_ID)));
                task.setPlanTopicId(cursor.getInt(cursor.getColumnIndex(KEY_PLAN_TOPIC_ID)));
                task.setEstimateTime(cursor.getInt(cursor.getColumnIndex(KEY_ESTIMATE_TIME)));
                task.setEffortTime(cursor.getInt(cursor.getColumnIndex(KEY_EFFORT_TIME)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(KEY_TASK_DESCRIPTION)));
                task.setDueDate(cursor.getString(cursor.getColumnIndex(KEY_DUE_DATE)));
                task.setCreateDate(cursor.getString(cursor.getColumnIndex(KEY_CREATE_DATE)));
                task.setPriority(cursor.getInt(cursor.getColumnIndex(KEY_PRIORITY)));
                task.setComplete(status);

                tasks.add(task);
            } while (cursor.moveToNext());
        }

        helper.closeDatabase(db);
        return tasks;
    }

    public int updateTask(Task task) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLAN_TOPIC_ID, task.getPlanTopicId());
        values.put(KEY_TASK_DESCRIPTION, task.getDescription());
        values.put(KEY_ESTIMATE_TIME, task.getEstimateTime());
        values.put(KEY_EFFORT_TIME, task.getEffortTime());
        values.put(KEY_PRIORITY, task.getPriority());
        values.put(KEY_DUE_DATE, task.getDueDate());
        values.put(KEY_CREATE_DATE, task.getCreateDate());
        values.put(KEY_STATUS, task.isComplete());

        int result = db.update(TABLE_NAME, values, KEY_TASK_ID + " = ?",
                new String[] { String.valueOf(task.getTaskId())});

        helper.closeDatabase(db);
        return result;
    }

    public int deleteTask(int taskId) {
        SQLiteDatabase db = helper.getWritableDatabase();

        int result = db.delete(TABLE_NAME, KEY_TASK_ID + " = ?",
                new String[] { String.valueOf(taskId) });

        helper.closeDatabase(db);
        return result;
    }
}
