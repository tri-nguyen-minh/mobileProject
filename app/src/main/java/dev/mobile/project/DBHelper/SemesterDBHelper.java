package dev.mobile.project.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.project.dto.Semester;

public class SemesterDBHelper {

    private static final String TABLE_NAME = "Semesters";
    public static final String KEY_SEMESTER_ID = "SemesterId";
    public static final String KEY_SEMESTER_NAME = "SemesterName";
    public static final String KEY_START_DATE = "StartDate";
    public static final String KEY_END_DATE = "EndDate";
    public static final String KEY_SEMESTER_STATUS = "IsComplete";

    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;

    public SemesterDBHelper(DatabaseHelper databaseHelper) {
        helper = databaseHelper;
    }

    public long createSemester(Semester semester) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SEMESTER_ID, semester.getSemesterId());
        values.put(KEY_SEMESTER_NAME, semester.getSemesterName());
        values.put(KEY_START_DATE, semester.getStartDate());
        values.put(KEY_END_DATE, semester.getEndDate());
        values.put(KEY_SEMESTER_STATUS, semester.isComplete());

        long result = db.insert(TABLE_NAME, null, values);
        helper.closeDatabase(db);
        return result;
    }

    public List<Semester> getAllSemesters() {
        List<Semester> semesters = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        db = helper.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);
        Semester semester;
        if (cursor.moveToFirst()) {
            do {
                semester = new Semester();
                semester.setSemesterId(cursor.getString(cursor.getColumnIndex(KEY_SEMESTER_ID)));
                semester.setSemesterName(cursor.getString(cursor.getColumnIndex(KEY_SEMESTER_NAME)));
                semester.setStartDate(cursor.getString(cursor.getColumnIndex(KEY_START_DATE)));
                semester.setEndDate(cursor.getString(cursor.getColumnIndex(KEY_END_DATE)));
                int status = cursor.getInt(cursor.getColumnIndex(KEY_SEMESTER_STATUS));
                semester.setComplete(status == 1);

                semesters.add(semester);
            } while (cursor.moveToNext());
        }

        helper.closeDatabase(db);
        return semesters;
    }

    public Semester getOngoingSemester() {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                + KEY_SEMESTER_STATUS + " = " + false;

        db = helper.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);

        Semester semester = null;
        if (cursor.moveToFirst()) {
            
            semester = new Semester();
            semester.setSemesterId(cursor.getString(cursor.getColumnIndex(KEY_SEMESTER_ID)));
            semester.setSemesterName(cursor.getString(cursor.getColumnIndex(KEY_SEMESTER_NAME)));
            semester.setStartDate(cursor.getString(cursor.getColumnIndex(KEY_START_DATE)));
            semester.setEndDate(cursor.getString(cursor.getColumnIndex(KEY_END_DATE)));
            int status = cursor.getInt(cursor.getColumnIndex(KEY_SEMESTER_STATUS));
            semester.setComplete(status == 1);
        }

        helper.closeDatabase(db);
        return semester;
    }
}
