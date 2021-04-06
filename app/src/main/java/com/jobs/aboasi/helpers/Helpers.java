package com.jobs.aboasi.helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.jobs.aboasi.R;

public class Helpers {
    public static int HIRER_REQ_CODE = 22;
    public static int HIRER_REQ_DELETE = 11;
    public static int HIRER_REQ_ADD = 16;
    public static AlertDialog showDialogue(Activity activity,String message) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.custom_dialogue,null);
        TextView textView = view.findViewById(R.id.alertTitle);
        textView.setText(message);
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setView(view)
                .create();

         alertDialog.show();
         return  alertDialog;

    }
    public static void remove(AlertDialog alertDialog){

        alertDialog.dismiss();
    }

    public static void showSnackBar(View view, String message, int color){

        final Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(),color));
        snackbar.setActionTextColor(ContextCompat.getColor(view.getContext(),R.color.white_text_color));

        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                snackbar.dismiss();

            }
        });


        snackbar.show();


    }
}
