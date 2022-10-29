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

import com.danasoftprototype.govet.FrontEndVet.vet_monitor_update;
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
        View view = LayoutInflater.from(context).inflate(R.layout.monitorholder, parent, false);
        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MonitoringAdapter.MyHolder holder, int position) {
        String breed = petAdmitMonitoringList.get(position).getBreed();
        String date = petAdmitMonitoringList.get(position).getDate();
        String petName = petAdmitMonitoringList.get(position).getPetName();
        String petPic = petAdmitMonitoringList.get(position).getPetPic();
        String status = petAdmitMonitoringList.get(position).getStatus();
        String time = petAdmitMonitoringList.get(position).getTime();
        String name = petAdmitMonitoringList.get(position).getName();

        holder.name.setText("Name : "+ name);
        holder.petname.setText("Pet name : " + petName);
        holder.date.setText("Date admitted : " + date);
        holder.status.setText("Pet status : "+ status);

        Picasso.get().load(petPic).placeholder(R.drawable.logogv).into(holder.petdp);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, vet_monitor_update_pet.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("pet" , petName)
                        .putExtra("status" , status)
                        .putExtra("date" , date)
                        .putExtra("time" , time);


                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return petAdmitMonitoringList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView petdp;
        TextView name, petname, date, status;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            petdp = itemView.findViewById(R.id.addPetsPic1);

            name = itemView.findViewById(R.id.monitorName);
            petname = itemView.findViewById(R.id.monitorPetName);
            date = itemView.findViewById(R.id.monitorDate);
            status = itemView.findViewById(R.id.monitorStatus);



        }
    }
}
