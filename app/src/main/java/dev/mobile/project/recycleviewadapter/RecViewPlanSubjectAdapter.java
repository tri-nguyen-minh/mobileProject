package dev.mobile.project.recycleviewadapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.mobile.project.DBHelper.DatabaseHelper;
import dev.mobile.project.DBHelper.SubjectDBHelper;
import dev.mobile.project.R;
import dev.mobile.project.activities.SubjectActivity;
import dev.mobile.project.dto.PlanSubject;

public class RecViewPlanSubjectAdapter extends RecyclerView.Adapter<RecViewPlanSubjectAdapter.ViewHolder>{

    private Context context;
    private Activity activity;
    private SubjectDBHelper subjectDBHelper;
    private Intent intent;
    private List<PlanSubject> subjectList;

    public RecViewPlanSubjectAdapter(Context context, Activity activity, DatabaseHelper db) {
        this.context = context;
        this.activity = activity;
        subjectDBHelper = new SubjectDBHelper(db);
    }

    public void setSubjectList(List<PlanSubject> subjectList) {
        this.subjectList = subjectList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_plan_subject_card, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecViewPlanSubjectAdapter.ViewHolder holder, int position) {
        String subjectId = subjectList.get(position).getSubjectId();
        holder.txtSubjectId.setText(subjectId);
        holder.txtSubjectName.setText(subjectDBHelper.getSubjectById(subjectId).getSubjectName());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int planSubjectId = subjectList.get(position).getPlanSubjectId();
                intent = new Intent(context, SubjectActivity.class);
                intent.putExtra("PLAN_SUBJECT_ID", planSubjectId);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtSubjectId, txtSubjectName;
        private LinearLayout parent;

        public ViewHolder(View view) {
            super(view);
            txtSubjectId = view.findViewById(R.id.txtSubjectId);
            txtSubjectName = view.findViewById(R.id.txtSubjectName);
            parent = view.findViewById(R.id.subjectCard);
        }
    }
}
