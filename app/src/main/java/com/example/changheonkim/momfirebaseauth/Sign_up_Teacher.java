package com.example.changheonkim.momfirebaseauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_up_Teacher extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    EditText userIdInput;
    EditText userPwInput;
    EditText userNameInput;
    EditText userPhoneInput;
    EditText schoolCodeInput;
    Button signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_teacher);

        mAuth = FirebaseAuth.getInstance();

        userIdInput = (EditText)findViewById(R.id.userIdInput);
        userPwInput = (EditText)findViewById(R.id.userPwInput);
        userNameInput = (EditText)findViewById(R.id.userAdminNameInput);
        userPhoneInput = (EditText)findViewById(R.id.userPhoneInput);
        schoolCodeInput = (EditText)findViewById(R.id.schoolCodeInput);
        signUp = (Button)findViewById(R.id.signUp);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser(userIdInput.getText().toString(), userPwInput.getText().toString());
            }
        });
    }

    private void createUser(final String email, final String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            uploadUserInfo();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Sign_up_Teacher.this,MainActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }
    private void uploadUserInfo(){
        Info_User_Teacher info = new Info_User_Teacher();
        info.setId(userIdInput.getText().toString());
        info.setPassword(userPwInput.getText().toString());
        info.setName_teacher(userNameInput.getText().toString());
        info.setPhone_number(userPhoneInput.getText().toString());
        info.setSchool_code(schoolCodeInput.getText().toString());
        databaseReference.child("user").child("user_teacher").push().setValue(info);

    }
}
