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
import com.danasoftprototype.govet.FrontEnd.Bookings;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyHolder>{

    private List<Bookings> bookingsList;
    Context context;

    public AppointmentAdapter(Context context, List<Bookings> bookingsList) {
        this.context = context;
        this.bookingsList = bookingsList;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_row, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String time = bookingsList.get(position).getTime();
        String day = bookingsList.get(position).getDay();
        String month = bookingsList.get(position).getMonth();
        String year = bookingsList.get(position).getYear();
        String date = bookingsList.get(position).getDate();
        String description = bookingsList.get(position).getDescription();
        String name = bookingsList.get(position).getName();
        String uid = bookingsList.get(position).getUid();

        holder.name.setText("Name : " + name);
        holder.date.setText("Date : " + date);
        holder.time.setText("Time : " +time);
        holder.description.setText("Description : "+description);






        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , appointment_confirm.class)
                        .putExtra("name" , name)
                        .putExtra("date" , date)
                        .putExtra("time" , time)
                        .putExtra("description" , description)
                        .putExtra("uid" , uid)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return bookingsList.size();
    }

    public void filteredList(List<Bookings> filteredList){
        bookingsList = filteredList;
        notifyDataSetChanged();

    }

    class  MyHolder extends RecyclerView.ViewHolder{

        TextView name, date, time, description;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameText);
            date = itemView.findViewById(R.id.DateText);
            time = itemView.findViewById(R.id.timeText);
            description = itemView.findViewById(R.id.DescriptionText);

        }
    }
}
