package com.example.changheonkim.safekids;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Admin_SignUp extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up);

        final EditText idText = (EditText)findViewById(R.id.userIdInput);
        final EditText pwText = (EditText)findViewById(R.id.userPwInput);
        final EditText nameText = (EditText)findViewById(R.id.userAdminNameInput);
        final EditText phoneNum = (EditText)findViewById(R.id.userPhoneInput);
        final EditText emailText = (EditText)findViewById(R.id.userEmailInput);

        Button signup = (Button)findViewById(R.id.signUp);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();
                String userPw = pwText.getText().toString();
                String userAdminName = nameText.getText().toString();
                String userEmail = emailText.getText().toString();
                int userPhoneNum;
                if("".equals(phoneNum.getText().toString())|"".equals(idText.getText().toString())
                        |"".equals(pwText.getText().toString()) |"".equals(nameText.getText().toString())
                        |"".equals(emailText.getText().toString())){
                    Toast.makeText(getApplicationContext(), "모두 입력해 주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    userPhoneNum = Integer.parseInt(phoneNum.getText().toString());

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Admin_SignUp.this);
                                    builder.setMessage("회원등록에 성공했습니다.")
                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(Admin_SignUp.this, MainActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    Admin_SignUp.this.startActivity(intent);
                                                }
                                            })
                                            .create().show();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Admin_SignUp.this);
                                    builder.setMessage("회원등록에 실패했습니다.")
                                            .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(Admin_SignUp.this, MainActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    Admin_SignUp.this.startActivity(intent);
                                                }
                                            })
                                            .create().show();
                                }
                            } catch (JSONException e) {
                                e.getStackTrace();
                            }
                        }
                    };
                    RegisterRequest_Admin registerRequest_admin = new RegisterRequest_Admin(userID, userPw, userAdminName, userEmail, userPhoneNum, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Admin_SignUp.this);
                    queue.add(registerRequest_admin);
                }
            }
        });
    }
}
