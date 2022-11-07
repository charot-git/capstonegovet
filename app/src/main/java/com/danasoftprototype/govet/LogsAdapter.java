package com.danasoftprototype.govet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LogsAdapter extends RecyclerView.Adapter<LogsAdapter.MyHolder> {

    public LogsAdapter(Context context, List<Logs> logsList) {
        this.context = context;
        this.logsList = logsList;
    }

    Context context;
    List<Logs>logsList;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.logs_holder, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String nameString = logsList.get(position).getName();
        String timeString = logsList.get(position).getTime();
        String dateString = logsList.get(position).getDate();
        String statusString = logsList.get(position).getStatus();

        holder.name.setText(nameString);
        holder.time.setText(timeString);
        holder.date.setText(dateString);
        holder.status.setText(statusString);

    }

    @Override
    public int getItemCount() {
        return logsList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView name, date, time, status;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            status = itemView.findViewById(R.id.status);
        }
    }

}
