package dev.mobile.project.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import dev.mobile.project.DBHelper.DatabaseHelper;
import dev.mobile.project.DBHelper.StudentDBHelper;
import dev.mobile.project.R;
import dev.mobile.project.activities.LoginActivity;
import dev.mobile.project.activities.TaskActivity;
import dev.mobile.project.dto.Student;

public class ProfileFragment extends Fragment {

    private String studentId;
    private Student student;
    private Intent intent;

    private DatabaseHelper db;
    private StudentDBHelper studentDBHelper;

    private TextView txtCommon;
    private LinearLayout btnLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = new DatabaseHelper(getContext());
        studentDBHelper = new StudentDBHelper(db);
        return inflater.inflate(R.layout.fragment_main_3_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        intent = getActivity().getIntent();
        studentId = intent.getStringExtra("STUDENT_ID");
        student = studentDBHelper.getStudentByStudentId(studentId);


        txtCommon = getView().findViewById(R.id.txtStudentName);
        txtCommon.setText(student.getStudentName());
        txtCommon = getView().findViewById(R.id.txtStudentId);
        txtCommon.setText(student.getStudentId().toUpperCase());
        txtCommon = getView().findViewById(R.id.txtStudentMail);
        txtCommon.setText(student.getEmail());
        btnLogout = getView().findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Sign out");
                builder.setMessage("Do you want to Sign out?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getContext(), LoginActivity.class));
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
}