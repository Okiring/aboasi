package com.jobs.aboasi.views.hirer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jobs.aboasi.FullJobReview;
import com.jobs.aboasi.ProfileScreen;
import com.jobs.aboasi.R;
import com.jobs.aboasi.adapters.JobListAdapter;
import com.jobs.aboasi.helpers.Helpers;
import com.jobs.aboasi.models.Job;
import com.jobs.aboasi.views.hiree.HireeHomePage;

import java.util.ArrayList;
import java.util.List;

public class HirerHomePage extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Job> jobArrayList;
    private JobListAdapter jobListAdapter;
    ProgressBar progressBar;
    TextView noItem;
    Spinner filter;
    String category;
    ArrayList<String> categories = new ArrayList<>();
   public HirerHomePage(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hirer_home,container,false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Helpers.HIRER_REQ_CODE){

            if(resultCode == Helpers.HIRER_REQ_DELETE){
                if(data!=null){
                    if(!data.getBooleanExtra("is_closed",false)){
                        jobArrayList.remove(data.getIntExtra("index",0));
                        jobListAdapter.setJobArrayList(jobArrayList);
                        jobListAdapter.notifyDataSetChanged();

                    }
                }
            }else{
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                noItem.setVisibility(View.GONE);
                FirebaseFirestore.getInstance().collection("jobs"). whereEqualTo("isOpen",true).orderBy("posted", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                                jobArrayList.clear();

                                jobArrayList.addAll(jobs);
                                jobListAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                if(jobs.size() == 0){
                                    String message = "No open jobs";
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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler);
        progressBar = view.findViewById(R.id.progress_circular);
        jobArrayList = new ArrayList<>();
        filter = view.findViewById(R.id.filter);
        noItem = view.findViewById(R.id.no_item);
        jobListAdapter = new JobListAdapter(getContext(),jobArrayList,true,true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(jobListAdapter);
        recyclerView.setVisibility(View.GONE);
        noItem.setVisibility(View.GONE);
        categories.add("All");
        categories.add("Carpentry");
        categories.add("House Help");
        categories.add("Plumber");
        categories.add("Electrician");
        categories.add("Gardener");
        categories.add("Domestic Pets Control");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,categories);
        filter.setAdapter(arrayAdapter);

        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = categories.get(position);
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                noItem.setVisibility(View.GONE);
                if(category.equals("All")){
                    FirebaseFirestore.getInstance().collection("jobs"). whereEqualTo("isOpen",true).orderBy("posted", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                                    jobArrayList.clear();

                                    jobArrayList.addAll(jobs);
                                    jobListAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    if(jobs.size() == 0){
                                        String message = "No open jobs";
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


                }else{

                    FirebaseFirestore.getInstance().collection("jobs").whereEqualTo("category",category). whereEqualTo("isOpen",true).orderBy("posted", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                                jobArrayList.clear();

                                jobArrayList.addAll(jobs);
                                jobListAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                if(jobs.size() == 0){
                                    String message = "No open jobs";
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
}
