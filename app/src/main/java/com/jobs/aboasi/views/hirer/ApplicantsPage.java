package com.jobs.aboasi.views.hirer;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jobs.aboasi.R;
import com.jobs.aboasi.adapters.JobApplicantsAdapter;
import com.jobs.aboasi.adapters.JobListAdapter;
import com.jobs.aboasi.models.Applicant;
import com.jobs.aboasi.models.Job;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApplicantsPage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Applicant> applicantArrayList;
    private JobApplicantsAdapter jobApplicantsAdapter;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_applicants);
        recyclerView = findViewById(R.id.recycler);
        applicantArrayList = (ArrayList<Applicant>) getIntent().getSerializableExtra("applicants");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        jobApplicantsAdapter = new JobApplicantsAdapter(this,applicantArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(jobApplicantsAdapter);
    }
}
