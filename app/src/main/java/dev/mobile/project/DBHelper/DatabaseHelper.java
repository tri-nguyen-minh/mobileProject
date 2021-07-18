package dev.mobile.project.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public void closeDatabase(SQLiteDatabase db) {
        if (db != null && db.isOpen())
            db.close();
    }

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MobileDB";

    private static final String CREATE_TABLE_SEMESTER = "CREATE TABLE \"Semesters\" (\n" +
            "\"SemesterId\"\tTEXT NOT NULL,\n" +
            "\"SemesterName\"\tTEXT NOT NULL,\n" +
            "\"StartDate\"\tDATETIME NOT NULL,\n" +
            "\"EndDate\"\tDATETIME NOT NULL,\n" +
            "\"IsComplete\"\tBIT NOT NULL\n" +
            ");";

    private static final String CREATE_TABLE_PLAN_SEMESTER = "CREATE TABLE \"PlanSemester\" (\n" +
            "\"PlanSemesterId\"\tINTEGER NOT NULL,\n" +
            "\"PlanSemesterName\"\tTEXT NOT NULL,\n" +
            "\"StudentId\"\tTEXT NOT NULL,\n" +
            "\"SemesterId\"\tTEXT NOT NULL,\n" +
            "\"IsComplete\"\tBIT NOT NULL\n" +
            ");";

    private static final String CREATE_TABLE_PLAN_SUBJECT = "CREATE TABLE \"PlanSubject\" (\n" +
            "\"PlanSubjectId\"\tINTEGER NOT NULL,\n" +
            "\"PlanSemesterId\"\tINTEGER NOT NULL,\n" +
            "\"SubjectId\"\tTEXT NOT NULL,\n" +
            "\"Priority\"\tINTEGER NOT NULL,\n" +
            "\"Progress\"\tINTEGER NOT NULL,\n" +
            "\"IsComplete\"\tBIT NOT NULL\n" +
            ");";

    private static final String CREATE_TABLE_PLAN_TOPIC = "CREATE TABLE \"PlanTopic\" (\n" +
            "\"PlanTopicId\"\tINTEGER NOT NULL,\n" +
            "\"TopicId\"\tINTEGER NOT NULL,\n" +
            "\"PlanSubjectId\"\tINTEGER NOT NULL,\n" +
            "\"Progress\"\tINTEGER NOT NULL,\n" +
            "\"IsComplete\"\tBIT NOT NULL\n" +
            ");";

    private static final String CREATE_TABLE_TASK = "CREATE TABLE \"Tasks\" (\n" +
            "\"TaskId\"\tINTEGER NOT NULL,\n" +
            "\"TaskDescription\"\tTEXT NOT NULL,\n" +
            "\"PlanTopicId\"\tINTEGER NOT NULL,\n" +
            "\"EstimateTime\"\tINTEGER NOT NULL,\n" +
            "\"EffortTime\"\tINTEGER NOT NULL,\n" +
            "\"Priority\"\tINTEGER NOT NULL,\n" +
            "\"CreateDate\"\tTEXT NOT NULL,\n" +
            "\"DueDate\"\tTEXT NOT NULL,\n" +
            "\"IsComplete\"\tBIT NOT NULL\n" +
            ");";

    private static final String CREATE_TABLE_STUDENT = "CREATE TABLE \"Students\" (\n" +
            "\"StudentId\"\tTEXT NOT NULL,\n" +
            "\"StudentName\"\tTEXT NOT NULL,\n" +
            "\"StudentEmail\"\tTEXT NOT NULL\n" +
            ");";

    private static final String CREATE_TABLE_TOPIC = "CREATE TABLE \"Topics\" (\n" +
            "\"TopicId\"\tINTEGER NOT NULL,\n" +
            "\"TopicName\"\tTEXT NOT NULL,\n" +
            "\"TopicDescription\"\tTEXT NOT NULL,\n" +
            "\"SubjectId\"\tTEXT NOT NULL\n" +
            ");";


    private static final String CREATE_TABLE_SUBJECT = "CREATE TABLE \"Subjects\" (\n" +
            "\"SubjectId\"\tTEXT NOT NULL,\n" +
            "\"SubjectName\"\tTEXT NOT NULL\n" +
            ");";


    private static final String DROP_TABLE_SEMESTER = "Drop Table If Exists Semesters";

    private static final String DROP_TABLE_SUBJECT = "Drop Table If Exists Subjects";

    private static final String DROP_TABLE_STUDENT = "Drop Table If Exists Students";

    private static final String DROP_TABLE_PLAN_SEMESTER = "Drop Table If Exists PlanSemester";

    private static final String DROP_TABLE_PLAN_SUBJECT = "Drop Table If Exists PlanSubject";

    private static final String DROP_TABLE_PLAN_TOPIC = "Drop Table If Exists PlanTopic";

    private static final String DROP_TABLE_TOPIC = "Drop Table If Exists Topics";

    private static final String DROP_TABLE_TASK = "Drop Table If Exists Tasks";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SEMESTER);
        db.execSQL(CREATE_TABLE_SUBJECT);
        db.execSQL(CREATE_TABLE_STUDENT);
        db.execSQL(CREATE_TABLE_PLAN_SEMESTER);
        db.execSQL(CREATE_TABLE_PLAN_SUBJECT);
        db.execSQL(CREATE_TABLE_TOPIC);
        db.execSQL(CREATE_TABLE_PLAN_TOPIC);
        db.execSQL(CREATE_TABLE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_SEMESTER);
        db.execSQL(DROP_TABLE_SUBJECT);
        db.execSQL(DROP_TABLE_STUDENT);
        db.execSQL(DROP_TABLE_PLAN_SEMESTER);
        db.execSQL(DROP_TABLE_PLAN_SUBJECT);
        db.execSQL(DROP_TABLE_TOPIC);
        db.execSQL(DROP_TABLE_PLAN_TOPIC);
        db.execSQL(DROP_TABLE_TASK);
        onCreate(db);
    }

}