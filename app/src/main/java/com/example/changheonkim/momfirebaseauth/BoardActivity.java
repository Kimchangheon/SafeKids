package com.example.changheonkim.momfirebaseauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<Info_User_Parent> info_user_parents = new ArrayList<>();
    private List<String> uidLists = new ArrayList<>();
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        database = FirebaseDatabase.getInstance(); //singletone패턴으로 선언을 해 주어야 한다.
        auth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final BoardRecyclerViewAdapter boardRecyclerViewAdapter = new BoardRecyclerViewAdapter();
        recyclerView.setAdapter(boardRecyclerViewAdapter);

        database.getReference().child("user").child("user_parent").addValueEventListener(new ValueEventListener() {//글자 하나씩 바뀔때마다 클라이언트에 알려준다.. 자동정으로 새로고침이 된다. 옵저버 패턴
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {       //9분 18초

                info_user_parents.clear(); //수정될 때마다 날아오기 때문에 clear안해주면 쌓인다.
                uidLists.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Info_User_Parent Info_User_Parent = snapshot.getValue(Info_User_Parent.class);
                    String uidKey = snapshot.getKey();
                    info_user_parents.add(Info_User_Parent);
                    uidLists.add(uidKey);

                }
                boardRecyclerViewAdapter.notifyDataSetChanged();//계속 갱신되기 때문에 새로고침을 해 주어야 한다.


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

   class BoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board,parent,false);
        return new CustomViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((CustomViewHolder)holder).textView.setText(info_user_parents.get(position).getName_child());
        ((CustomViewHolder)holder).textView2.setText(info_user_parents.get(position).getPhone_number());

        Glide.with(holder.itemView.getContext()).load(info_user_parents.get(position).getImageUrl()).into(((CustomViewHolder)holder).imageView);
        ((CustomViewHolder)holder).starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStarClicked(database.getReference().child("user_parent").child(uidLists.get(position)));
            }
        });

        if (info_user_parents.get(position).stars.containsKey(auth.getCurrentUser().getUid())){
            ((CustomViewHolder)holder).starButton.setImageResource(R.drawable.baseline_favorite_black_18dp);
        }
        else{
            ((CustomViewHolder)holder).starButton.setImageResource(R.drawable.baseline_favorite_border_black_18dp);
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

    public CustomViewHolder(View view) {
        super(view);
        imageView = (ImageView)view.findViewById(R.id.item_imageView);
        textView = (TextView)view.findViewById(R.id.item_textView);
        textView2 = (TextView)view.findViewById(R.id.item_textView2);
        starButton = (ImageView)view.findViewById(R.id.item_starBurron_imageView);
    }
}
}
