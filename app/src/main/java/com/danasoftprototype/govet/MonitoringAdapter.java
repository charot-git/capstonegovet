package com.danasoftprototype.govet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MonitoringAdapter extends RecyclerView.Adapter<MonitoringAdapter.MyHolder> {

    private List<petAdmitMonitoring> petAdmitMonitoringList;
    Context context;

    public MonitoringAdapter(List<petAdmitMonitoring> petAdmitMonitoringList, Context context) {
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

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyHolder extends RecyclerView.ViewHolder{

        public MyHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
