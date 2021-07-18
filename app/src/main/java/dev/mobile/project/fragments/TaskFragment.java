package dev.mobile.project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.project.DBHelper.DatabaseHelper;
import dev.mobile.project.DBHelper.TaskDBHelper;
import dev.mobile.project.R;
import dev.mobile.project.dto.Task;
import dev.mobile.project.recycleviewadapter.RecViewTaskAdapter;

public class TaskFragment extends Fragment {

    private EditText editCommon;
    private List<Task> taskList;
    private ArrayList<String> spinnerData;
    private ArrayAdapter<String> dataAdapter;
    private RecyclerView recViewTaskSearch;
    private Spinner spinnerTaskPriority, spinnerTaskStatus;

    private RecViewTaskAdapter adapter;

    private DatabaseHelper db;
    private TaskDBHelper taskDBHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = new DatabaseHelper(getContext());
        taskDBHelper = new TaskDBHelper(db);
        return inflater.inflate(R.layout.fragment_main_2_task, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recViewTaskSearch = getView().findViewById(R.id.recViewTaskSearch);
        spinnerTaskPriority = getView().findViewById(R.id.spinnerTaskPriority);
        spinnerTaskStatus = getView().findViewById(R.id.spinnerTaskStatus);

        spinnerData = new ArrayList<>();
        spinnerData.add("Not Urgent");
        spinnerData.add("Urgent");
        dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerData);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaskPriority.setAdapter(dataAdapter);

        spinnerData = new ArrayList<>();
        spinnerData.add("Ongoing");
        spinnerData.add("Finished");
        dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerData);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaskStatus.setAdapter(dataAdapter);

        ImageView imgSearchSlider = getView().findViewById(R.id.imgSearchSlider);
        imgSearchSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout searchDrawerHandle = getView().findViewById(R.id.searchDrawerHandle);
                searchDrawerHandle.performClick();
            }
        });
        TextView txtActionSearch = getView().findViewById(R.id.txtSearchTask);
        txtActionSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout searchDrawerHandle = getView().findViewById(R.id.searchDrawerHandle);
                searchDrawerHandle.performClick();
                setupTaskList();
            }
        });
        setupTaskList();
    }

    private void setupTaskList() {
        editCommon = getView().findViewById(R.id.editDescription);
        String description = editCommon.getText().toString();
        int priority = spinnerTaskPriority.getSelectedItemPosition();
        boolean status = spinnerTaskStatus.getSelectedItemPosition() == 1;
        taskList = taskDBHelper.searchTaskByCondition(description, priority, status);
        adapter = new RecViewTaskAdapter(getContext(), getActivity(), db);
        adapter.setTaskList(taskList);
        recViewTaskSearch.setAdapter(adapter);
        recViewTaskSearch.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
    }
}