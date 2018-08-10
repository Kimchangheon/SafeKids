package com.example.changheonkim.safekids;

import android.content.DialogInterface;
import android.content.Intent;
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

public class Parents_sign_up extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents_sign_up);

        final EditText idText = (EditText)findViewById(R.id.userIdInput);
        final EditText pwText = (EditText)findViewById(R.id.userPwInput);
        final EditText parentsNameText = (EditText)findViewById(R.id.userParentsNameInput);
        final EditText emailText = (EditText)findViewById(R.id.userEmailInput);
        final EditText phoneNumText = (EditText)findViewById(R.id.userPhoneInput);
        final EditText childNameText = (EditText)findViewById(R.id.userChildNameInput);
        final EditText schoolCodeText = (EditText)findViewById(R.id.schoolCodeInput);




        Button signup = (Button)findViewById(R.id.signUp);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();
                String userPw = pwText.getText().toString();
                String userParentsName = parentsNameText.getText().toString();
                String userEmail = emailText.getText().toString();
                String userChildName = childNameText.getText().toString();
                String schoolCode = schoolCodeText.getText().toString();
                int userPhoneNum;
                if("".equals(phoneNumText.getText().toString())|"".equals(idText.getText().toString())
                        |"".equals(pwText.getText().toString()) |"".equals(parentsNameText.getText().toString())
                        |"".equals(emailText.getText().toString()) | "".equals(childNameText.getText().toString())|"".equals(schoolCodeText.getText().toString())){
                    Toast.makeText(getApplicationContext(), "모두 입력해 주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    userPhoneNum = Integer.parseInt(phoneNumText.getText().toString());

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Parents_sign_up.this);
                                    builder.setMessage("회원등록에 성공했습니다.")
                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(Parents_sign_up.this, MainActivity.class);
                                                    Parents_sign_up.this.startActivity(intent);
                                                }
                                            })
                                            .create().show();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Parents_sign_up.this);
                                    builder.setMessage("회원등록에 실패했습니다.")
                                            .setNegativeButton("확인", null)
                                            .create().show();
                                    Intent intent = new Intent(Parents_sign_up.this, MainActivity.class);
                                    Parents_sign_up.this.startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.getStackTrace();
                            }
                        }
                    };//2018/08/10 14:05
                    RegisterRequest_Parents registerRequest_parents = new RegisterRequest_Admin(userID, userPw, userParentsName, userChildName, userEmail, userPhoneNum, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Parents_sign_up.this);
                    queue.add(registerRequest_parents);
                }
            }
        });
    }
    }
}
