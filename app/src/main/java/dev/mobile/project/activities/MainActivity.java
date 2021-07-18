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

    void setData() {

        Semester semester = new Semester("FAL_20", "Fall 2020", "2021-01-04", "2021-04-29", true);
        semesterDBHelper.createSemester(semester);
        semester = new Semester("SPR_21", "Spring 2021", "2021-01-04", "2021-04-29", true);
        semesterDBHelper.createSemester(semester);
        semester = new Semester("SUM_21", "Summer 2021", "2021-05-10", "2021-07-23", false);
        semesterDBHelper.createSemester(semester);

        Subject subject = new Subject("CEA201", "Computer Organization and Architecture");
        subjectDBHelper.createSubject(subject);
        subject = new Subject("CSD201", "Data Structure and Algorithm");
        subjectDBHelper.createSubject(subject);
        subject = new Subject("HCI201", "Human Computer Interaction");
        subjectDBHelper.createSubject(subject);
        subject = new Subject("HCM201", "Ho Chi Minh Ideology");
        subjectDBHelper.createSubject(subject);
        subject = new Subject("ISC301", "E Commerce");
        subjectDBHelper.createSubject(subject);
        subject = new Subject("PRM391", "Mobile Programming");
        subjectDBHelper.createSubject(subject);
        subject = new Subject("PRO192", "Object-Oriented Paradigm using Java");
        subjectDBHelper.createSubject(subject);
        subject = new Subject("SWD391", "Software Architecture & Design");
        subjectDBHelper.createSubject(subject);

        studentDBHelper.createStudent(new Student("se140329", "Nguyen Minh Tri", "trinmse140329@fpt.edu.vn"));

        planSemesterDBHelper.createSemester(new PlanSemester(1, "OJT", "se140329", "SPR_21", true));
        planSemesterDBHelper.createSemester(new PlanSemester(2, "Hell", "se140329", "SUM_21", false));

        planSubjectDBHelper.createPlanSubject(new PlanSubject(1, 1, 0, 0, "PRM391", true));
        planSubjectDBHelper.createPlanSubject(new PlanSubject(2, 1, 0, 0, "SWD391", true));
        planSubjectDBHelper.createPlanSubject(new PlanSubject(3, 2, 0, 0, "PRM391", false));
        planSubjectDBHelper.createPlanSubject(new PlanSubject(4, 2, 0, 0, "SWD391", false));
        planSubjectDBHelper.createPlanSubject(new PlanSubject(5, 2, 0, 0, "HCI201", false));
        planSubjectDBHelper.createPlanSubject(new PlanSubject(6, 2, 0, 0, "ISC301", false));

        topicDBHelper.createTopic(new Topic(1, "Project", "Set task for your Project", "SWD391"));
        topicDBHelper.createTopic(new Topic(2, "Flutter", "Learn how to create app with Flutter and Dart Language", "SWD391"));
        topicDBHelper.createTopic(new Topic(3, "RestAPI", "Learn about RestAPI and how to implement it", "SWD391"));
        topicDBHelper.createTopic(new Topic(4, "Project", "Set task for your Project", "PRM391"));
        topicDBHelper.createTopic(new Topic(5, "Flutter", "Learn how to create app with Flutter and Dart Language", "PRM391"));
        topicDBHelper.createTopic(new Topic(6, "Android Studio", "Learn how to create Android apps with Android Studio", "PRM391"));
        topicDBHelper.createTopic(new Topic(7, "SQLite", "Learn how to connect SQLite Database to your Android apps", "PRM391"));
        topicDBHelper.createTopic(new Topic(8, "Chapter 1", "What is interaction design?", "HCI201"));
        topicDBHelper.createTopic(new Topic(9, "Chapter 2", "Understanding and conceptualizing interaction", "HCI201"));
        topicDBHelper.createTopic(new Topic(10, "Chapter 3", "Understanding users", "HCI201"));
        topicDBHelper.createTopic(new Topic(11, "Chapter 4", "Designing for collaboration and communication", "HCI201"));
        topicDBHelper.createTopic(new Topic(12, "Chapter 5", "Affective aspects", "HCI201"));
        topicDBHelper.createTopic(new Topic(13, "Project", "Design an Interactive Mobile App", "HCI201"));
        topicDBHelper.createTopic(new Topic(14, "Chapter 1", "Overview of E-Commerce", "ISC301"));
        topicDBHelper.createTopic(new Topic(15, "Chapter 2", "E-Marketplace: structures, mechanisms, economics, and impacts", "ISC301"));

        planTopicDBHelper.createTopic(new PlanTopic(1, 4, 3, 0, false));
        planTopicDBHelper.createTopic(new PlanTopic(2, 6, 3, 0, false));
        planTopicDBHelper.createTopic(new PlanTopic(3, 1, 4, 0, false));
        planTopicDBHelper.createTopic(new PlanTopic(4, 2, 4, 0, false));
        planTopicDBHelper.createTopic(new PlanTopic(5, 3, 4, 0, false));
        planTopicDBHelper.createTopic(new PlanTopic(6, 13, 5, 0, false));
        planTopicDBHelper.createTopic(new PlanTopic(7, 15, 6, 0, false));

        taskDBHelper.createTask(new Task(1, 1, 70, 10, 1, "Design Database", "2021-08-01", "2021-05-12", false));
        taskDBHelper.createTask(new Task(2, 2, 50, 20, 0, "Do Demo", "2021-08-01", "2021-05-12", false));
        taskDBHelper.createTask(new Task(3, 3, 60, 30, 1, "Design Database", "2021-08-01", "2021-05-12", false));
        taskDBHelper.createTask(new Task(4, 4, 70, 10, 1, "Learn Flutter", "2021-08-01", "2021-05-12", false));
        taskDBHelper.createTask(new Task(5, 5, 30, 18, 1, "Create API", "2021-08-01", "2021-05-12", false));
        taskDBHelper.createTask(new Task(6, 6, 60, 20, 1, "Build Project", "2021-08-01", "2021-05-12", false));
        taskDBHelper.createTask(new Task(7, 7, 50, 15, 0, "EduNext", "2021-08-01", "2021-05-12", false));

    }
}