package dev.mobile.project.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.project.dto.Subject;

public class SubjectDBHelper {

    private static final String TABLE_NAME = "Subjects";
    public static final String KEY_SUBJECT_ID = "SubjectId";
    public static final String KEY_SUBJECT_NAME = "SubjectName";

    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;

    public SubjectDBHelper(DatabaseHelper databaseHelper) {
        helper = databaseHelper;
    }

    public long createSubject(Subject subject) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SUBJECT_ID, subject.getSubjectId());
        values.put(KEY_SUBJECT_NAME, subject.getSubjectName());

        long result = db.insert(TABLE_NAME, null, values);
        helper.closeDatabase(db);
        return result;
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        db = helper.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);
        Subject subject;
        if (cursor.moveToFirst()) {
            do {
                subject = new Subject();
                subject.setSubjectId(cursor.getString(cursor.getColumnIndex(KEY_SUBJECT_ID)));
                subject.setSubjectName(cursor.getString(cursor.getColumnIndex(KEY_SUBJECT_NAME)));

                subjects.add(subject);
            } while (cursor.moveToNext());
        }

        helper.closeDatabase(db);
        return subjects;
    }

    public Subject getSubjectById(String subjectId) {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                + KEY_SUBJECT_ID + " = '" + subjectId + "'";

        db = helper.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);

        Subject subject = null;
        if (cursor.moveToFirst()) {
            
            subject = new Subject();
            subject.setSubjectId(cursor.getString(cursor.getColumnIndex(KEY_SUBJECT_ID)));
            subject.setSubjectName(cursor.getString(cursor.getColumnIndex(KEY_SUBJECT_NAME)));
        }

        helper.closeDatabase(db);
        return subject;
    }
}
