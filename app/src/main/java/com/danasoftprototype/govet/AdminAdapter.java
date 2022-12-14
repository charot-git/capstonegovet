package com.danasoftprototype.govet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.danasoftprototype.govet.FrontEnd.AddFriend;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.MyHolder>{

    Context context;

    public AdminAdapter(Context context, List<ModelUser> userList) {
        this.context = context;
        this.userList = userList;
    }

    List<ModelUser> userList;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_holder_admin, parent, false);

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


        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Pets").child(uid).child("Pet").child("Pet1");
        reference1.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if(task.getResult().exists()){
                        holder.pet1CardView.setVisibility(View.VISIBLE);
                        DataSnapshot dataSnapshot = task.getResult();
                        String pet1PicUrl = String.valueOf(dataSnapshot.child("petProfilePic").getValue());
                        Picasso.get().load(pet1PicUrl).placeholder(R.drawable.logogv).into(holder.pet1);
                    }
                }

            }
        });

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Pets").child(uid).child("Pet").child("Pet2");
        reference2.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if(task.getResult().exists()){
                        holder.pet2CardView.setVisibility(View.VISIBLE);
                        DataSnapshot dataSnapshot = task.getResult();
                        String pet2PicUrl = String.valueOf(dataSnapshot.child("petProfilePic").getValue());
                        Picasso.get().load(pet2PicUrl).placeholder(R.drawable.logogv).into(holder.pet2);
                    }
                }

            }
        });

        DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("Pets").child(uid).child("Pet").child("Pet3");
        reference3.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if(task.getResult().exists()){
                        holder.pet3CardView.setVisibility(View.VISIBLE);
                        DataSnapshot dataSnapshot = task.getResult();
                        String pet3PicUrl = String.valueOf(dataSnapshot.child("petProfilePic").getValue());
                        Picasso.get().load(pet3PicUrl).placeholder(R.drawable.logogv).into(holder.pet3);
                    }
                }

            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, com.danasoftprototype.govet.FrontEndAdmin.adminUsersDelete.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
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

        ImageView pet1, pet2, pet3;
        CardView pet1CardView, pet2CardView, pet3CardView;
        TextView nameText, emailText;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            
            pet1 = itemView.findViewById(R.id.addPetsPic1);
            pet2 = itemView.findViewById(R.id.addPetsPic2);
            pet3 = itemView.findViewById(R.id.addPetsPic3);
            pet1CardView = itemView.findViewById(R.id.pet1CardView);
            pet2CardView = itemView.findViewById(R.id.pet2CardView);
            pet3CardView = itemView.findViewById(R.id.pet3CardView);
            nameText = itemView.findViewById(R.id.userText);
            emailText = itemView.findViewById(R.id.emailText);
        }
    }
}
