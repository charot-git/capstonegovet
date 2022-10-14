package com.danasoftprototype.govet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private ArrayList<User> users;
    private Context context;
    private OnUserClickListener onUserClickListener;

    public UserAdapter(ArrayList<User> users, Context context, OnUserClickListener onUserClickListener) {
        this.users = users;
        this.context = context;
        this.onUserClickListener = onUserClickListener;
    }



    public interface OnUserClickListener{
        void onUserClicked(int position);
    }



    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.user_holder,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        holder.txtUsername.setText(users.get(position).getUsername());
        Glide.with(context).load(users.get(position).getProfilepic()).error(R.drawable.logogv).placeholder(R.drawable.logogv).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserHolder extends RecyclerView.ViewHolder{
        TextView txtUsername;
        ImageView imageView;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUserClickListener.onUserClicked(getAdapterPosition());
                }
            });
            txtUsername = itemView.findViewById(R.id.textUsername);
            imageView = itemView.findViewById(R.id.imageProfile);
        }
    }
}
