package com.danasoftprototype.govet.FrontEndVet;

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

import com.danasoftprototype.govet.ModelUser;
import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MonitoringAdapterVet extends RecyclerView.Adapter<MonitoringAdapterVet.MyHolder> {


    Context context;

    public MonitoringAdapterVet(Context context, List<MonitoringUsers> monitoringUsersList) {
        this.context = context;
        this.monitoringUsersList = monitoringUsersList;
    }

    List<MonitoringUsers> monitoringUsersList;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vet_userholder, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String uid = monitoringUsersList.get(position).getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();

                        String name = String.valueOf(dataSnapshot.child("name").getValue());
                        String dp = String.valueOf(dataSnapshot.child("image").getValue());

                        holder.nameText.setText(name);

                        Picasso.get().load(dp).placeholder(R.drawable.logogv).into(holder.dp);
                    }
                }

            }
        });

        holder.nameText.setText(uid);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, vetMonitorUser.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("uid", uid));
            }
        });

    }

    @Override
    public int getItemCount() {
        return monitoringUsersList.size();
    }

     class MyHolder extends RecyclerView.ViewHolder{
        ImageView dp;
        TextView nameText;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            dp = itemView.findViewById(R.id.userPic);
            nameText = itemView.findViewById(R.id.monitorName);

        }
    }
}
