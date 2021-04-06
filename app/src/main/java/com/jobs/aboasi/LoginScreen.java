package com.jobs.aboasi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.jobs.aboasi.helpers.Helpers;
import com.jobs.aboasi.repository.Repository;
import com.jobs.aboasi.views.hiree.HireeHomePage;
import com.jobs.aboasi.views.hiree.HireeIndexPage;
import com.jobs.aboasi.views.hirer.HirerHomePage;
import com.jobs.aboasi.views.hirer.HirerIndexPage;

import java.util.Objects;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout email_layout;
    private TextInputLayout password_layout;
    private LinearLayout linearLayout;
     Boolean isHirer;
    AlertDialog alertDialog;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        TextView here = findViewById(R.id.here);

        Button loginButton = findViewById(R.id.loginbutton);
        sharedPreferences = getApplication().getSharedPreferences("prefs",MODE_PRIVATE);

        isHirer = getIntent().getBooleanExtra("is_hirer",false);

        //layout

        email_layout = findViewById(R.id.emaillayout);
        password_layout = findViewById(R.id.passwordlayout);

        loginButton.setOnClickListener(this);
        here.setOnClickListener(this);
        linearLayout = findViewById(R.id.linearLayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.here:

                startActivity(new Intent(this,RegistrationScreen.class));

                break;

            case R.id.loginbutton:


                if(Objects.requireNonNull(email_layout.getEditText()).getText().toString().isEmpty()
                        || Objects.requireNonNull(password_layout.getEditText()).getText().toString().isEmpty()){



                    Helpers.showSnackBar(linearLayout,"Please fill in all the required fields",R.color.colorError);



                }else{

                    Repository.getRepositoryInstance().LoginInUser((Objects.requireNonNull(email_layout.getEditText()).getText().toString()),
                            Objects.requireNonNull(password_layout.getEditText()).getText().toString(),this,isHirer,linearLayout);



                }



                break;


        }
    }
}
