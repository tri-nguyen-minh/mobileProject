package dev.mobile.project.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.project.dto.PlanSemester;
import dev.mobile.project.dto.Semester;

public class PlanSemesterDBHelper {

    private static final String TABLE_NAME = "PlanSemester";
    public static final String KEY_PLAN_SEMESTER_ID = "PlanSemesterId";
    public static final String KEY_PLAN_SEMESTER_NAME = "PlanSemesterName";
    public static final String KEY_STUDENT_ID = "StudentId";
    public static final String KEY_SEMESTER_ID = "SemesterId";
    public static final String KEY_STATUS = "IsComplete";

    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;

    public PlanSemesterDBHelper(DatabaseHelper databaseHelper) {
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

    public long createSemester(PlanSemester semester) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLAN_SEMESTER_ID, semester.getPlanSemesterId());
        values.put(KEY_PLAN_SEMESTER_NAME, semester.getPlanSemesterName());
        values.put(KEY_STUDENT_ID, semester.getStudentId());
        values.put(KEY_SEMESTER_ID, semester.getSemesterId());
        values.put(KEY_STATUS, semester.isComplete());

        long result = db.insert(TABLE_NAME, null, values);
        helper.closeDatabase(db);
        return result;
    }
    public List<PlanSemester> getSemesterByStudentId(String studentId) {
        List<PlanSemester> semesterList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                + KEY_STUDENT_ID + " = '" + studentId + "'";

        db = helper.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);

        PlanSemester semester = null;
        if (cursor.moveToFirst()) {

            do {
            semester = new PlanSemester();
            semester.setPlanSemesterId(cursor.getInt(cursor.getColumnIndex(KEY_PLAN_SEMESTER_ID)));
            semester.setPlanSemesterName(cursor.getString(cursor.getColumnIndex(KEY_PLAN_SEMESTER_NAME)));
            semester.setStudentId(cursor.getString(cursor.getColumnIndex(KEY_STUDENT_ID)));
            semester.setSemesterId(cursor.getString(cursor.getColumnIndex(KEY_SEMESTER_ID)));
            int status = cursor.getInt(cursor.getColumnIndex(KEY_STATUS));
            semester.setComplete(status == 1);

            semesterList.add(semester);
            } while (cursor.moveToNext());
        }

        helper.closeDatabase(db);
        return semesterList;
    }

    public PlanSemester getSemesterByStudentAndSemester(String studentId, String semesterId) {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                + KEY_STUDENT_ID + " = '" + studentId + "' AND " + KEY_SEMESTER_ID + " = '" + semesterId + "'";

        db = helper.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);

        PlanSemester semester = null;
        if (cursor.moveToFirst()) {
            semester = new PlanSemester();
            semester.setPlanSemesterId(cursor.getInt(cursor.getColumnIndex(KEY_PLAN_SEMESTER_ID)));
            semester.setPlanSemesterName(cursor.getString(cursor.getColumnIndex(KEY_PLAN_SEMESTER_NAME)));
            semester.setStudentId(cursor.getString(cursor.getColumnIndex(KEY_STUDENT_ID)));
            semester.setSemesterId(cursor.getString(cursor.getColumnIndex(KEY_SEMESTER_ID)));
            int status = cursor.getInt(cursor.getColumnIndex(KEY_STATUS));
            semester.setComplete(status == 1);
        }

        helper.closeDatabase(db);
        return semester;
    }

}
