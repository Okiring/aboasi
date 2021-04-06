package com.jobs.aboasi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jobs.aboasi.adapters.GridViewAdapter;
import com.jobs.aboasi.models.GridObject;
import com.jobs.aboasi.views.hiree.HireeHomePage;
import com.jobs.aboasi.views.hiree.HireeIndexPage;
import com.jobs.aboasi.views.hirer.HirerIndexPage;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GridObject gridObject;
    SharedPreferences sharedPreferences;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        final ArrayList<GridObject> gridObjectArrayList = new ArrayList<>();
        Button proceedButton = findViewById(R.id.proceed);
        gridObjectArrayList.add(new GridObject("Employer",true,1));
        gridObjectArrayList.add(new GridObject("Job Seeker",false,2));
        GridView gridView = findViewById(R.id.gridView);
        gridObject = gridObjectArrayList.get(0);
        sharedPreferences = getApplication().getSharedPreferences("prefs",MODE_PRIVATE);
        final GridViewAdapter gridViewAdapter = new GridViewAdapter(this,gridObjectArrayList);
        gridView.setAdapter(gridViewAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i = 0; i < gridViewAdapter.getGridObjects().size();i++){
                    gridViewAdapter.getGridObjects().get(i).setTapped(false);
                }
                gridViewAdapter.getItem(position).setTapped(true);
                gridObject = gridObjectArrayList.get(position);
                gridViewAdapter.notifyDataSetChanged();
            }
        });
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gridObject.getId() ==1){

                   Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                   intent.putExtra("is_hirer",true);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainActivity.this, HireeIndexPage.class);

                    startActivity(intent);
                }

        }
        });
    }

    private void showDialogue() {

        LayoutInflater layoutInflater = getLayoutInflater();
        @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.custom_dialogue,null);
        TextView textView = view.findViewById(R.id.alertTitle);
        textView.setText("Preparing");
        alertDialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setView(view)
                .create();

        alertDialog.show();

    }

    private void remove(){

        alertDialog.dismiss();
    }
}
