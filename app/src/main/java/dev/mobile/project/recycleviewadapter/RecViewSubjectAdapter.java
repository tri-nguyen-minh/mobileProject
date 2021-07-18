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

import dev.mobile.project.R;
import dev.mobile.project.activities.NewSubjectActivity;
import dev.mobile.project.dto.Subject;

public class RecViewSubjectAdapter extends RecyclerView.Adapter<RecViewSubjectAdapter.ViewHolder>{

    private Context context;
    private Activity activity;
    private Intent intent;
    private List<Subject> subjectList;

    public RecViewSubjectAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_subject_card, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecViewSubjectAdapter.ViewHolder holder, int position) {
        holder.txtSubjectId.setText(subjectList.get(position).getSubjectId());
        holder.txtSubjectName.setText(subjectList.get(position).getSubjectName());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String studentId = activity.getIntent().getStringExtra("STUDENT_ID");
                Intent intent = new Intent(context, NewSubjectActivity.class);
                intent.putExtra("STUDENT_ID", studentId);
                intent.putExtra("SUBJECT_ID", subjectList.get(position).getSubjectId());
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
