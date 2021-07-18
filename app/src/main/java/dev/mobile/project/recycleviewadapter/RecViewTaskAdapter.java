package dev.mobile.project.recycleviewadapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.mobile.project.DBHelper.DatabaseHelper;
import dev.mobile.project.R;
import dev.mobile.project.activities.TaskActivity;
import dev.mobile.project.dto.Subject;
import dev.mobile.project.dto.Task;

public class RecViewTaskAdapter extends RecyclerView.Adapter<RecViewTaskAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private Intent intent;
    private List<Task> taskList;
    private Subject subject;
    private Task task;

    public RecViewTaskAdapter(Context context, Activity activity, DatabaseHelper db) {
        this.context = context;
        this.activity = activity;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_task_card, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecViewTaskAdapter.ViewHolder holder, int position) {
        task = taskList.get(position);
        holder.txtTaskDescription.setText(task.getDescription());
        holder.txtSubjectName.setText(subject.getSubjectName());
        holder.txtDueDate.setText(task.getDueDate());
        holder.txtEffortTime.setText(task.getEffortTime() + "");
        holder.txtEstimateTime.setText(task.getEstimateTime() + "");
        if (task.getPriority() == 0) {
            holder.imgTaskPriority.setImageResource(R.drawable.ic_not_urgent);
        } else {
            holder.imgTaskPriority.setImageResource(R.drawable.ic_urgent);
        }
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, TaskActivity.class);
                intent.putExtra("TASK_ID", task.getTaskId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTaskDescription, txtSubjectName, txtDueDate, txtEffortTime, txtEstimateTime;
        private LinearLayout parent;
        private ImageView imgTaskPriority;

        public ViewHolder(View view) {
            super(view);
            txtTaskDescription = view.findViewById(R.id.txtTaskDescription);
            txtSubjectName = view.findViewById(R.id.txtSubjectName);
            txtDueDate = view.findViewById(R.id.txtDueDate);
            txtEffortTime = view.findViewById(R.id.txtEffortTime);
            txtEstimateTime = view.findViewById(R.id.txtEstimateTime);
            imgTaskPriority = view.findViewById(R.id.imgTaskPriority);
            parent = view.findViewById(R.id.taskCard);
        }
    }
}
