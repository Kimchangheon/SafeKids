package com.example.changheonkim.safekids;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.SingleLineTransformationMethod;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public final int REQUEST_CODE_LOGIN = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button)findViewById(R.id.login);
        Button tempAdmin = (Button)findViewById(R.id.tempAdmin);
        Button tempParents = (Button)findViewById(R.id.tempParents);
        TextView SignUp = (TextView)findViewById(R.id.SignUp);
        TextView FindIdOrPw = (TextView)findViewById(R.id.FindIdOrPw);


        //로그인버튼
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(),Admin.class);
                startActivityForResult(mainActivity,REQUEST_CODE_LOGIN);
            }
        });



        //임시버튼 시작
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
        //임시버튼 끝

        /* "회원가입" 언더라인 주고 눌렀을 때 SignUp 액티비티로 넘어가도록 함 */
        SpannableString content = new SpannableString("회원가입");
        content.setSpan(new UnderlineSpan(),0,content.length(),0);
        SignUp.setText(content);
        SignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(),SignUp.class);
                startActivityForResult(mainActivity,REQUEST_CODE_LOGIN);
            }
        });
        /* "아이디나 비밀번호를 잊으셨나요?" 언더라인 주고 눌렀을 때 FindIdOrPw 액티비티로 넘어가도록 함 */
        SpannableString content2 = new SpannableString("아이디나 비밀번호를 잊으셨나요?");
        content2.setSpan(new UnderlineSpan(), 0,content2.length(), 0);
        FindIdOrPw.setText(content2);
        FindIdOrPw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(),FindIdOrPw.class);
                startActivityForResult(mainActivity,REQUEST_CODE_LOGIN);
            }
        });
    }
}
