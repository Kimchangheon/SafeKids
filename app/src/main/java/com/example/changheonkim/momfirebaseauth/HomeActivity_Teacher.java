package com.example.changheonkim.momfirebaseauth;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.changheonkim.momfirebaseauth.model.NotificationModel;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HomeActivity_Teacher extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int GALLERY_CODE = 10 ;
    private TextView nameTextView; //네비게이션 뷰에 사용자 이름 표시
    private TextView emailTextView;// 이메일 표시
    private FirebaseAuth auth; //인증 변수를 global로 선언
    private FirebaseStorage storage; //마찬가지로 저장소도  global로 선언
    private FirebaseDatabase database; //데이터베이스도 선언
    private DatabaseReference mDatabase;

    private FloatingActionButton sendingButton;

    private RecyclerView recyclerView;

    private List<Info_User_Parent> info_user_parents = new ArrayList<>();
    private List<String> uidLists = new ArrayList<>();
    private HashMap<String, String> info_absent = new HashMap<String, String>();


    void sendGcm(){

        Gson gson = new Gson();

        NotificationModel notificationModel = new NotificationModel();
        notificationModel.to =  "ezTNMI4z9UU:APA91bGb7obZb9NF04q7NxvPy3i98cEoIrKO2xgsrxF9KPOJn4Lhe0Ocnazb9a6aQ_Per9r7P06E7mSeL33mpUbBJVpQq1iYNg-YTMJ6PIXgvbCoRczmlwdog6VbQ7PGEwXDWdSAonyfwzB9cQ21PWWUwVrpythBJQ";
        notificationModel.notification.title = "보낸이 아이디";
        notificationModel.notification.text = "니 아들이 안왔는디?";

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset = utf8"), gson.toJson(notificationModel));

        Request request = new Request.Builder()
                .header("Content-Type", "application/json")
                .addHeader("Authorization", "key=AIzaSyDTyevOMo8GBpzJ6mPub4e7RMoMn7T7BEQ")
                .url("https://gcm-http.googleapis.com/gcm/send")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_teacher);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        auth = FirebaseAuth.getInstance(); //auth변수에 사용자 인스턴스객체를 받아온다.
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        sendingButton = (FloatingActionButton)findViewById(R.id.sendingButton);
        sendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "전송 완료", Toast.LENGTH_LONG).show();
                sendGcm();

            }
        });


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final HomeActivity_Teacher.HomeRecyclerViewAdapter homeRecyclerViewAdapter = new HomeActivity_Teacher.HomeRecyclerViewAdapter();
        recyclerView.setAdapter(homeRecyclerViewAdapter);

        database.getReference().child("user").child("user_parent").addValueEventListener(new ValueEventListener() {//글자 하나씩 바뀔때마다 클라이언트에 알려준다.. 자동정으로 새로고침이 된다. 옵저버 패턴
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {       //9분 18초

                info_user_parents.clear(); //수정될 때마다 날아오기 때문에 clear안해주면 쌓인다.
                uidLists.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){//처음부터 끝까지 하나씩 꺼낸다.
                    Info_User_Parent Info_User_Parent = snapshot.getValue(Info_User_Parent.class);
                    String uidKey = snapshot.getKey();
                    info_user_parents.add(Info_User_Parent);
                    uidLists.add(uidKey);

                }
                homeRecyclerViewAdapter.notifyDataSetChanged();//계속 갱신되기 때문에 새로고침을 해 주어야 한다.


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);

        nameTextView = (TextView)view.findViewById(R.id.header_name_textView);
        emailTextView = (TextView)view.findViewById(R.id.header_email_textView);

        nameTextView.setText(auth.getCurrentUser().getDisplayName());
        emailTextView.setText(auth.getCurrentUser().getEmail());//현재 사용자의 이메일을 가져와서 텍스트뷰에 보여준다.


        passPushTokenToServer();
    }

    void passPushTokenToServer() //push알람 토큰을 서버로 전송
    {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String token = FirebaseInstanceId.getInstance().getToken();
        Map<String, Object> map = new HashMap<>();
        map.put("pushToken",token);
        FirebaseDatabase.getInstance().getReference().child("user").child("user_teacher").child(uid).updateChildren(map);//setvalue하게되면 기존의 데이터가 다 날라간다.
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_board) {
            startActivity(new Intent(HomeActivity_Teacher.this,BoardActivity.class));
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

            startActivityForResult(intent,GALLERY_CODE);//어디에 쓰이는 거였어?

        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(getApplicationContext(), HomeActivity_Parent.class));

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if(id==R.id.nav_logout){
            auth.signOut();
            LoginManager.getInstance().logOut(); //페이스북 로그아웃을 위한 코드
            finish(); //자기는 사라지고
            Intent intent = new Intent (HomeActivity_Teacher.this,MainActivity.class);//앞에 MainActivity.f
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_teacher,parent,false);
            return new HomeActivity_Teacher.CustomViewHolder(view);

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ((HomeActivity_Teacher.CustomViewHolder)holder).textView.setText(info_user_parents.get(position).getName_child());
            ((HomeActivity_Teacher.CustomViewHolder)holder).textView2.setText(info_user_parents.get(position).getPhone_number());
            //부모에게 보낼 정보 map에 집어넣기
            info_absent.put(info_user_parents.get(position).getPhone_number(), ((HomeActivity_Teacher.CustomViewHolder)holder).spinner.getSelectedItem().toString());

            Glide.with(holder.itemView.getContext()).load(info_user_parents.get(position).getImageUrl()).into(((HomeActivity_Teacher.CustomViewHolder)holder).imageView);

            ((HomeActivity_Teacher.CustomViewHolder)holder).starButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onStarClicked(database.getReference().child("user_parent").child(uidLists.get(position)));
                }
            });

            if (info_user_parents.get(position).stars.containsKey(auth.getCurrentUser().getUid())){
                ((HomeActivity_Teacher.CustomViewHolder)holder).starButton.setImageResource(R.drawable.baseline_favorite_black_18dp);
            }
            else{
                ((HomeActivity_Teacher.CustomViewHolder)holder).starButton.setImageResource(R.drawable.baseline_favorite_border_black_18dp);
            }

        }

        @Override
        public int getItemCount() {
            return info_user_parents.size();
        }

        private void onStarClicked(DatabaseReference postRef) {
            postRef.runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {
                    Info_User_Parent Info_User_Parent = mutableData.getValue(Info_User_Parent.class);
                    if (Info_User_Parent == null) {
                        return Transaction.success(mutableData);
                    }

                    if (Info_User_Parent.stars.containsKey(auth.getCurrentUser().getUid())) {
                        // Unstar the post and remove self from stars
                        Info_User_Parent.starCount = Info_User_Parent.starCount - 1;
                        Info_User_Parent.stars.remove(auth.getCurrentUser().getUid());
                    } else {
                        // Star the post and add self to stars
                        Info_User_Parent.starCount = Info_User_Parent.starCount + 1;
                        Info_User_Parent.stars.put(auth.getCurrentUser().getUid(), true);
                    }

                    // Set value and report transaction success
                    mutableData.setValue(Info_User_Parent);
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b,
                                       DataSnapshot dataSnapshot) {
                    // Transaction completed
                }
            });
        }
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView textView2;
        ImageView starButton;
        Spinner spinner;

        public CustomViewHolder(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.item_imageView);
            textView = (TextView)view.findViewById(R.id.item_textView);
            textView2 = (TextView)view.findViewById(R.id.item_textView2);
            starButton = (ImageView)view.findViewById(R.id.item_starBurron_imageView);
            spinner = (Spinner)view.findViewById(R.id.spinner);

            }
    }
}



