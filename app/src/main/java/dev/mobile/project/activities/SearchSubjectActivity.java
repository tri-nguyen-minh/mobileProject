package dev.mobile.project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import dev.mobile.project.DBHelper.DatabaseHelper;
import dev.mobile.project.DBHelper.SubjectDBHelper;
import dev.mobile.project.R;
import dev.mobile.project.dto.Subject;
import dev.mobile.project.recycleviewadapter.RecViewSubjectAdapter;

public class SearchSubjectActivity extends AppCompatActivity {

    private EditText editCommon;
    private RecyclerView recViewSubjectSearch;
    private RecViewSubjectAdapter adapter;

    private DatabaseHelper db;
    private SubjectDBHelper subjectDBHelper;
    private List<Subject> subjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_subject);

        db = new DatabaseHelper(getApplicationContext());
        subjectDBHelper = new SubjectDBHelper(db);

        recViewSubjectSearch = findViewById(R.id.recViewSubjectSearch);
        setupSubjectList("", "");
        ImageView imgSearchSlider = findViewById(R.id.imgSearchSlider);
        imgSearchSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout searchDrawerHandle = findViewById(R.id.searchDrawerHandle);
                searchDrawerHandle.performClick();
            }
        });
        TextView txtSearch = findViewById(R.id.txtSearch);
        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout searchDrawerHandle = findViewById(R.id.searchDrawerHandle);
                searchDrawerHandle.performClick();
                editCommon = findViewById(R.id.editSearchSubjectId);
                String subjectId = editCommon.getText().toString();
                editCommon = findViewById(R.id.editSearchSubjectName);
                String subjectName = editCommon.getText().toString();
                setupSubjectList(subjectId, subjectName);
            }
        });
        this.getSupportActionBar().hide();
    }

    private void setupSubjectList(String subjectId, String subjectName) {
        subjectList = subjectDBHelper.searchAllSubjectsByIdAndName(subjectId, subjectName);
        adapter = new RecViewSubjectAdapter(getApplicationContext(), SearchSubjectActivity.this);
        adapter.setSubjectList(subjectList);
        recViewSubjectSearch.setAdapter(adapter);
        recViewSubjectSearch.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false));
    }

    public void onBack(View view) {
        finish();
    }
}