package dev.mobile.project.DBHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.project.dto.PlanSubject;

public class PlanSubjectDBHelper {
    private static final String TABLE_NAME = "PlanSubject";
    public static final String KEY_PLAN_SUBJECT_ID = "PlanSubjectId";
    public static final String KEY_PLAN_SEMESTER_ID = "PlanSemesterId";
    public static final String KEY_SUBJECT_ID = "SubjectId";
    public static final String KEY_PRIORITY = "Priority";
    public static final String KEY_PROGRESS = "Progress";
    public static final String KEY_STATUS = "IsComplete";

    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;

    public PlanSubjectDBHelper(DatabaseHelper databaseHelper) {
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

    public long createPlanSubject(PlanSubject subject) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLAN_SUBJECT_ID, subject.getPlanSubjectId());
        values.put(KEY_PLAN_SEMESTER_ID, subject.getPlanSemesterId());
        values.put(KEY_SUBJECT_ID, subject.getSubjectId());
        values.put(KEY_PRIORITY, subject.getPriority());
        values.put(KEY_PROGRESS, subject.getProgress());
        values.put(KEY_STATUS, subject.isComplete());

        long result = db.insert(TABLE_NAME, null, values);
        helper.closeDatabase(db);
        return result;
    }

    public List<PlanSubject> getAllSubjects() {
        List<PlanSubject> planSubjects = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        db = helper.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);
        PlanSubject subject;
        if (cursor.moveToFirst()) {
            do {
                subject = new PlanSubject();
                subject.setPlanSubjectId(cursor.getInt(cursor.getColumnIndex(KEY_PLAN_SUBJECT_ID)));
                subject.setPlanSemesterId(cursor.getInt(cursor.getColumnIndex(KEY_PLAN_SEMESTER_ID)));
                subject.setSubjectId(cursor.getString(cursor.getColumnIndex(KEY_SUBJECT_ID)));
                subject.setPriority(cursor.getInt(cursor.getColumnIndex(KEY_PRIORITY)));
                subject.setProgress(cursor.getInt(cursor.getColumnIndex(KEY_PROGRESS)));
                int status = cursor.getInt(cursor.getColumnIndex(KEY_STATUS));
                subject.setComplete(status == 1);

                planSubjects.add(subject);
            } while (cursor.moveToNext());
        }

        helper.closeDatabase(db);
        return planSubjects;
    }
    public List<PlanSubject> getAllSubjectsByPlanSemester(int planSemesterId) {
        List<PlanSubject> planSubjects = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " +
                "" + KEY_PLAN_SEMESTER_ID + " = " + planSemesterId;

        db = helper.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);
        PlanSubject subject;
        if (cursor.moveToFirst()) {
            do {
                subject = new PlanSubject();
                subject.setPlanSubjectId(cursor.getInt(cursor.getColumnIndex(KEY_PLAN_SUBJECT_ID)));
                subject.setPlanSemesterId(cursor.getInt(cursor.getColumnIndex(KEY_PLAN_SEMESTER_ID)));
                subject.setSubjectId(cursor.getString(cursor.getColumnIndex(KEY_SUBJECT_ID)));
                subject.setPriority(cursor.getInt(cursor.getColumnIndex(KEY_PRIORITY)));
                subject.setProgress(cursor.getInt(cursor.getColumnIndex(KEY_PROGRESS)));
                int status = cursor.getInt(cursor.getColumnIndex(KEY_STATUS));
                subject.setComplete(status == 1);

                planSubjects.add(subject);
            } while (cursor.moveToNext());
        }

        helper.closeDatabase(db);
        return planSubjects;
    }

    public PlanSubject getPlanSubjectsById(int planSubjectId) {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " +
                "" + KEY_PLAN_SUBJECT_ID + " = " + planSubjectId;

        db = helper.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);
        PlanSubject subject = null;
        if (cursor.moveToFirst()) {
            do {
                subject = new PlanSubject();
                subject.setPlanSubjectId(cursor.getInt(cursor.getColumnIndex(KEY_PLAN_SUBJECT_ID)));
                subject.setPlanSemesterId(cursor.getInt(cursor.getColumnIndex(KEY_PLAN_SEMESTER_ID)));
                subject.setSubjectId(cursor.getString(cursor.getColumnIndex(KEY_SUBJECT_ID)));
                subject.setPriority(cursor.getInt(cursor.getColumnIndex(KEY_PRIORITY)));
                subject.setProgress(cursor.getInt(cursor.getColumnIndex(KEY_PROGRESS)));
                int status = cursor.getInt(cursor.getColumnIndex(KEY_STATUS));
                subject.setComplete(status == 1);

            } while (cursor.moveToNext());
        }

        helper.closeDatabase(db);
        return subject;
    }
}
