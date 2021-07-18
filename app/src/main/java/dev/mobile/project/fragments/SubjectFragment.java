package dev.mobile.project.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.project.DBHelper.DatabaseHelper;
import dev.mobile.project.DBHelper.PlanSemesterDBHelper;
import dev.mobile.project.DBHelper.PlanSubjectDBHelper;
import dev.mobile.project.DBHelper.SemesterDBHelper;
import dev.mobile.project.R;
import dev.mobile.project.activities.SearchSubjectActivity;
import dev.mobile.project.dto.PlanSemester;
import dev.mobile.project.dto.PlanSubject;
import dev.mobile.project.dto.Semester;
import dev.mobile.project.recycleviewadapter.RecViewPlanSubjectAdapter;

public class SubjectFragment extends Fragment {

    private String studentId;
    private Intent intent;
    private Semester selectedSemester;
    private PlanSemester selectedPlanSemester;

    private Spinner spinnerSemester;
    private TextView txtCommon;
    private RecyclerView recViewCommon;
    private ImageView imgCommon;

    private DatabaseHelper db;
    private SemesterDBHelper semesterDBHelper;
    private RecViewPlanSubjectAdapter adapter;
    private PlanSemesterDBHelper planSemesterDBHelper;
    private PlanSubjectDBHelper planSubjectDBHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        db = new DatabaseHelper(getContext());
        semesterDBHelper = new SemesterDBHelper(db);
        planSemesterDBHelper = new PlanSemesterDBHelper(db);
        planSubjectDBHelper = new PlanSubjectDBHelper(db);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_1_subject, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        intent = getActivity().getIntent();
        studentId = intent.getStringExtra("STUDENT_ID");
        if (studentId == null) {
            studentId = "se140329";
        }
        spinnerSemester = getView().findViewById(R.id.spinnerSemester);
        List<Semester> semesterList = semesterDBHelper.getAllSemesters();
        ArrayList<String> spinnerData = new ArrayList<>();
        for (Semester semester : semesterList) {
            spinnerData.add(semester.getSemesterName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerData);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemester.setAdapter(dataAdapter);
        spinnerSemester.setSelection(semesterList.size() - 1);
        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            List<PlanSubject> subjectsList;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSemester = semesterList.get(position);
                txtCommon = getView().findViewById(R.id.txtSemesterStartDate);
                txtCommon.setText(selectedSemester.getStartDate());
                txtCommon = getView().findViewById(R.id.txtSemesterEndDate);
                txtCommon.setText(selectedSemester.getEndDate());
                selectedPlanSemester = planSemesterDBHelper.getSemesterByStudentAndSemester(studentId, selectedSemester.getSemesterId());
                txtCommon = getView().findViewById(R.id.txtPlanSemesterName);
                if (selectedPlanSemester != null) {
                    txtCommon.setText(selectedPlanSemester.getPlanSemesterName());
                    subjectsList = planSubjectDBHelper.getAllSubjectsByPlanSemester(selectedPlanSemester.getPlanSemesterId());
                } else {
                    txtCommon.setText(selectedSemester.getSemesterName());
                    subjectsList = new ArrayList<>();
                }
                txtCommon = getView().findViewById(R.id.txtSubjectCount);
                txtCommon.setText(subjectsList.size() + "");
                setupSubjectList(subjectsList);
                imgCommon = getView().findViewById(R.id.imgAddSubject);
                if (selectedSemester.isComplete() || subjectsList.size() >= 10) {
                    imgCommon.setVisibility(View.GONE);
                } else {
                    imgCommon.setVisibility(View.VISIBLE);
                }
                imgCommon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(getContext(), SearchSubjectActivity.class);
                        intent.putExtra("STUDENT_ID", studentId);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setupSubjectList(List<PlanSubject> subjectsList) {
        recViewCommon = getView().findViewById(R.id.recViewSubjectList);
        adapter = new RecViewPlanSubjectAdapter(getContext(), getActivity(), db);
        adapter.setSubjectList(subjectsList);
        recViewCommon.setAdapter(adapter);
        recViewCommon.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
    }
}