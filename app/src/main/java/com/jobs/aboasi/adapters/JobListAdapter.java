package com.jobs.aboasi.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.jobs.aboasi.FullJobReview;
import com.jobs.aboasi.R;
import com.jobs.aboasi.helpers.Helpers;
import com.jobs.aboasi.models.Job;
import com.jobs.aboasi.views.hirer.HirerJobDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.JobViewHolder> {
 private Context mContext;
 private ArrayList<Job> jobArrayList;
private Boolean isHirer;
private Boolean showClose;
    public JobListAdapter(Context context, ArrayList<Job> jobArrayList,Boolean isHirer,Boolean showClose){
        this.mContext = context;
        this.jobArrayList = jobArrayList;
        this.isHirer = isHirer;
        this.showClose = showClose;
    }

    public void setJobArrayList(ArrayList<Job> jobArrayList) {
        this.jobArrayList = jobArrayList;
    }

    public ArrayList<Job> getJobArrayList() {
        return jobArrayList;
    }


    @NonNull
    @Override
    public JobListAdapter.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.job_item,parent,false);

        return new JobViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull JobListAdapter.JobViewHolder holder, final int position) {
        holder.getCategory().setText(jobArrayList.get(position).getCategory());
        holder.getHeadline().setText(jobArrayList.get(position).getHeadline());
        holder.getDescription().setText(jobArrayList.get(position).getDescription());
        holder.getPosted().setReferenceTime(jobArrayList.get(position).getPosted());
        String salary =" Not specified";
        String village = "Not specified";
        if(jobArrayList.get(position).getVillage() != null){
            village = jobArrayList.get(position).getVillage();
        }
        if(jobArrayList.get(position).budget != null){
            salary = "Cedi" + " " + jobArrayList.get(position).budget;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        Long end = jobArrayList.get(position).getDeadline();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(end);
        holder.getDeadline().setText(simpleDateFormat.format(calendar.getTime()));
        holder.getBudget().setText(salary);
        holder.getVillage().setText(village);
        holder.getJobView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isHirer){
                   Intent intent = new Intent(v.getContext(), HirerJobDetails.class);
                   intent.putExtra("show_close",showClose);
                   intent.putExtra("job",jobArrayList.get(position));
                   intent.putExtra("index",position);
                   ((Activity)mContext).startActivityForResult(intent, Helpers.HIRER_REQ_CODE);

               }else{
                   Intent intent = new Intent(v.getContext(), FullJobReview.class);
                   intent.putExtra("job",jobArrayList.get(position));

                   mContext.startActivity(intent);
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobArrayList.size();
    }

    static class JobViewHolder extends RecyclerView.ViewHolder{
          TextView description;

        public TextView getDescription() {
            return description;
        }

        public void setDescription(TextView description) {
            this.description = description;
        }

        public TextView getHeadline() {
            return headline;
        }

        public void setHeadline(TextView headline) {
            this.headline = headline;
        }

        public TextView getCategory() {
            return category;
        }

        public void setCategory(TextView category) {
            this.category = category;
        }

        public TextView getBudget() {
            return budget;
        }

        public void setBudget(TextView budget) {
            this.budget = budget;
        }

        public RelativeTimeTextView getPosted() {
            return posted;
        }

        public void setPosted(RelativeTimeTextView posted) {
            this.posted = posted;
        }

        public TextView getExperience() {
            return experience;
        }

        public void setExperience(TextView experience) {
            this.experience = experience;
        }

        public LinearLayout getJobView() {
            return jobView;
        }

        public void setJobView(LinearLayout jobView) {
            this.jobView = jobView;
        }
        public TextView getVillage() {
            return village;
        }

        public void setVillage(TextView village) {
            this.village = village;
        }


        TextView headline;
         TextView category;
         TextView budget;
        RelativeTimeTextView posted;
         TextView experience;
         LinearLayout jobView;
        TextView village;
         TextView deadline;

        public TextView getDeadline() {
            return deadline;
        }

        public void setDeadline(TextView deadline) {
            this.deadline = deadline;
        }

        private JobViewHolder(@NonNull View itemView) {
            super(itemView);
            description = (itemView).findViewById(R.id.description);
            headline = itemView.findViewById(R.id.title);
            category = itemView.findViewById(R.id.category);
            budget = itemView.findViewById(R.id.budget);
            posted = itemView.findViewById(R.id.created);
            experience = itemView.findViewById(R.id.experience);
            jobView = itemView.findViewById(R.id.job_item);
            deadline = itemView.findViewById(R.id.deadline);
            village = itemView.findViewById(R.id.village);
        }
    }
}
