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
import androidx.recyclerview.widget.RecyclerView;

import com.danasoftprototype.govet.FrontEnd.AddFriend;
import com.google.android.gms.tasks.OnSuccessListener;
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


        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference pet1Ref = storageReference.child("images/pet/" + uid + "pet1.jpg");
        pet1Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).placeholder(R.drawable.logogv).into(holder.pet1);
            }
        });

        StorageReference pet2Ref = storageReference.child("images/pet2/" + uid + "pet2.jpg");
        pet2Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).placeholder(R.drawable.logogv).into(holder.pet2);
            }
        });

        StorageReference pet3Ref = storageReference.child("images/pet3/" + uid + "pet3.jpg");
        pet3Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).placeholder(R.drawable.logogv).error(R.drawable.logogv).into(holder.pet3);
            }
        });



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

        ImageView pet1, pet2, pet3;
        TextView nameText, emailText;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            pet1 = itemView.findViewById(R.id.addPetsPic1);
            pet2 = itemView.findViewById(R.id.addPetsPic2);
            pet3 = itemView.findViewById(R.id.addPetsPic3);
            nameText = itemView.findViewById(R.id.userText);
            emailText = itemView.findViewById(R.id.emailText);
        }
    }
}
