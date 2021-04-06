package com.jobs.aboasi;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.jobs.aboasi.helpers.Helpers;
import com.jobs.aboasi.models.User;
import com.jobs.aboasi.repository.Repository;

import java.util.Objects;

public class RegistrationScreen extends AppCompatActivity {
    User userObject = new User();
    private String password;

    private String confirmed_password;
    LinearLayout linearLayout;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        linearLayout = findViewById(R.id.linearLayout);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        final TextInputLayout first_name_layout = findViewById(R.id.firstnamelayout);
        TextInputLayout last_name_layout = findViewById(R.id.lastnamelayout);
        TextInputLayout email_layout = findViewById(R.id.emaillayout);
        TextInputLayout password_layout = findViewById(R.id.passwordlayout);
        TextInputLayout phone_layout = findViewById(R.id.phonelayout);
        TextInputLayout confirm_password_layout = findViewById(R.id.cpasswordlayout);

        Button register = findViewById(R.id.register_button);

        Objects.requireNonNull(first_name_layout.getEditText()).addTextChangedListener(getTextWatcher("first_name"));
        Objects.requireNonNull(last_name_layout.getEditText()).addTextChangedListener(getTextWatcher("last_name"));
        Objects.requireNonNull(email_layout.getEditText()).addTextChangedListener(getTextWatcher("email"));
        Objects.requireNonNull(phone_layout.getEditText()).addTextChangedListener(getTextWatcher("phone"));
        Objects.requireNonNull(password_layout.getEditText()).addTextChangedListener(getTextWatcher("password"));
        Objects.requireNonNull(confirm_password_layout.getEditText()).addTextChangedListener(getTextWatcher("confirmed_password"));

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = isValid();

                if(message.equals("passed validation")){
                    Repository.getRepositoryInstance().createUserWithEmailAndPassword(userObject,password,linearLayout,RegistrationScreen.this);

                }else{
                    Helpers.showSnackBar(linearLayout,message,R.color.colorError);

                }
            }
        });


    }

    public TextWatcher getTextWatcher(final String textype){

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                switch (textype) {
                    case "first_name":

                        userObject.setFirstName(s.toString());

                        break;

                    case "last_name":

                        userObject.setLastName(s.toString());

                        break;

                    case "email":

                        userObject.setEmail(s.toString());

                        break;

                    case "password":

                        password = s.toString();

                        break;

                    case "phone":

                        userObject.setPhoneNumber(s.toString());
                        break;

                    case "confirmed_password":

                        confirmed_password = s.toString();

                }

            }
        };

    };

    //checks whether account details match and have all been  filled in

    private String isValid(){


        if(userObject != null){


            if(!TextUtils.isEmpty(userObject.getEmail()) && !TextUtils.isEmpty(userObject.getFirstName()) && !TextUtils.isEmpty(userObject.getPhoneNumber())&& !TextUtils.isEmpty(userObject.getLastName()) && password != null
                    && confirmed_password != null
                    && !password.isEmpty() && !confirmed_password.isEmpty()){


                if(!confirmed_password.contentEquals(password)){


                    return "Your passwords don't match";




                }else if(!Patterns.EMAIL_ADDRESS.matcher(userObject.getEmail()).matches()){

                    return "Please enter the correct email";




                }else{

                    return "passed validation";



                }






            }else{


                return "Please fill in all the required fields";
            }



        }else{


            return "Please fill in all the required fields";


        }






    }



}
