package com.danasoftprototype.govet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.danasoftprototype.govet.FrontEnd.monitorPetUser;
import com.danasoftprototype.govet.FrontEndVet.vet_monitor_update_pet;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MonitoringAdapter extends RecyclerView.Adapter<MonitoringAdapter.MyHolder> {

    private List<petAdmitMonitoring> petAdmitMonitoringList;
    Context context;

    public MonitoringAdapter(Context context, List<petAdmitMonitoring> petAdmitMonitoringList) {
        this.petAdmitMonitoringList = petAdmitMonitoringList;
        this.context = context;
    }

    @NonNull
    @Override
    public MonitoringAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_holder_monitor_admitted, parent, false);
        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MonitoringAdapter.MyHolder holder, int position) {
        String name = petAdmitMonitoringList.get(position).getPetName();
        String status = petAdmitMonitoringList.get(position).getStatus();
        String dp = petAdmitMonitoringList.get(position).getPetPic();
        String date = petAdmitMonitoringList.get(position).getDate();
        String time = petAdmitMonitoringList.get(position).getTime();
        String breed = petAdmitMonitoringList.get(position).getBreed();

        holder.name.setText(name);
        holder.status.setText(status);

        Picasso.get().load(dp).placeholder(R.drawable.logogv).into(holder.petdp);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, monitorPetUser.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("name" , name)
                        .putExtra("dp",dp)
                        .putExtra("breed", breed)
                        .putExtra("status", status)
                        .putExtra("date", date)
                        .putExtra("time", time)

                );
            }
        });

    }

    @Override
    public int getItemCount() {
        return petAdmitMonitoringList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView petdp;
        TextView name, status;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            petdp = itemView.findViewById(R.id.petPic1);
            name = itemView.findViewById(R.id.petname1);
            status = itemView.findViewById(R.id.statusText);

        }
    }
}
