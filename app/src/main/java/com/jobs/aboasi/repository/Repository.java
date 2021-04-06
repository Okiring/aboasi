package com.jobs.aboasi.repository;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.jobs.aboasi.R;
import com.jobs.aboasi.SuccessPage;
import com.jobs.aboasi.helpers.Helpers;
import com.jobs.aboasi.models.Job;
import com.jobs.aboasi.models.User;
import com.jobs.aboasi.views.hirer.HirerIndexPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Repository {

    private static Repository repository;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    public static Repository getRepositoryInstance() {

        if (repository == null) {

            repository = new Repository();

        }

        return repository;

    }



    public void createUserWithEmailAndPassword(final User userObject, String password, final View view, final Activity activity){


        firebaseAuth.createUserWithEmailAndPassword(userObject.getEmail(),password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    if(task.getResult()!=null && task.getResult().getUser() !=null){
                        userObject.setUserId(task.getResult().getUser().getUid());
                        userObject.setIsHirer(false);
                        firebaseFirestore.collection("users").document(userObject.getUserId()).set(userObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    Intent intent = new Intent(activity, SuccessPage.class);
                                    intent.putExtra("message","Account was successfully created");
                                    activity.startActivity(intent);
                                    activity.finish();

                                }else{

                                    Helpers.showSnackBar(view, Objects.requireNonNull(task.getException()).getMessage(),R.color.colorError);

                                }

                            }
                        });

                    }else{
                        Helpers.showSnackBar(view, Objects.requireNonNull(task.getException()).getMessage(),R.color.colorError);

                    }

                }else{
                    Helpers.showSnackBar(view, Objects.requireNonNull(task.getException()).getMessage(),R.color.colorError);


                }

            }
        });



    }

    public void LoginInUser(final String email, String password, final Activity activity, final Boolean isHirer, final View view) {

      final AlertDialog alertDialog = Helpers.showDialogue(activity,"Signing in");
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    if(task.getResult()!= null && task.getResult().getUser() != null){
                        FirebaseFirestore.getInstance().collection("users").document(task.getResult().getUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Helpers.remove(alertDialog);

                                User userObject = documentSnapshot.toObject(User.class);
                                SharedPreferences sharedPreferences = activity.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Gson gson = new Gson();
                                editor.putString("user",gson.toJson(userObject));
                                editor.apply();
                               if(isHirer){
                                   Intent intent = new Intent(activity.getApplicationContext(), HirerIndexPage.class);
                                   intent.putExtra("is_hirer",true);
                                   activity.startActivity(intent);
                                   activity.finish();

                               }else{
                                   activity.finish();

                               }



                            }
                        });
                    }else{
                        //failed retrieving user
                        Helpers.remove(alertDialog);
                       Helpers.showSnackBar(view,"Failed logging you in, please try again",R.color.colorError);

                    }


                } else {

                  Helpers.remove(alertDialog);
                    try {
                        throw Objects.requireNonNull(task.getException());

                    } catch (Exception e) {

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {



                            Helpers.showSnackBar(view,"Email or password is incorrect",R.color.colorError);


                        } else if (e instanceof FirebaseAuthInvalidUserException) {

                            if (Objects.requireNonNull(e.getMessage()).equals("ERROR_USER_NOT_FOUND")) {


                                Helpers.showSnackBar(view,"This user does't exist in our database",R.color.colorError);


                            } else {


                                Helpers.showSnackBar(view,"This user does't exist in our database",R.color.colorError);


                            }


                        } else {


                            Helpers.showSnackBar(view,"Oops, problem logging in, please try again", R.color.colorError);


                        }


                    }


                }

            }
        });


    }
}
