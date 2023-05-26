package com.example.chatapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusHolder> {
    List<MyContacts> list;
    Context context;

    public StatusAdapter(Context context, List<MyContacts> list, ContactsList c) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public StatusAdapter.StatusHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflater.inflate(R.layout.friendlist_item,viewGroup,false);
        StatusHolder holder=new StatusHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StatusHolder statusHolder, int i) {

        MyContacts contact;
        contact=list.get(i);
        final String id=contact.getContactName();
        Log.d("contents",id);
        statusHolder.dp.setImageResource(R.drawable.ic_visibility_black_24dp);
        statusHolder.dp.setBackgroundColor(Color.RED);
        statusHolder.name.setText(contact.getContactName());
        statusHolder.msg.setText(contact.getMsg().getTime().toString());
        statusHolder.time.setText("");
        statusHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MainActivity.class);
                intent.putExtra("name",id);
                context.startActivity(intent);


            }
        });
        statusHolder.dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, VideoViewer.class);
                intent.putExtra("imageId",id );
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StatusHolder extends RecyclerView.ViewHolder {
        ImageView dp;
        TextView name;
        TextView msg;
        TextView time;
        RelativeLayout itemLayout;

        public StatusHolder(View view) {
            super(view);
            dp = view.findViewById(R.id.dp);
            name = view.findViewById(R.id.name);
            msg = view.findViewById(R.id.Lmsg);
            time = view.findViewById(R.id.LmTime);
            itemLayout = view.findViewById(R.id.rLay);

        }
    }
}
