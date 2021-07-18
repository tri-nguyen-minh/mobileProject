package dev.mobile.project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import dev.mobile.project.DBHelper.DatabaseHelper;
import dev.mobile.project.DBHelper.PlanSubjectDBHelper;
import dev.mobile.project.DBHelper.PlanTopicDBHelper;
import dev.mobile.project.DBHelper.SubjectDBHelper;
import dev.mobile.project.DBHelper.TaskDBHelper;
import dev.mobile.project.DBHelper.TopicDBHelper;
import dev.mobile.project.R;
import dev.mobile.project.dto.PlanSubject;
import dev.mobile.project.dto.Subject;
import dev.mobile.project.dto.Topic;
import dev.mobile.project.recycleviewadapter.RecViewTopicAdapter;

public class SubjectActivity extends AppCompatActivity {

    private Intent intent;
    private TextView txtCommon, txtAddTask;
    private RecyclerView recViewCommon;

    private DatabaseHelper db;
    private SubjectDBHelper subjectDBHelper;
    private PlanSubjectDBHelper planSubjectDBHelper;
    private TopicDBHelper topicDBHelper;
    private PlanTopicDBHelper planTopicDBHelper;
    private TaskDBHelper taskDBHelper;

    private String subjectId, studentId;
    private int planSubjectId;
    private PlanSubject planSubject;
    private Subject subject;
    private List<Topic> topicList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        db = new DatabaseHelper(getApplicationContext());
        subjectDBHelper = new SubjectDBHelper(db);
        planSubjectDBHelper = new PlanSubjectDBHelper(db);
        topicDBHelper = new TopicDBHelper(db);
        planTopicDBHelper = new PlanTopicDBHelper(db);
        taskDBHelper = new TaskDBHelper(db);

        intent = getIntent();
        studentId = intent.getStringExtra("STUDENT_ID");
        planSubjectId = intent.getIntExtra("PLAN_SUBJECT_ID", 0);
        planSubject = planSubjectDBHelper.getPlanSubjectsById(planSubjectId);
        subjectId = planSubject.getSubjectId();

        subject = subjectDBHelper.getSubjectById(subjectId);

        txtCommon = findViewById(R.id.txtSubjectId);
        txtCommon.setText(subject.getSubjectId());
        txtCommon = findViewById(R.id.txtSubjectName);
        txtCommon.setText(subject.getSubjectName());

        topicList = topicDBHelper.getAllTopicsBySubjectId(subjectId);
        recViewCommon = findViewById(R.id.recViewTopic);

        RecViewTopicAdapter adapter = new RecViewTopicAdapter(getApplicationContext(), SubjectActivity.this, db);
        adapter.setTopicList(topicList);
        adapter.setPlanSubjectId(planSubjectId);
        adapter.setSubject(subject);
        adapter.setRecViewLayout(R.layout.recycle_view_topic_card);
        recViewCommon.setAdapter(adapter);
        recViewCommon.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false));

        txtAddTask = findViewById(R.id.txtAddTask);

        if (planSubject.isComplete()) {
            txtAddTask.setVisibility(View.GONE);
        } else {
            txtAddTask.setVisibility(View.VISIBLE);
            txtAddTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(getApplicationContext(), TaskActivity.class);
                    intent.putExtra("SUBJECT_ID", subjectId);
                    intent.putExtra("STUDENT_ID", studentId);
                    startActivity(intent);
                }
            });
        }

        this.getSupportActionBar().hide();
    }

    public void onBack(View view) {
        intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("STUDENT_ID", studentId);
        startActivity(intent);
    }
}