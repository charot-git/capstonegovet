package com.danasoftprototype.govet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.danasoftprototype.govet.FrontEnd.AddFriend;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder>{

    String imageOfCurrentUser;

    Context context;

    public AdapterUsers(Context context, List<ModelUser> userList) {
        this.context = context;
        this.userList = userList;
    }

    List<ModelUser> userList;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_users, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String userImage = userList.get(position).getImage();
        String userName = userList.get(position).getName();
        String userEmail = userList.get(position).getEmail();
        String userUsername = userList.get(position).getUsername();
        String uid = userList.get(position).getUid();

        holder.nameText.setText(userName);
        holder.emailText.setText(userEmail);

        try {
            Picasso.get().load(userImage).placeholder(R.drawable.logogv).into(holder.dp);
        }
        catch (Exception e){

        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot = task.getResult();
                imageOfCurrentUser = String.valueOf(dataSnapshot.child("image").getValue());

            }
        });

        if (userEmail.equals("vetgovet@gmail.com")){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, chatActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("name_of_roomate" , userName)
                            .putExtra("username_of_roomate", userUsername)
                            .putExtra("dp_of_roomate", userImage)
                            .putExtra("email_of_roomate", userEmail)
                            .putExtra("dp_of_user", imageOfCurrentUser);
                    context.startActivity(intent);
                }
            });

        }
        else if (!FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("vetgovet@gmail.com")){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddFriend.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("uid" , uid)
                            .putExtra("name" , userName)
                            .putExtra("email" , userEmail)
                            .putExtra("username" , userUsername)
                            .putExtra("image" , userImage);


                    context.startActivity(intent);

                }
            });
        }
        else{
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, chatActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("name_of_roomate" , userName)
                            .putExtra("username_of_roomate", userUsername)
                            .putExtra("dp_of_roomate", userImage)
                            .putExtra("email_of_roomate", userEmail)
                            .putExtra("dp_of_user", R.drawable.logogv);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void filteredList(List<ModelUser> filteredList){
        userList = filteredList;
        notifyDataSetChanged();

    }

    class  MyHolder extends RecyclerView.ViewHolder{

        ImageView dp;
        TextView nameText, emailText;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            dp = itemView.findViewById(R.id.dp);
            nameText = itemView.findViewById(R.id.textName);
            emailText = itemView.findViewById(R.id.textEmail);
        }
    }
}
