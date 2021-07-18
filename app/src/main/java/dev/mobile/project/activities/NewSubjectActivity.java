package dev.mobile.project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import dev.mobile.project.DBHelper.DatabaseHelper;
import dev.mobile.project.DBHelper.PlanSemesterDBHelper;
import dev.mobile.project.DBHelper.PlanSubjectDBHelper;
import dev.mobile.project.DBHelper.SemesterDBHelper;
import dev.mobile.project.DBHelper.SubjectDBHelper;
import dev.mobile.project.DBHelper.TopicDBHelper;
import dev.mobile.project.R;
import dev.mobile.project.dto.PlanSemester;
import dev.mobile.project.dto.PlanSubject;
import dev.mobile.project.dto.Semester;
import dev.mobile.project.dto.Subject;
import dev.mobile.project.dto.Topic;
import dev.mobile.project.recycleviewadapter.RecViewTopicAdapter;

public class NewSubjectActivity extends AppCompatActivity {

    private String subjectId, studentId;
    private Intent intent;
    private TextView txtCommon;
    private LinearLayout layoutSubjectStatus;
    private ImageView imgSubjectStatus;
    private RecyclerView recViewCommon;

    private Subject subject;
    private Semester currentSemester;
    private PlanSemester planSemester;
    private List<PlanSubject> subjectList;

    private DatabaseHelper db;
    private SubjectDBHelper subjectDBHelper;
    private SemesterDBHelper semesterDBHelper;
    private PlanSemesterDBHelper planSemesterDBHelper;
    private PlanSubjectDBHelper planSubjectDBHelper;
    private TopicDBHelper topicDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_subject);

        db = new DatabaseHelper(getApplicationContext());
        subjectDBHelper = new SubjectDBHelper(db);
        semesterDBHelper = new SemesterDBHelper(db);
        planSemesterDBHelper = new PlanSemesterDBHelper(db);
        planSubjectDBHelper = new PlanSubjectDBHelper(db);
        topicDBHelper = new TopicDBHelper(db);

        intent = getIntent();
        studentId = intent.getStringExtra("STUDENT_ID");
        subjectId = intent.getStringExtra("SUBJECT_ID");

        currentSemester = semesterDBHelper.getOngoingSemester();
        planSemester = planSemesterDBHelper.getSemesterByStudentAndSemester(studentId, currentSemester.getSemesterId());
        if (planSemester == null) {
            planSemester = new PlanSemester();
            planSemester.setPlanSemesterId((planSemesterDBHelper.getCount() + 1));
            planSemester.setStudentId(studentId);
            planSemester.setSemesterId(currentSemester.getSemesterId());
            planSemester.setComplete(false);
            planSemester.setPlanSemesterName(currentSemester.getSemesterName());
            planSemesterDBHelper.createSemester(planSemester);
        }

        recViewCommon = findViewById(R.id.recViewTopic);
        RecViewTopicAdapter adapter = new RecViewTopicAdapter(getApplicationContext(), NewSubjectActivity.this, db);
        adapter.setTopicList(topicDBHelper.getAllTopicsBySubjectId(subjectId));
        adapter.setPlanSubjectId(0);
        adapter.setSubject(subject);
        adapter.setRecViewLayout(R.layout.recycle_view_topic_simple_card);
        recViewCommon.setAdapter(adapter);
        recViewCommon.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false));

        subjectList = planSubjectDBHelper.getAllSubjectsByPlanSemester(planSemester.getPlanSemesterId());
        layoutSubjectStatus = findViewById(R.id.layoutSubjectStatus);
        imgSubjectStatus = findViewById(R.id.imgSubjectStatus);
        if (checkAddedSubject()) {
            imgSubjectStatus.setImageResource(R.drawable.ic_check);
        } else {
            imgSubjectStatus.setImageResource(R.drawable.ic_add);
            layoutSubjectStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewSubjectActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Add Subject");
                    builder.setMessage("Do you want to Add this Subject?");
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    PlanSubject planSubject = new PlanSubject();
                                    planSubject.setPlanSubjectId(planSubjectDBHelper.getCount() + 1);
                                    planSubject.setProgress(0);
                                    planSubject.setPriority(0);
                                    planSubject.setPlanSemesterId(planSemester.getPlanSemesterId());
                                    planSubject.setSubjectId(subjectId);
                                    planSubject.setComplete(false);
                                    planSubjectDBHelper.createPlanSubject(planSubject);

                                    intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("STUDENT_ID", studentId);
                                    startActivity(intent);
                                }
                            });
                    builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }

        subject = subjectDBHelper.getSubjectById(subjectId);
        txtCommon = findViewById(R.id.txtSubjectId);
        txtCommon.setText(subject.getSubjectId());
        txtCommon = findViewById(R.id.txtSubjectName);
        txtCommon.setText(subject.getSubjectName());

        this.getSupportActionBar().hide();
    }

    private boolean checkAddedSubject() {
        for (PlanSubject subject : subjectList) {
            if (subject.getSubjectId().equals(subjectId)) {
                return true;
            }
        }
        return false;
    }

    public void onBack(View view) {
        finish();
    }
}