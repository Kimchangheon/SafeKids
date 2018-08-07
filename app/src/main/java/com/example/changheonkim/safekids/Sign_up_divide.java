package com.example.changheonkim.safekids;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Sign_up_divide extends AppCompatActivity {
    Button admin;
    Button parents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_divide);
        admin = (Button)findViewById(R.id.admin);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminIntent = new Intent(getApplicationContext(),Admin_SignUp.class);
                startActivityForResult(adminIntent,101);
            }
        });
        parents = (Button)findViewById(R.id.parents);
        parents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent parentsIntent = new Intent(getApplicationContext(),Parents_sign_up.class);
                startActivityForResult(parentsIntent,101);
            }
        });

    }
}
