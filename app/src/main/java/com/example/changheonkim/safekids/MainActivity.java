package com.example.changheonkim.safekids;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public final int REQUEST_CODE_LOGIN = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button)findViewById(R.id.login);
        Button tempAdmin = (Button)findViewById(R.id.tempAdmin);
        Button tempParents = (Button)findViewById(R.id.tempParents);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(),Admin.class);
                startActivityForResult(mainActivity,REQUEST_CODE_LOGIN);
            }
        });

        tempAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(),Admin.class);
                startActivityForResult(mainActivity,REQUEST_CODE_LOGIN);
            }
        });

        tempParents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(),Parents.class);
                startActivityForResult(mainActivity,REQUEST_CODE_LOGIN);
            }
        });
    }
}
