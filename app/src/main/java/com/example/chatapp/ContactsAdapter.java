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
import android.widget.VideoView;


import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyHolder> {
    Context context;
    List<MyContacts> myContactsList;
    ContactsList contactsListActivity;

    public ContactsAdapter(Context applicationContext,List<MyContacts> list,ContactsList contactsList){
        this.context=applicationContext;
        this.contactsListActivity=contactsList;
        this.myContactsList=list;
    }

    @NonNull
    @Override
    public ContactsAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.friendlist_item,viewGroup,false);
        MyHolder myholder=new MyHolder(view);
        return myholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.MyHolder myHolder, int i) {

        MyContacts contact;
        contact=myContactsList.get(i);
        final String id=contact.getContactName();
        Log.d("contents",id);
        myHolder.dp.setImageResource(R.drawable.round_corner);
        myHolder.name.setText(contact.getContactName());
        myHolder.msg.setText(contact.getMsg().getMsgData().getText());
        myHolder.time.setText(contact.getMsg().getTime().toString());
        myHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MainActivity.class);
                intent.putExtra("name",id);
                context.startActivity(intent);


            }
        });
        myHolder.dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, VideoViewer.class);
                intent.putExtra("imageId",id+"");
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return myContactsList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView dp;
        TextView name;
        TextView msg;
        TextView time;
        RelativeLayout itemLayout;
        public MyHolder(View contactsView){
            super(contactsView);

            dp=contactsView.findViewById(R.id.dp);
            name=contactsView.findViewById(R.id.name);
            msg=contactsView.findViewById(R.id.Lmsg);
            time=contactsView.findViewById(R.id.LmTime);
            itemLayout=contactsView.findViewById(R.id.rLay);


        }

    }
}
