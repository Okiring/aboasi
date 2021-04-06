package com.jobs.aboasi.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jobs.aboasi.R;
import com.jobs.aboasi.models.Applicant;

import java.net.URI;
import java.util.ArrayList;

public class JobApplicantsAdapter extends RecyclerView.Adapter<JobApplicantsAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<Applicant> applicants;

    public JobApplicantsAdapter(Context context, ArrayList<Applicant> applicants){
        this.mcontext = context;
        this.applicants = applicants;

    }

    @NonNull
    @Override
    public JobApplicantsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.applicant_item,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull JobApplicantsAdapter.ViewHolder holder, final int position) {



        String names = applicants.get(position).getFirstName() + " " + applicants.get(position).getLastName();
        holder.getFullName().setText(names);
        holder.setExperience(holder.getExperience());
        String salary =" Not specified";

        if(applicants.get(position).getBudget() != null){
            salary = "Cedi" + " " + applicants.get(position).getBudget();
        }

        holder.getBudget().setText(salary);
        holder.getEmail().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setData(Uri.parse("mailito:"));
                intent.setAction(Intent.ACTION_SENDTO);
                intent.putExtra(Intent.EXTRA_EMAIL,applicants.get(position).getEmail());
                if(intent.resolveActivity(mcontext.getPackageManager())!= null){
                    mcontext.startActivity(intent);
                }


            }
        });

        holder.getCall().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + applicants.get(position).getPhoneNumber()));
                if(intent.resolveActivity(mcontext.getPackageManager())!= null){
                    mcontext.startActivity(intent);
                }


            }
        });




    }

public class ViewHolder extends RecyclerView.ViewHolder{
    public TextView getFullName() {
        return fullName;
    }

    public void setFullName(TextView fullName) {
        this.fullName = fullName;
    }

    public ImageView getEmail() {
        return email;
    }

    public void setEmail(ImageView email) {
        this.email = email;
    }

    public ImageView getCall() {
        return call;
    }

    public void setCall(ImageView call) {
        this.call = call;
    }


    public TextView getBudget() {
        return budget;
    }

    public void setBudget(TextView budget) {
        this.budget = budget;
    }

    public TextView getExperience() {
        return experience;
    }

    public void setExperience(TextView experience) {
        this.experience = experience;
    }


    TextView fullName;
        ImageView email;
        ImageView call;

        TextView budget;
        TextView experience;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.full_names);
            email = itemView.findViewById(R.id.email);
            call = itemView.findViewById(R.id.call);
            experience = itemView.findViewById(R.id.experience);
            budget = itemView.findViewById(R.id.budget);

        }
    }

    @Override
    public int getItemCount() {
        return applicants.size();
    }
}
