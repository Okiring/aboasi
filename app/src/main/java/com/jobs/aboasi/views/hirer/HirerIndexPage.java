package com.jobs.aboasi.views.hirer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.jobs.aboasi.FullJobReview;
import com.jobs.aboasi.ProfileScreen;
import com.jobs.aboasi.R;
import com.jobs.aboasi.helpers.Helpers;
import com.jobs.aboasi.models.User;

public class HirerIndexPage extends AppCompatActivity {

    int selected = 0;
    User user;
SharedPreferences sharedPreferences;
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String userObject = sharedPreferences.getString("user",null);
        Gson gson = new Gson();
        user = gson.fromJson(userObject,User.class);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            fragment.onActivityResult(requestCode,resultCode,data);
        }


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hirer_index);
        sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        openFragment(new HirerHomePage());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.home:
                        selected = 0;
                        openFragment(new HirerHomePage());
                        return true;


                    case R.id.closed:
                        selected = 1;
                        openFragment(new HirerClosedJobs());
                        return true;

                    case R.id.profile:
                        selected = 2;
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user",user);
                        openFragment(new ProfileScreen(bundle));
                        return true;
                }

                return false;
            }
        });

    }

    public void onTapJob(View view){
        Intent intent = new Intent(view.getContext(), HirerJobDetails.class);
        if(selected == 0){
            intent.putExtra("show_close",true);
        }else{
            intent.putExtra("show_close",false);
        }
        startActivity(intent);

    }

    public void onTapAdd(View view){
startActivityForResult(new Intent(this,AddJobPosting.class), Helpers.HIRER_REQ_CODE);
    }

    public void onLogOut(View view){
        SharedPreferences.Editor prefs = sharedPreferences.edit();
       prefs.clear();
       prefs.apply();
       FirebaseAuth.getInstance().signOut();
        finish();

    }

    void openFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.commit();

    }
}
