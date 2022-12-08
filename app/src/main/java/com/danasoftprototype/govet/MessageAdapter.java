package com.danasoftprototype.govet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    private ArrayList<Message> messages;
    private String senderImg, receiverImg;
    Context context;

    public MessageAdapter(ArrayList<Message> messages, String senderImg, String receiverImg, Context context) {
        this.messages = messages;
        this.senderImg = senderImg;
        this.receiverImg = receiverImg;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_holder,parent,false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        holder.TxtMessage.setText(messages.get(position).getContent());
        ConstraintLayout constraintLayout = holder.ccll;

        String link = messages.get(position).getContent();

        if (messages.get(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
            Glide.with(context).load(senderImg).error(R.drawable.logogv).placeholder(R.drawable.logogv).into(holder.dp);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.clear(R.id.cardView, ConstraintSet.LEFT);
            constraintSet.clear(R.id.messages, ConstraintSet.LEFT);
            constraintSet.clear(R.id.imgMessage, ConstraintSet.LEFT);
            constraintSet.connect(R.id.cardView, ConstraintSet.RIGHT, R.id.ccLayout, ConstraintSet.RIGHT, 0);
            constraintSet.connect(R.id.messages, ConstraintSet.RIGHT, R.id.cardView, ConstraintSet.LEFT, 0);
            constraintSet.connect(R.id.imgMessage, ConstraintSet.RIGHT, R.id.cardView, ConstraintSet.LEFT, 0);
            constraintSet.applyTo(constraintLayout);
        }
        else {
            Glide.with(context).load(receiverImg).error(R.drawable.logogv).placeholder(R.drawable.logogv).into(holder.dp);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.clear(R.id.cardView, ConstraintSet.RIGHT);
            constraintSet.clear(R.id.messages, ConstraintSet.RIGHT);
            constraintSet.clear(R.id.imgMessage, ConstraintSet.RIGHT);
            constraintSet.connect(R.id.cardView, ConstraintSet.LEFT, R.id.ccLayout, ConstraintSet.LEFT, 0);
            constraintSet.connect(R.id.messages, ConstraintSet.LEFT, R.id.cardView, ConstraintSet.RIGHT, 0);
            constraintSet.connect(R.id.imgMessage, ConstraintSet.LEFT, R.id.cardView, ConstraintSet.RIGHT, 0);
            constraintSet.applyTo(constraintLayout);
        }

        if (link.startsWith("https")) {
            holder.TxtMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(link); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessageHolder extends RecyclerView.ViewHolder{
        ConstraintLayout ccll;
        TextView TxtMessage;
        ImageView dp, imgMessage;
        public MessageHolder(@NonNull View itemView) {
            super(itemView);

            ccll = itemView.findViewById(R.id.ccLayout);
            TxtMessage = itemView.findViewById(R.id.messages);
            dp = itemView.findViewById(R.id.dpOfUser);
            imgMessage = itemView.findViewById(R.id.imgMessage);
            imgMessage.setVisibility(View.INVISIBLE);


        }
    }
}
