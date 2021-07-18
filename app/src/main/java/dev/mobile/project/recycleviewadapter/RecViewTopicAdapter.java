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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.project.DBHelper.DatabaseHelper;
import dev.mobile.project.DBHelper.PlanTopicDBHelper;
import dev.mobile.project.DBHelper.TaskDBHelper;
import dev.mobile.project.R;
import dev.mobile.project.dto.PlanTopic;
import dev.mobile.project.dto.Subject;
import dev.mobile.project.dto.Task;
import dev.mobile.project.dto.Topic;

public class RecViewTopicAdapter extends RecyclerView.Adapter<RecViewTopicAdapter.ViewHolder> {

    private PlanTopic planTopic;
    private int planSubjectId;
    private Subject subject;
    private int planTopicId;
    private int recViewLayout;

    private Context context;
    private Activity activity;
    private Intent intent;
    private RecyclerView recViewCommon;
    private DatabaseHelper db;

    private List<Topic> topicList;
    private List<Task> taskList;

    private TaskDBHelper taskDBHelper;
    private PlanTopicDBHelper planTopicDBHelper;

    public RecViewTopicAdapter(Context context, Activity activity, DatabaseHelper db) {
        this.context = context;
        this.activity = activity;
        this.db = db;
        taskDBHelper = new TaskDBHelper(db);
        planTopicDBHelper = new PlanTopicDBHelper(db);
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public void setPlanSubjectId(int planSubjectId) {
        this.planSubjectId = planSubjectId;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setRecViewLayout(int recViewLayout) {
        this.recViewLayout = recViewLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(recViewLayout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecViewTopicAdapter.ViewHolder holder, int position) {
        if (recViewLayout == R.layout.recycle_view_topic_card) {
            holder.txtTopicName.setText(topicList.get(position).getTopicName());
            holder.txtDescription.setText(topicList.get(position).getTopicDescription());
            holder.imgTopic.setImageResource(R.drawable.ic_key_down);
            holder.layoutTopicTask.setVisibility(View.GONE);

            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.layoutTopicTask.getVisibility() == View.GONE) {
                        holder.layoutTopicTask.setVisibility(View.VISIBLE);
                        holder.imgTopic.setImageResource(R.drawable.ic_key_up);
                    } else {
                        holder.layoutTopicTask.setVisibility(View.GONE);
                        holder.imgTopic.setImageResource(R.drawable.ic_key_down);
                    }
                }
            });
            planTopic = planTopicDBHelper.getPlanTopicByTopicAndPlanSubject(topicList.get(position).getTopicId(), planSubjectId);
            if (planTopic != null) {
                planTopicId = planTopic.getPlanTopicId();
                taskList = taskDBHelper.getAllTaskByPlanTopic(planTopic.getPlanTopicId());
            } else {
                planTopicId = 0;
                taskList = new ArrayList<>();
            }
            holder.txtTaskCount.setText(taskList.size() + "");
            RecViewTaskAdapter adapter = new RecViewTaskAdapter(context, activity, db);
            adapter.setSubject(subject);
            adapter.setTaskList(taskList);
            holder.recViewTopicTask.setAdapter(adapter);
            holder.recViewTopicTask.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
        } else {
            holder.txtTopicName.setText(topicList.get(position).getTopicName());
            holder.txtDescription.setText(topicList.get(position).getTopicDescription());
        }

    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTopicName, txtDescription, txtTaskCount;
        private LinearLayout layoutTopicTask, parent;
        private ImageView imgTopic;
        private RecyclerView recViewTopicTask;

        public ViewHolder(View view) {
            super(view);
            txtTopicName = view.findViewById(R.id.txtTopicName);
            txtDescription = view.findViewById(R.id.txtTopicDescription);
            txtTaskCount = view.findViewById(R.id.txtTaskCount);
            imgTopic = view.findViewById(R.id.imgExpandTopic);
            layoutTopicTask = view.findViewById(R.id.layoutTopicTask);
            parent = view.findViewById(R.id.topicCard);
            recViewTopicTask = view.findViewById(R.id.recViewTopicTask);
        }
    }
}
