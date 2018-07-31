package com.example.changheonkim.safekids;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {

    //widget
    int Studentnum = 0;
    TableRow signUp_tableRow= (TableRow)findViewById(R.id.signUp_tableRow);;
    Spinner signUpSpinner= (Spinner)findViewById(R.id.signUpSpinner);
    TextView studentName = (TextView)findViewById(R.id.studentName);
    TextView studentNameInput = (TextView)findViewById(R.id.studentNameInput);
    LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //signup spinner
        signUpSpinner.setSelection(0);
        ArrayAdapter signUpAdapter = ArrayAdapter.createFromResource(this,R.array.signUp,android.R.layout.simple_spinner_item);
        signUpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUpSpinner.setAdapter(signUpAdapter);

        makesignUpRow();

    }
    //StudentNum의 값에 따른 SignupRow를 동적생성
    public void makesignUpRow() {
        for (int i = 0; i < Studentnum; i++) {
            TableRow signUptablerow = new TableRow(context);

        }
    }
}
