package com.jobs.aboasi.views.hirer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jobs.aboasi.R;
import com.jobs.aboasi.adapters.JobListAdapter;
import com.jobs.aboasi.helpers.Helpers;
import com.jobs.aboasi.models.Job;

import java.util.ArrayList;
import java.util.List;


public class HirerClosedJobs extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Job> jobArrayList;
    private JobListAdapter jobListAdapter;
    ProgressBar progressBar;
    TextView noItem;

   public HirerClosedJobs(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hirer_closed_jobs,container,false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Helpers.HIRER_REQ_CODE){

       if(resultCode == Helpers.HIRER_REQ_DELETE){
          if(data!=null){
             if(data.getBooleanExtra("is_closed",false)){
                 jobArrayList.remove(data.getIntExtra("index",0));
                 jobListAdapter.setJobArrayList(jobArrayList);
                 jobListAdapter.notifyDataSetChanged();

             }
          }
       }

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler);
        progressBar = view.findViewById(R.id.progress_circular);
        noItem = view.findViewById(R.id.no_item);
        jobArrayList = new ArrayList<>();
        jobListAdapter = new JobListAdapter(getContext(),jobArrayList,true,false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(jobListAdapter);
        recyclerView.setVisibility(View.GONE);
        FirebaseFirestore.getInstance().collection("jobs").whereEqualTo("isOpen",false).orderBy("posted", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    if (task.getResult() != null) {
                        List<Job> jobs = new ArrayList<>();
                        for(QueryDocumentSnapshot documentSnapshot1 : task.getResult()){
                            Job job = documentSnapshot1.toObject(Job.class);
                            job.setId(documentSnapshot1.getId());
                            jobs.add(job);
                        }

                        jobArrayList.addAll(jobs);
                        jobListAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);


                        if(jobs.size() == 0){
                            String message = "No closed jobs";
                            noItem.setText(message);
                            noItem.setVisibility(View.VISIBLE);

                        }




                    } else {
                        String message = "Failed loading jobs";
                        noItem.setText(message);
                        noItem.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }

                } else {
                    String message = "Failed loading jobs";
                    noItem.setText(message);
                    noItem.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                }

            }
        });

    }
}
