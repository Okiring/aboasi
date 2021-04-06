package com.jobs.aboasi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jobs.aboasi.helpers.Helpers;

public class SuccessPage extends AppCompatActivity {
    Boolean fromAdding;
    @Override
    public void onBackPressed() {
        if(fromAdding){
            Intent intent = new Intent();
            intent.putExtra("added",true);
            setResult(Helpers.HIRER_REQ_ADD,intent);
        }
        super.onBackPressed();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);
        TextView messageView = findViewById(R.id.message);
        String message = getIntent().getStringExtra("message");
         fromAdding = getIntent().getBooleanExtra("added",false);
        if(message != null){
            messageView.setText(message);

        }
        Button button = findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }
}