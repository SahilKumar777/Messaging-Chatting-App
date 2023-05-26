package com.example.chatapp;

import android.content.Context;
import android.content.Intent;
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

public class CallsAdapter extends RecyclerView.Adapter<CallsAdapter.CallsHolder> {
    List<MyContacts> list;
    Context context;

    public CallsAdapter(Context context, List<MyContacts> list, ContactsList c) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CallsAdapter.CallsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflater.inflate(R.layout.friendlist_item,viewGroup,false);
        CallsHolder holder=new CallsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CallsHolder callsHolder, int i) {

        MyContacts contact;
        contact=list.get(i);
        final String id=contact.getContactName();
        Log.d("contents",id);
        callsHolder.dp.setImageResource(R.drawable.ic_phone_black_24dp);
        callsHolder.name.setText(contact.getContactName());
        callsHolder.msg.setText("5 Missed calls");
        callsHolder.time.setText(contact.getMsg().getTime().toString());
        callsHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MainActivity.class);
                intent.putExtra("name",id);
                context.startActivity(intent);


            }
        });
        callsHolder.dp.setOnClickListener(new View.OnClickListener() {
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

    public class CallsHolder extends RecyclerView.ViewHolder {
        ImageView dp;
        TextView name;
        TextView msg;
        TextView time;
        RelativeLayout itemLayout;

        public CallsHolder(View view) {
            super(view);
            dp = view.findViewById(R.id.dp);
            name = view.findViewById(R.id.name);
            msg = view.findViewById(R.id.Lmsg);
            time = view.findViewById(R.id.LmTime);
            itemLayout = view.findViewById(R.id.rLay);

        }
    }
}


