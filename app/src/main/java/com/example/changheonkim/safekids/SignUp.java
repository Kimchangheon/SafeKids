package com.example.changheonkim.safekids;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SignUp extends AppCompatActivity {
    int Studentnum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //signup spinner
        Spinner signUpSpinner = (Spinner)findViewById(R.id.signUp);
        ArrayAdapter signUpAdapter = ArrayAdapter.createFromResource(this,R.array.signUp,android.R.layout.simple_spinner_item);
        signUpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUpSpinner.setAdapter(signUpAdapter);
    }


}
