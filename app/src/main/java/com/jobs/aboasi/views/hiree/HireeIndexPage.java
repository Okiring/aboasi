package com.jobs.aboasi.views.hiree;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.jobs.aboasi.FullJobReview;
import com.jobs.aboasi.LoginScreen;
import com.jobs.aboasi.ProfileScreen;
import com.jobs.aboasi.R;
import com.jobs.aboasi.models.User;

public class HireeIndexPage extends AppCompatActivity {

    User user;
    SharedPreferences sharedPreferences;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
        String userObject = sharedPreferences.getString("user",null);
       if(userObject != null){
           Gson gson = new Gson();
           user = gson.fromJson(userObject,User.class);
       }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sharedPreferences != null){
            String userObject = sharedPreferences.getString("user",null);
            if(userObject != null){
                Gson gson = new Gson();
                user = gson.fromJson(userObject,User.class);
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        openFragment(new HireeHomePage());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.home:

                        openFragment(new HireeHomePage());
                      return true;

                    case R.id.profile:
                        if(FirebaseAuth.getInstance().getCurrentUser() != null){
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user",user);
                            openFragment(new ProfileScreen(bundle));
                            openFragment(new ProfileScreen(bundle));
                            return true;
                        }else{
                            Intent intent =   new Intent(HireeIndexPage.this, LoginScreen.class);
                            intent.putExtra("is_hirer",false);
                            startActivity(intent);
                            return false;
                        }

                }

                return false;
            }
        });


    }

    void openFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.commit();

    }
    public void onLogOut(View view){
        SharedPreferences.Editor prefs = sharedPreferences.edit();
        prefs.clear();
        prefs.apply();
        FirebaseAuth.getInstance().signOut();
        finish();

    }
}
