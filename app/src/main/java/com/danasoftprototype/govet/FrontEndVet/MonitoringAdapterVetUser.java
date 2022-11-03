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

import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.petAdmitMonitoring;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MonitoringAdapterVetUser extends RecyclerView.Adapter<MonitoringAdapterVetUser.MyHolder> {

    Context context;

    public MonitoringAdapterVetUser(Context context, List<petAdmitMonitoring> petAdmitMonitoringList) {
        this.context = context;
        this.petAdmitMonitoringList = petAdmitMonitoringList;
    }

    List<petAdmitMonitoring> petAdmitMonitoringList;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vet_pet_container_admitted, parent, false);
        return new MyHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String status = petAdmitMonitoringList.get(position).getStatus();
        String name = petAdmitMonitoringList.get(position).getPetName();
        String date = petAdmitMonitoringList.get(position).getDate();
        String time = petAdmitMonitoringList.get(position).getTime();
        String dp = petAdmitMonitoringList.get(position).getPetPic();
        String uid = petAdmitMonitoringList.get(position).getUid();

        holder.status.setText(status);
        holder.name.setText(name);
        holder.date.setText(date);

        Picasso.get().load(dp).placeholder(R.drawable.logogv).into(holder.dp);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, vet_monitor_update_pet.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("uid", uid)
                        .putExtra("status" , status)
                        .putExtra("name" , name)
                        .putExtra("date", date)
                        .putExtra("time", time)
                        .putExtra("dp", dp)


                );
            }
        });


    }

    @Override
    public int getItemCount() {
        return petAdmitMonitoringList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView dp;
        TextView status, name, date;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            dp = itemView.findViewById(R.id.petPic1);
            status = itemView.findViewById(R.id.status);
            name = itemView.findViewById(R.id.petname1);
            date = itemView.findViewById(R.id.date1);
        }
    }
}
