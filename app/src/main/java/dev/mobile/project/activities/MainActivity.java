package dev.mobile.project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import dev.mobile.project.DBHelper.DatabaseHelper;
import dev.mobile.project.DBHelper.PlanSemesterDBHelper;
import dev.mobile.project.DBHelper.PlanSubjectDBHelper;
import dev.mobile.project.DBHelper.PlanTopicDBHelper;
import dev.mobile.project.DBHelper.SemesterDBHelper;
import dev.mobile.project.DBHelper.StudentDBHelper;
import dev.mobile.project.DBHelper.SubjectDBHelper;
import dev.mobile.project.DBHelper.TaskDBHelper;
import dev.mobile.project.DBHelper.TopicDBHelper;
import dev.mobile.project.R;
import dev.mobile.project.dto.PlanSemester;
import dev.mobile.project.dto.PlanSubject;
import dev.mobile.project.dto.PlanTopic;
import dev.mobile.project.dto.Semester;
import dev.mobile.project.dto.Student;
import dev.mobile.project.dto.Subject;
import dev.mobile.project.dto.Task;
import dev.mobile.project.dto.Topic;
import dev.mobile.project.navigations.NavigationAdapter;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private SemesterDBHelper semesterDBHelper;
    private SubjectDBHelper subjectDBHelper;
    private StudentDBHelper studentDBHelper;
    private PlanSemesterDBHelper planSemesterDBHelper;
    private PlanSubjectDBHelper planSubjectDBHelper;
    private TopicDBHelper topicDBHelper;
    private PlanTopicDBHelper planTopicDBHelper;
    private TaskDBHelper taskDBHelper;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabLayout.Tab tabCommon;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(getApplicationContext());
        semesterDBHelper = new SemesterDBHelper(db);
        subjectDBHelper = new SubjectDBHelper(db);
        studentDBHelper = new StudentDBHelper(db);
        planSemesterDBHelper = new PlanSemesterDBHelper(db);
        planSubjectDBHelper = new PlanSubjectDBHelper(db);
        topicDBHelper = new TopicDBHelper(db);
        planTopicDBHelper = new PlanTopicDBHelper(db);
        taskDBHelper = new TaskDBHelper(db);

//        setData();
//        taskDBHelper.createTask(new Task(8, 7, 50, 15, 0, "EduNext", "2021-08-01", "2021-05-12", false));
//        taskDBHelper.createTask(new Task(9, 7, 50, 15, 0, "EduNext", "2021-08-01", "2021-05-12", false));

        intent = getIntent();

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);


        tabCommon = tabLayout.newTab();
        tabCommon.setIcon(R.drawable.ic_nav_list);
        tabCommon.setText("Subjects");
        tabCommon.getIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        tabLayout.addTab(tabCommon);

        tabCommon = tabLayout.newTab();
        tabCommon.setIcon(R.drawable.ic_nav_check_task);
        tabCommon.setText("Tasks");
        tabCommon.getIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        tabLayout.addTab(tabCommon);

        tabCommon = tabLayout.newTab();
        tabCommon.setIcon(R.drawable.ic_nav_profile);
        tabCommon.setText("Profile");
        tabCommon.getIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        tabLayout.addTab(tabCommon);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.gold));
        NavigationAdapter adapter = new NavigationAdapter(getSupportFragmentManager(), MainActivity.this,
                MainActivity.this, tabLayout.getTabCount());
        tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.gold), PorterDuff.Mode.SRC_IN);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getIcon() != null)
                    tab.getIcon().setColorFilter(getResources().getColor(R.color.gold), PorterDuff.Mode.SRC_IN);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getIcon() != null)
                    tab.getIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        this.getSupportActionBar().hide();
    }
//
//    public void test(View view) {
//        System.out.println("delete");
//        System.out.println(taskDBHelper.deleteTask(2));
//        List<Task> listTask = taskDBHelper.getAllTask();
//        for (Task s : listTask) {
//            System.out.println("Task: " + s.getDescription());
//        }
//    }
//
//    public void testUpdate(View view) {
//        System.out.println("update");
//        System.out.println(taskDBHelper.updateTask(new Task(7, 7, 50, 15, 0, "EduNext Question", "2021-08-01", "2021-05-12", false)));
//
//        List<Task> listTask = taskDBHelper.getAllTask();
//        for (Task s : listTask) {
//            System.out.println("Task: " + s.getDescription());
//        }
//    }
}