package com.jobs.aboasi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.jobs.aboasi.models.Job;
import com.jobs.aboasi.views.hiree.JobApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class FullJobReview extends AppCompatActivity {
    Job job;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_job_description);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

         job = (Job)getIntent().getSerializableExtra("job");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
       TextView description = findViewById(R.id.description);
        TextView headline = findViewById(R.id.title);
        TextView category = findViewById(R.id.category);
        TextView budget = findViewById(R.id.budget);
        RelativeTimeTextView  posted = findViewById(R.id.created);
        TextView experience = findViewById(R.id.experience);
        TextView location = findViewById(R.id.village);
        TextView deadline=findViewById(R.id.deadline);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(job.getDeadline());
        deadline.setText(simpleDateFormat.format(calendar.getTime()));

        if(job != null){
            String salary =" Not specified";
            if(job.budget != null){
                salary = "Cedi" + " " + job.budget;
            }
            String village = "Not specified";
            if(job.getVillage() != null){
                village = job.getVillage();
            }
            location.setText(village);

            description.setText(job.getDescription());
            headline.setText(job.getHeadline());
            category.setText(job.getCategory());
            experience.setText(job.getExperience());
            posted.setReferenceTime(job.getPosted());
            budget.setText(salary);
        }



    }



    public void onTapJob(View view){


    }

    public void onTapApply(View view){
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(FullJobReview.this, JobApplication.class);
            intent.putExtra("job_id",job.getId());
            startActivity(intent);
        }else{
         Intent intent =   new Intent(FullJobReview.this, LoginScreen.class);
         intent.putExtra("is_hirer",false);
            startActivity(intent);
        }


    }
}
