package dev.mobile.project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dev.mobile.project.DBHelper.DatabaseHelper;
import dev.mobile.project.DBHelper.PlanSemesterDBHelper;
import dev.mobile.project.DBHelper.PlanSubjectDBHelper;
import dev.mobile.project.DBHelper.PlanTopicDBHelper;
import dev.mobile.project.DBHelper.SemesterDBHelper;
import dev.mobile.project.DBHelper.TaskDBHelper;
import dev.mobile.project.DBHelper.TopicDBHelper;
import dev.mobile.project.R;
import dev.mobile.project.dto.PlanSemester;
import dev.mobile.project.dto.PlanSubject;
import dev.mobile.project.dto.PlanTopic;
import dev.mobile.project.dto.Semester;
import dev.mobile.project.dto.Task;
import dev.mobile.project.dto.Topic;

public class TaskActivity extends AppCompatActivity {

    private Intent intent;
    private TextView txtCommon, txtAdd, txtDelete, txtUpdate, txtCheckInput;
    private Spinner spinnerSubject, spinnerTopic, spinnerPriority, spinnerStatus;
    private ArrayAdapter<String> dataAdapter;
    private EditText editCommon;
    private RelativeLayout layoutSelectDate, imgTaskCalendar;
    private CalendarView calendarView;
    private ConstraintLayout layoutAddTask, layoutUpdateTask;
    private LinearLayout layoutCommon;

    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private int taskId, index;
    private String studentId = "se140329", subjectId;
    private Task task, newTask;
    private Semester currentSemester;
    private PlanSemester currentPlanSemester;
    private PlanTopic planTopic;
    private List<PlanSubject> planSubjectList;
    private List<Topic> topicList;
    private ArrayList<String> spinnerData;

    private DatabaseHelper db;
    private TaskDBHelper taskDBHelper;
    private SemesterDBHelper semesterDBHelper;
    private PlanSemesterDBHelper planSemesterDBHelper;
    private PlanSubjectDBHelper planSubjectDBHelper;
    private TopicDBHelper topicDBHelper;
    private PlanTopicDBHelper planTopicDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);


        db = new DatabaseHelper(getApplicationContext());
        taskDBHelper = new TaskDBHelper(db);
        semesterDBHelper = new SemesterDBHelper(db);
        planSemesterDBHelper = new PlanSemesterDBHelper(db);
        planSubjectDBHelper = new PlanSubjectDBHelper(db);
        topicDBHelper = new TopicDBHelper(db);
        planTopicDBHelper = new PlanTopicDBHelper(db);

        intent = getIntent();

        studentId = intent.getStringExtra("STUDENT_ID");
        taskId = intent.getIntExtra("TASK_ID", 0);
        subjectId = intent.getStringExtra("SUBJECT_ID");


        task = taskDBHelper.getTaskById(taskId);

        spinnerSubject = findViewById(R.id.spinnerTaskSubject);
        spinnerTopic = findViewById(R.id.spinnerTaskTopic);
        spinnerPriority = findViewById(R.id.spinnerTaskPriority);
        spinnerStatus =findViewById(R.id.spinnerTaskStatus);
        layoutSelectDate = findViewById(R.id.layoutSelectDate);
        imgTaskCalendar = findViewById(R.id.imgTaskCalendar);
        calendarView = findViewById(R.id.calendarTaskDueDate);
        layoutAddTask = findViewById(R.id.layoutAddTask);
        layoutUpdateTask = findViewById(R.id.layoutUpdateTask);
        txtAdd = findViewById(R.id.txtActionTaskAdd);
        txtUpdate = findViewById(R.id.txtActionTaskUpdate);
        txtDelete = findViewById(R.id.txtActionTaskDelete);
        txtCheckInput = findViewById(R.id.txtTaskInputCheck);

        calendarView.setMinDate((new Date().getTime()));
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                txtCommon = findViewById(R.id.txtTaskDueDate);
                txtCommon.setText(year + "-" + (((month + 1) < 10) ? "0" + (month + 1) : (month + 1)) + "-" + ((dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth));
                layoutSelectDate.setVisibility(View.GONE);
            }
        });

        layoutSelectDate.setVisibility(View.GONE);

        imgTaskCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutSelectDate.setVisibility(View.VISIBLE);
            }
        });

        currentSemester = semesterDBHelper.getOngoingSemester();
        currentPlanSemester = planSemesterDBHelper.getSemesterByStudentAndSemester(studentId, currentSemester.getSemesterId());

        planSubjectList = planSubjectDBHelper.getAllSubjectsByPlanSemester(currentPlanSemester.getPlanSemesterId());

        spinnerData = new ArrayList<>();
        for (PlanSubject subject : planSubjectList) {
            spinnerData.add(subject.getSubjectId());
        }
        if (subjectId != null) {
            index = (spinnerData.indexOf(subjectId) < 0) ? 0 : spinnerData.indexOf(subjectId);
        }
        dataAdapter = new ArrayAdapter<>(TaskActivity.this, android.R.layout.simple_spinner_item, spinnerData);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubject.setAdapter(dataAdapter);
        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setupTopicList(planSubjectList.get(position).getSubjectId());
                spinnerTopic.setSelection(getTopic(topicList));
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerData = new ArrayList<>();
        spinnerData.add("Not Urgent");
        spinnerData.add("Urgent");
        dataAdapter = new ArrayAdapter<>(TaskActivity.this, android.R.layout.simple_spinner_item, spinnerData);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(dataAdapter);

        spinnerData = new ArrayList<>();
        spinnerData.add("Ongoing");
        spinnerData.add("Finished");
        dataAdapter = new ArrayAdapter<>(TaskActivity.this, android.R.layout.simple_spinner_item, spinnerData);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(dataAdapter);

        if (task != null) {
            planTopic = planTopicDBHelper.getPlanTopicByPlanTopicId(task.getPlanTopicId());
            index = getSubject(planSubjectList);
            editCommon = findViewById(R.id.editTaskDescription);
            editCommon.setText(task.getDescription());
            spinnerPriority.setSelection(task.getPriority());
            txtCommon = findViewById(R.id.txtTaskDueDate);
            txtCommon.setText(task.getDueDate());
            editCommon = findViewById(R.id.editTaskEstimateTime);
            editCommon.setText(task.getEstimateTime() + "");
            editCommon = findViewById(R.id.editTaskEffortTime);
            editCommon.setText(task.getEffortTime() + "");
            spinnerStatus.setSelection(task.isComplete() ? 1 : 0);
            layoutUpdateTask.setVisibility(View.VISIBLE);
            layoutAddTask.setVisibility(View.GONE);

        } else {
            txtCommon = findViewById(R.id.txtTaskDueDate);
            txtCommon.setText(format.format(Calendar.getInstance().getTime()));
            layoutUpdateTask.setVisibility(View.GONE);
            layoutAddTask.setVisibility(View.VISIBLE);
            editCommon = findViewById(R.id.editTaskEstimateTime);
            editCommon.setText("");
            editCommon = findViewById(R.id.editTaskEffortTime);
            editCommon.setText("");
            editCommon.setEnabled(false);
            spinnerStatus.setEnabled(false);
            layoutCommon = findViewById(R.id.layoutTaskEffortTime);
            layoutCommon.setVisibility(View.GONE);
            layoutCommon = findViewById(R.id.layoutTaskStatus);
            layoutCommon.setVisibility(View.GONE);
        }
        spinnerSubject.setSelection(index);

        txtCheckInput.setVisibility(View.GONE);
        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCommon = findViewById(R.id.editTaskEffortTime);
                editCommon.setText("0");
                if (!checkValidInput()) {
                    txtCheckInput.setVisibility(View.VISIBLE);
                } else {

                    int topicId = topicList.get(spinnerTopic.getSelectedItemPosition()).getTopicId();
                    int planSubjectId = planSubjectList.get(spinnerSubject.getSelectedItemPosition()).getPlanSubjectId();
                    int planTopicId = planTopicDBHelper.getCount() + 1;
                    planTopic = planTopicDBHelper.getPlanTopicByTopicAndPlanSubject(topicId, planSubjectId);
                    System.out.println(topicId);
                    System.out.println(planSubjectId);
                    System.out.println(planTopicId);
                    System.out.println(planTopic == null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Add Task");
                    builder.setMessage("Do you want to Add this task?");
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    newTask = getDataTask();
                                    newTask.setCreateDate(format.format(Calendar.getInstance().getTime()));
                                    taskDBHelper.createTask(newTask);
                                    resetSubject();
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
            }
        });

        txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkValidInput()) {
                    txtCheckInput.setVisibility(View.VISIBLE);
                } else {

                    int topicId = topicList.get(spinnerTopic.getSelectedItemPosition()).getTopicId();
                    int planSubjectId = planSubjectList.get(spinnerSubject.getSelectedItemPosition()).getPlanSubjectId();
                    int planTopicId = planTopicDBHelper.getCount() + 1;
                    planTopic = planTopicDBHelper.getPlanTopicByTopicAndPlanSubject(topicId, planSubjectId);
                    System.out.println(topicId);
                    System.out.println(planSubjectId);
                    System.out.println(planTopicId);
                    System.out.println(planTopic == null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Update Task");
                    builder.setMessage("Do you want to Update this task?");
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    newTask = getDataTask();
                                    newTask.setCreateDate(task.getCreateDate());
                                    taskDBHelper.updateTask(newTask);
                                    resetSubject();
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
            }
        });

        txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Delete Task");
                builder.setMessage("Do you want to Delete this task?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                taskDBHelper.deleteTask(task.getTaskId());
                                resetSubject();
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

        this.getSupportActionBar().hide();
    }

    private int getSubject(List<PlanSubject> list) {
        if (planTopic != null) {
            for (PlanSubject subject : list) {
                if (subject.getPlanSubjectId() == planTopic.getPlanSubjectId()) {
                    return list.indexOf(subject);
                }
            }
        }
        return 0;
    }
    private int getTopic(List<Topic> list) {
        if (planTopic != null) {
            for (Topic topic : list) {
                if (topic.getTopicId() == planTopic.getTopicId()) {
                    return list.indexOf(topic);
                }
            }
        }
        return 0;
    }

    private void setupTopicList(String subjectId) {
        topicList = topicDBHelper.getAllTopicsBySubjectId(subjectId);
        spinnerData = new ArrayList<>();
        for (Topic topic : topicList) {
            spinnerData.add(topic.getTopicName());
        }
        dataAdapter = new ArrayAdapter<>(TaskActivity.this, android.R.layout.simple_spinner_item, spinnerData);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTopic.setAdapter(dataAdapter);
    }

    private boolean checkValidInput() {
        editCommon = findViewById(R.id.editTaskDescription);
        if (editCommon.getText().toString().isEmpty()) {
            return false;
        }
        editCommon = findViewById(R.id.editTaskEstimateTime);
        if (editCommon.getText().toString().isEmpty()) {
            return false;
        }
        editCommon = findViewById(R.id.editTaskEffortTime);
        if (editCommon.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    private Task getDataTask() {
        newTask = new Task();
        int taskId = taskDBHelper.getCount() + 1;
        if (task != null) {
            taskId = task.getTaskId();
        }
        int topicId = topicList.get(spinnerTopic.getSelectedItemPosition()).getTopicId();
        int planSubjectId = planSubjectList.get(spinnerSubject.getSelectedItemPosition()).getPlanSubjectId();
        int planTopicId = planTopicDBHelper.getCount() + 1;
        planTopic = planTopicDBHelper.getPlanTopicByTopicAndPlanSubject(topicId, planSubjectId);
        if (planTopic == null) {
            planTopic = new PlanTopic(planTopicId, topicId, planSubjectId, 0, false);
            planTopicDBHelper.createTopic(planTopic);
        } else {
            planTopicId = planTopic.getPlanTopicId();
        }
        newTask.setTaskId(taskId);
        newTask.setPlanTopicId(planTopicId);
        editCommon = findViewById(R.id.editTaskDescription);
        newTask.setDescription(editCommon.getText().toString());
        editCommon = findViewById(R.id.editTaskEstimateTime);
        newTask.setEstimateTime(Integer.parseInt(editCommon.getText().toString()));
        editCommon = findViewById(R.id.editTaskEffortTime);
        newTask.setEffortTime(Integer.parseInt(editCommon.getText().toString()));
        newTask.setPriority(spinnerPriority.getSelectedItemPosition());
        txtCommon = findViewById(R.id.txtTaskDueDate);
        newTask.setDueDate(txtCommon.getText().toString());
        newTask.setComplete(spinnerStatus.getSelectedItemPosition() == 1);

        return newTask;
    }

    private void resetSubject() {
        intent = new Intent(getApplicationContext(), SubjectActivity.class);
        intent.putExtra("STUDENT_ID", studentId);
        intent.putExtra("PLAN_SUBJECT_ID", planSubjectList.get(spinnerSubject.getSelectedItemPosition()).getPlanSubjectId());
        startActivity(intent);
    }

    public void onBack(View view) {
        finish();
    }
}