package com.example.changheonkim.safekids;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    //code 101 : request success
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final Button login = (Button)findViewById(R.id.login);
        final Button tempAdmin = (Button)findViewById(R.id.tempAdmin);
        final Button tempParents = (Button)findViewById(R.id.tempParents);
        final TextView signUp = (TextView)findViewById(R.id.SignUp);
        final TextView findIdOrPw = (TextView)findViewById(R.id.FindIdOrPw);
        final EditText idText = (EditText)findViewById(R.id.userId);
        final EditText pwText = (EditText)findViewById(R.id.userPw);
        final RadioGroup state = (RadioGroup)findViewById(R.id.state);


        //로그인버튼
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = idText.getText().toString();
                final String userPw = pwText.getText().toString();
                int st = state.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(st);
                if (rb == null) {
                    Toast.makeText(getApplicationContext(), "계정권한을 체크해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    if (rb.getText().equals("관리자")) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {
                                        //db에서 유저의 정보 받아옴
                                        String userID = jsonResponse.getString("userID");
                                        String userPw = jsonResponse.getString("userPw");
                                        String userAdminName = jsonResponse.getString("userAdminName");

                                            Intent intent = new Intent(MainActivity.this, Admin.class);
                                            intent.putExtra("userID", userID);
                                            intent.putExtra("userAdminName", userAdminName);

                                            MainActivity.this.startActivity(intent);
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setMessage("로그인에 실패했습니다.")
                                                .setNegativeButton("다시 시도", null)
                                                .create()
                                                .show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        LoginRequest_Admin loginRequest_admin = new LoginRequest_Admin(userID, userPw, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        queue.add(loginRequest_admin);
                    } else if (rb.getText().equals("학부모")) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {
                                        //db에서 유저의 정보 받아옴
                                        String userID = jsonResponse.getString("userID");
                                        String userPw = jsonResponse.getString("userPw");
                                            Intent intent = new Intent(MainActivity.this, Parents.class);
                                            intent.putExtra("userID", userID);
                                            intent.putExtra("userPw", userPw);

                                            MainActivity.this.startActivity(intent);
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setMessage("로그인에 실패했습니다.")
                                                .setNegativeButton("다시 시도", null)
                                                .create()
                                                .show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        LoginRequest_Parents loginRequest_parents = new LoginRequest_Parents(userID, userPw, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        queue.add(loginRequest_parents);
                    } else{
                    }
                }
            }
        });



        //임시버튼 시작
        tempAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(),Admin.class);
                startActivityForResult(mainActivity,101);
            }
        });

                tempParents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(),Parents.class);
                startActivityForResult(mainActivity,101);
            }
        });
        //임시버튼 끝

        /* "회원가입" 언더라인 주고 눌렀을 때 Sign_Up_Divide액티비티로 넘어가도록 함 */
        SpannableString content = new SpannableString("회원가입");
        content.setSpan(new UnderlineSpan(),0,content.length(),0);
        signUp.setText(content);
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(),Sign_up_divide.class);
                startActivityForResult(mainActivity,101);
            }
        });

        /* "아이디나 비밀번호를 잊으셨나요?" 언더라인 주고 눌렀을 때 FindIdOrPw 액티비티로 넘어가도록 함 */
        SpannableString content2 = new SpannableString("아이디나 비밀번호를 잊으셨나요?");
        content2.setSpan(new UnderlineSpan(), 0,content2.length(), 0);
        findIdOrPw.setText(content2);
        findIdOrPw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(),FindIdOrPw.class);
                startActivityForResult(mainActivity,101);
            }
        });
    }
}
