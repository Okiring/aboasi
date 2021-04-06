package com.jobs.aboasi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.jobs.aboasi.models.User;

public class ProfileScreen extends Fragment {
    private User user;
   public ProfileScreen(Bundle bundle){
       user = (User)bundle.getSerializable("user");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_screen,container,false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView email = view.findViewById(R.id.email);
        TextView firstName = view.findViewById(R.id.first_name);
        TextView lastName = view.findViewById(R.id.last_name);
        email.setText(user.getEmail());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());

    }


}
