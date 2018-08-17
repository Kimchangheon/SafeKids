package com.example.changheonkim.momfirebaseauth;

import android.Manifest;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class Sign_up_Parent extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    DatabaseReference databaseReference;

    Info_User_Parent info = new Info_User_Parent();
    String imagePath;
    EditText userIdInput;
    EditText userPwInput;
    EditText userParentsNameInput;
    EditText userChildNameinput;
    EditText userPhoneInput;
    EditText schoolCodeInput;
    ImageView imageView;
    Button signUp;

    private static final int GALLERY_CODE = 10 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_parent);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();

        userIdInput = (EditText)findViewById(R.id.userIdInput);
        userPwInput = (EditText)findViewById(R.id.userPwInput);
        userParentsNameInput = (EditText)findViewById(R.id.userParentsNameInput);
        userChildNameinput = (EditText)findViewById(R.id.userChildNameInput);
        userPhoneInput = (EditText)findViewById(R.id.userPhoneInput);
        schoolCodeInput = (EditText)findViewById(R.id.schoolCodeInput);
        imageView = (ImageView) findViewById(R.id.userPhotoInput);
        signUp = (Button)findViewById(R.id.signUp);

        /*권한*/ //버전 6이상부터 작동하라(갤러리 접근권한)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

                startActivityForResult(intent,GALLERY_CODE);//어디에 쓰이는 거였어?

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage(imagePath);
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
                            // Sign in success, update UI with the signed-in user's  information
                            uploadUserInfo();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Sign_up_Parent.this,MainActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();


                        }

                    }
                });
    }

    private void uploadUserInfo(){
        info.setId(userIdInput.getText().toString());
        info.setPassword(userPwInput.getText().toString());
        info.setName_parent(userParentsNameInput.getText().toString());
        info.setName_child(userChildNameinput.getText().toString());
        info.setPhone_number(userPhoneInput.getText().toString());
        info.setSchool_code(schoolCodeInput.getText().toString());
        databaseReference.child("user").child("user_parent").child(mAuth.getCurrentUser().getUid()).setValue(info);

    }

    private void UploadImage(String uri){
        StorageReference storageRef = storage.getReferenceFromUrl("gs://authtest-ee68e.appspot.com"); //저장소 주소를 이용해서 업로드 한다.

        Uri file = Uri.fromFile(new File(uri)); //data.getData()를 getPath함수를 이용하여 변환 후 넣어준다.
        StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);
//        Toast.makeText(getApplicationContext(), "이미지데이터 업로드 성공!", Toast.LENGTH_LONG).show();

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests")
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                info.setImageUrl(downloadUrl.toString()); //db상의 이미지 주소(나중에 받아올때 쓰임)

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GALLERY_CODE){
            imagePath = getPath(data.getData());
            File f = new File(imagePath);
            imageView.setImageURI(Uri.fromFile(f));
        }
    }

    //이미지를 업로드하기 위한 path받아오기위한? 요상한 코드 구글에서 지원을 안해줬다.
    public String getPath(Uri uri){
        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }

}
