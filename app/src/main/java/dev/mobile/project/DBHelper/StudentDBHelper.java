package dev.mobile.project.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import dev.mobile.project.dto.Student;

public class StudentDBHelper {

    private static final String TABLE_NAME = "Students";
    public static final String KEY_STUDENT_ID = "StudentId";
    public static final String KEY_STUDENT_NAME = "StudentName";
    public static final String KEY_STUDENT_EMAIL = "StudentEmail";

    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;

    public StudentDBHelper(DatabaseHelper databaseHelper) {
        helper = databaseHelper;
    }

    public long createStudent(Student student) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STUDENT_ID, student.getStudentId());
        values.put(KEY_STUDENT_NAME, student.getStudentName());
        values.put(KEY_STUDENT_EMAIL, student.getEmail());

        long result = db.insert(TABLE_NAME, null, values);
        helper.closeDatabase(db);
        return result;
    }

    public Student getStudentByMail(String mail) {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                + KEY_STUDENT_EMAIL + " = '" + mail + "'";

        db = helper.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);

        Student student = null;
        if (cursor.moveToFirst()) {
            
            student = new Student();
            student.setStudentId(cursor.getString(cursor.getColumnIndex(KEY_STUDENT_ID)));
            student.setStudentName(cursor.getString(cursor.getColumnIndex(KEY_STUDENT_NAME)));
            student.setEmail(cursor.getString(cursor.getColumnIndex(KEY_STUDENT_EMAIL)));

        }
        helper.closeDatabase(db);
        return student;
    }

    public Student getStudentByStudentId(String id) {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                + KEY_STUDENT_ID + " = '" + id + "'";

        db = helper.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);

        Student student = null;
        if (cursor.moveToFirst()) {

            student = new Student();
            student.setStudentId(cursor.getString(cursor.getColumnIndex(KEY_STUDENT_ID)));
            student.setStudentName(cursor.getString(cursor.getColumnIndex(KEY_STUDENT_NAME)));
            student.setEmail(cursor.getString(cursor.getColumnIndex(KEY_STUDENT_EMAIL)));

        }
        helper.closeDatabase(db);
        return student;
    }
}
