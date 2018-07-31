package com.example.changheonkim.safekids;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

public class Admin extends AppCompatActivity {

    //value
    int StudentNum = 1;
    String[] StudentName;

    //widget
    TableRow admin_tableRow;
    Spinner adminSpinner;
    TextView studentName;
    TextView studentNumber;
    TextView studentNameInput;
    LinearLayout linearLayout;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        admin_tableRow = (TableRow)findViewById(R.id.admin_tableRow);
        adminSpinner = (Spinner)findViewById(R.id.adminSpinner);
        studentName = (TextView)findViewById(R.id.studentName);
        studentNumber = (TextView)findViewById(R.id.studentNumber);
        studentNameInput = (TextView)findViewById(R.id.studentNameInput);
        studentNameInput = (TextView)findViewById(R.id.studentNameInput);
        linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
        context = this;

        String StudentNum_string = "학생 수 : " + Integer.toString(StudentNum);

        studentNumber.setText(StudentNum_string);

        //signup spinner
        adminSpinner.setSelection(0);
        ArrayAdapter adminAdapter = ArrayAdapter.createFromResource(this,R.array.admin,android.R.layout.simple_spinner_item);
        adminAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adminSpinner.setAdapter(adminAdapter);

        makeAdminRow();

    }

    //StudentNum의 값에 따른 SignupRow를 동적생성
    public void makeAdminRow() {
        for (int i = 0; i < StudentNum; i++) {
            TableRow admintablerow = new TableRow(context);

        }
    }
}
