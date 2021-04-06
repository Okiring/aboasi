package com.jobs.aboasi.views.hiree;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.jobs.aboasi.R;
import com.jobs.aboasi.SuccessPage;
import com.jobs.aboasi.helpers.Helpers;
import com.jobs.aboasi.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class JobApplication extends AppCompatActivity {

    List<Integer> yrs = new ArrayList<>();
    Integer selected = 1;
    EditText budget;
    EditText phone;
    String jobId;
    User user;
    LinearLayout linearLayout;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_application);
        for(int i = 0; i< 20;i++){
            yrs.add(i+1);
        }

        linearLayout = findViewById(R.id.linearLayout);
        jobId = getIntent().getStringExtra("job_id");
        SharedPreferences sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
        String userObject = sharedPreferences.getString("user",null);
        if(userObject != null){
            Gson gson = new Gson();
            user = gson.fromJson(userObject, User.class);
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Spinner spinner = findViewById(R.id.spinner);

        budget = findViewById(R.id.budget);
        phone = findViewById(R.id.phone);
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,yrs);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = yrs.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void onTapApply(View view){
       if(!phone.getText().toString().isEmpty()){
       final AlertDialog alertDialog = Helpers.showDialogue(JobApplication.this,"Sending application");
           HashMap<String,Object> application = new HashMap<>();
           int salary = 0;
           if(!budget.getText().toString().isEmpty()){
               salary = Integer.parseInt(budget.getText().toString());
           }

           application.put("budget",salary);
           application.put("email",user.getEmail());
           application.put("firstName",user.getFirstName());
           application.put("lastName",user.getLastName());
           application.put("experience",selected);
           application.put("phoneNumber",phone.getText().toString());

           FirebaseFirestore.getInstance().collection("jobs").document(jobId).collection("applicants")
                   .add(application).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
               @Override
               public void onComplete(@NonNull Task<DocumentReference> task) {
                   Helpers.remove(alertDialog);
                   if(task.isSuccessful()){

                       Intent intent = new Intent(JobApplication.this, SuccessPage.class);
                       intent.putExtra("message","Your application has been sent");
                       startActivity(intent);
                       finish();
                   }else{

                       Helpers.showSnackBar(linearLayout,"Failed sending your application, please try again",R.color.colorError);

                   }

               }
           });
       }else{
           Helpers.showSnackBar(linearLayout,"Please fill in all required fields",R.color.colorError);
       }

    }
}
