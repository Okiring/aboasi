package com.jobs.aboasi.views.hirer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jobs.aboasi.R;
import com.jobs.aboasi.SuccessPage;
import com.jobs.aboasi.helpers.Helpers;
import com.jobs.aboasi.models.Job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AddJobPosting extends AppCompatActivity {

    ArrayList<String> experience = new ArrayList<>();
    ArrayList<String> categories = new ArrayList<>();
    EditText deadline;
    SimpleDateFormat simpleDateFormat;
    EditText title;
    EditText description;
    EditText budget;
    String village;
    List<String> villages = new ArrayList<>();
    LinearLayout linearLayout;

    Job job = new Job();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_job_posting);
        Toolbar toolbar = findViewById(R.id.toolbar);
         simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
         title = findViewById(R.id.title);
         description = findViewById(R.id.description);
         budget = findViewById(R.id.budget);
        villages.add("Accra");
        villages.add("Kasoa");
        villages.add("Kumasi");
        villages.add("Cape coast");
        villages.add("Agogo");
        villages.add("Tema");
         linearLayout = findViewById(R.id.linearLayout);
         job.experience = "Entry Level";
         job.category = "Carpentry";

        toolbar.setTitle("");
        experience.add("Entry");
        experience.add("Intermediate");
        experience.add("Expert");

        categories.add("Carpentry");
        categories.add("House Help");
        categories.add("Plumber");
        categories.add("Electrician");
        categories.add("Gardener");
        categories.add("Domestic Pets Control");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        EditText description = findViewById(R.id.description);
        EditText title = findViewById(R.id.title);
         deadline = findViewById(R.id.deadline);
        description.setRawInputType(InputType.TYPE_CLASS_TEXT);
        title.setRawInputType(InputType.TYPE_CLASS_TEXT);
        Spinner spinner = findViewById(R.id.spinner);
        Spinner spinner2 = findViewById(R.id.spinner_village);
        Spinner spinner1 = findViewById(R.id.category);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,experience);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,categories);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,villages);
        spinner.setAdapter(arrayAdapter);
        spinner1.setAdapter(arrayAdapter1);
        spinner2.setAdapter(arrayAdapter2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                job.setExperience(experience.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                village = villages.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                job.setCategory(categories.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onTapSubmit(View view){

     if(!title.getText().toString().isEmpty() && !description.getText().toString().isEmpty() && job.deadline != null && job.experience != null ){
         job.setDescription(description.getText().toString());
         job.setHeadline(title.getText().toString());
         job.setOpen(true);
         job.setVillage(village);
         job.setPosted(Calendar.getInstance().getTimeInMillis());
         if(!budget.getText().toString().isEmpty()){
             job.setBudget(Integer.parseInt(budget.getText().toString()));
         }
      final AlertDialog alertDialog = Helpers.showDialogue(AddJobPosting.this,"Posting job");
         FirebaseFirestore.getInstance().collection("jobs").add(job).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {

             @Override
             public void onComplete(@NonNull Task<DocumentReference> task) {
                 Helpers.remove(alertDialog);
                 if(task.isSuccessful()){
                     Intent intent = new Intent(AddJobPosting.this, SuccessPage.class);
                     intent.putExtra("message","Job was successfully posted");
                     intent.putExtra("added",true);
                     startActivityForResult(intent,Helpers.HIRER_REQ_CODE);
                     finish();
                 }else{

                     Helpers.showSnackBar(linearLayout, "Failed saving the job post, please try again",R.color.colorAccent);

                 }

             }
         });
     }else{

         Helpers.showSnackBar(linearLayout,"Please fill in all required fields",R.color.colorAccent);
     }




    }
    public void onTapCalender(View view){
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar  calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR,year);
                calendar1.set(Calendar.MONTH,month);
                calendar1.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                job.setDeadline(calendar1.getTimeInMillis());
                deadline.setText(simpleDateFormat.format(calendar1.getTime()));

            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


    }
}
