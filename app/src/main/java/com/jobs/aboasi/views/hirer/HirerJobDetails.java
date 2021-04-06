package com.jobs.aboasi.views.hirer;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jobs.aboasi.R;
import com.jobs.aboasi.helpers.Helpers;
import com.jobs.aboasi.models.Applicant;
import com.jobs.aboasi.models.Job;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class HirerJobDetails extends AppCompatActivity {

    private static final int REQUEST_CALL_PERMISSIONS = 11;
    LinearLayout linearLayout;
    ArrayList<Applicant> applicants;
    Button deleteButton;
    Button close;
    boolean showClose;

    Job job;
    boolean delete;
    int index;
    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return super.onSupportNavigateUp();
    }



    @Override
    public void onBackPressed() {
       if(delete){
           Intent intent = new Intent();
           intent.putExtra("index",index);
           intent.putExtra("is_closed",!showClose);
           setResult(Helpers.HIRER_REQ_DELETE,intent);
       }
        super.onBackPressed();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hirer_job_details);
        showClose = getIntent().getBooleanExtra("show_close",true);
         close = findViewById(R.id.apply);
        if (!showClose){
            close.setVisibility(View.INVISIBLE);
        }
         job = (Job)getIntent().getSerializableExtra("job");
        index = getIntent().getIntExtra("index",0);
        applicants = new ArrayList<>();
        TextView description = findViewById(R.id.description);
        TextView headline = findViewById(R.id.title);
        TextView category = findViewById(R.id.category);
        TextView budget = findViewById(R.id.budget);
        TextView location = findViewById(R.id.village);
        final TextView viewApplicant = findViewById(R.id.view_applicant);
        final TextView applicantCount = findViewById(R.id.applicant_count);

        RelativeTimeTextView posted = findViewById(R.id.created);
        TextView experience = findViewById(R.id.experience);
        linearLayout = findViewById(R.id.applicant_view);
        linearLayout.setVisibility(View.GONE);
         deleteButton = findViewById(R.id.delete);

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
        TextView deadline=findViewById(R.id.deadline);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(job.getDeadline());
        deadline.setText(simpleDateFormat.format(calendar.getTime()));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        FirebaseFirestore.getInstance().collection("jobs").document(job.getId()).collection("applicants")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    if (task.getResult() != null) {

                        for(QueryDocumentSnapshot documentSnapshot1 : task.getResult()){
                            Applicant applicant = documentSnapshot1.toObject(Applicant.class);
                            applicants.add(applicant);
                        }

                        String count = applicants.size() +  " Applicants";
                        applicantCount.setText(count);
                        if(applicants.size() ==0){
                            viewApplicant.setVisibility(View.GONE);
                        }
                        linearLayout.setVisibility(View.VISIBLE);


                    } else {
                        Helpers.showSnackBar(linearLayout,"Failed fetching applicants, try later",R.color.colorError);
                    }

                } else {

                    Helpers.showSnackBar(linearLayout,"Failed fetching applicants, try later",R.color.colorError);
                }
            }
        });
    }

    public void onTapClose(View view){
        HashMap<String,Object> update = new HashMap<>();
        update.put("isOpen",false);

        FirebaseFirestore.getInstance().collection("jobs").document(job.getId()).update(update).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    delete = true;
                    close.setVisibility(View.GONE);
                    Helpers.showSnackBar(linearLayout,"This job has been closed",R.color.colorPrimary);

                }else{
                    Helpers.showSnackBar(linearLayout,"Failed updating this job, try later",R.color.colorError);

                }

            }
        });


    }

    public void onTapJob(View view){


    }

    public void onTapDelete(View view){

        FirebaseFirestore.getInstance().collection("jobs").document(job.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    delete = true;
                    deleteButton.setVisibility(View.GONE);
                    Helpers.showSnackBar(linearLayout,"This job has been deleted",R.color.colorPrimary);

                }else{
                    Helpers.showSnackBar(linearLayout,"Failed deleting this job, try later",R.color.colorError);
                }

            }
        });

    }

    // checking results after requesting permissions

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {

        if (requestCode == REQUEST_CALL_PERMISSIONS) {// check if user chose an option

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(applicants.size() != 0){
                    Intent intent = new Intent(this,ApplicantsPage.class);
                    intent.putExtra("applicants",applicants);

                    startActivity(intent);
                }

            } else {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CALL_PHONE)) {

                    // show user an explanation for accessing phone call
                    AlertDialog alertDialog = new AlertDialog.Builder(HirerJobDetails.this).setTitle("Need call permissions")
                            .setMessage("Aboasi uses this permission to allow you make phone calls to job applicants")
                            .create();

                    alertDialog.show();


                } else {

                    // no explanation needed, request for permissions
                    ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CALL_PHONE },
                            REQUEST_CALL_PERMISSIONS);

                }

            }
        }

    }

    public void onTapApplicantView(View view){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {


                // show user an explanation for accessing phone external storage

                final AlertDialog alertDialog = new AlertDialog.Builder(HirerJobDetails.this).setTitle("Need your permission")
                        .setMessage("Aboasi uses this permission to allow you make phone calls to job applicants")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ActivityCompat.requestPermissions(HirerJobDetails.this, new String[] { Manifest.permission.CALL_PHONE },
                                        REQUEST_CALL_PERMISSIONS);

                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        })
                        .create();

                alertDialog.show();

            } else {

                // no explanation needed, request for permissions

                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CALL_PHONE },
                        REQUEST_CALL_PERMISSIONS);

            }
        } else {

            if(applicants.size() != 0){
                Intent intent = new Intent(this,ApplicantsPage.class);
                intent.putExtra("applicants",applicants);

                startActivity(intent);
            }

        }
    }
}


