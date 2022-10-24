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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder>{

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
