package com.example.chatapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends BaseAdapter {

    List<Message> messages = new ArrayList<Message>();
    Context context;
    String type;

    public MessageAdapter(Context context, List<Message> m,String type) {
        this.context = context;
        this.messages = m;
        this.type=type;
    }

    public void add(Message message) {
        this.messages.add(message);
        notifyDataSetChanged(); // to render the list we need to notify
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    // This is the backbone of the class, it handles the creation of single ListView row (chat bubble)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final MessageViewHolder holder = new MessageViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message = messages.get(i);
        int j=0;
        if(i>0){
            j=i-1;
        }

        Message messagePrev = messages.get(j);
        Boolean isSuccessiveMsg = false;
        if(type=="P"){
            isSuccessiveMsg=true;
        }else {
            if (message.getMemberData().getName().equalsIgnoreCase(messagePrev.getMemberData().getName())&& i!=0) {

                if (checkTime(message.getTime().toString(), messagePrev.getTime().toString())) {
                      isSuccessiveMsg = true;
                 }
            }

        }

        switch (message.getMsgData().getType()){
            case "T" : if (message.isBelongsToCurrentUser()) {
                convertView = messageInflater.inflate(R.layout.my_message, null);
                holder.msgTick = (ImageView) convertView.findViewById(R.id.tick);
                convertView.setTag(holder);
                switch (message.getTick()) {
                    case 0:
                        holder.msgTick.setImageResource(R.drawable.ic_update_black_24dp);
                        break;
                    case 1:
                        holder.msgTick.setImageResource(R.drawable.ic_done_black_24dp);
                        break;
                    case 2:
                        holder.msgTick.setImageResource(R.drawable.ic_done_all_black_24dp);
                        break;
                    case 3:
                        holder.msgTick.setImageResource(R.drawable.ic_done_all_red_24dp);
                        break;

                    default:
                        holder.msgTick.setImageResource(R.drawable.ic_update_black_24dp);
                }

            } else {
                if (isSuccessiveMsg) {
                    convertView = messageInflater.inflate(R.layout.others_next_message, null);
                    convertView.setTag(holder);
                } else {
                    convertView = messageInflater.inflate(R.layout.others_message, null);
                    holder.profileImg = (ImageView) convertView.findViewById(R.id.profile_pic);
                    holder.name = (TextView) convertView.findViewById(R.id.uName);
                    convertView.setTag(holder);
                    //Bitmap bmp=new Bitmap(message.getMemberData().getImage());
                    holder.profileImg.setBackgroundColor(Color.YELLOW);
                    holder.name.setText(message.getMemberData().getName());
                }

            }
                holder.msgBody = (TextView) convertView.findViewById(R.id.msg_body);
                holder.msgTime = (TextView) convertView.findViewById(R.id.msg_time);
                holder.msgBody.setText(message.getMsgData().getText());
                holder.msgTime.setText(msgTimeFormat(message.getTime().toString()));
                       break;
            case "V" :
                case "I" : if (message.isBelongsToCurrentUser()) {
                    convertView = messageInflater.inflate(R.layout.my_msg_graphic, null);
                    /*holder.msgGraphic =convertView.findViewById(R.id.imageView);
                    holder.msgTime = (TextView) convertView.findViewById(R.id.msg_time);*/
                    holder.msgTick = (ImageView) convertView.findViewById(R.id.tick);
                    convertView.setTag(holder);

                    switch (message.getTick()) {
                        case 0:
                            holder.msgTick.setImageResource(R.drawable.ic_update_black_24dp);
                            break;
                        case 1:
                            holder.msgTick.setImageResource(R.drawable.ic_done_black_24dp);
                            break;
                        case 2:
                            holder.msgTick.setImageResource(R.drawable.ic_done_all_black_24dp);
                            break;
                        case 3:
                            holder.msgTick.setImageResource(R.drawable.ic_done_all_red_24dp);
                            break;

                        default:
                            holder.msgTick.setImageResource(R.drawable.ic_update_black_24dp);
                    }

                }else {
                    if (isSuccessiveMsg) {
                        convertView = messageInflater.inflate(R.layout.other_next_msg_graphics, null);
                        /*holder.msgGraphic = convertView.findViewById(R.id.imageView);
                        holder.msgTime = (TextView) convertView.findViewById(R.id.msg_time);*/
                        convertView.setTag(holder);
                    } else {
                        convertView = messageInflater.inflate(R.layout.other_msg_graphics, null);
                        holder.profileImg = (ImageView) convertView.findViewById(R.id.profile_pic);
                        holder.name = (TextView) convertView.findViewById(R.id.uName);
                        /*holder.msgGraphic = convertView.findViewById(R.id.imageView);
                        holder.msgTime = (TextView) convertView.findViewById(R.id.msg_time);*/
                        convertView.setTag(holder);
                        //Bitmap bmp=new Bitmap(message.getMemberData().getImage());
                        holder.profileImg.setBackgroundColor(Color.YELLOW);
                        holder.name.setText(message.getMemberData().getName());
                    }

                }

                    holder.msgGraphic = convertView.findViewById(R.id.imageView);
                    holder.msgTime = (TextView) convertView.findViewById(R.id.msg_time);
                    holder.downloadGraphics=convertView.findViewById(R.id.downloadB);
                    holder.stopDownloadGraphics=convertView.findViewById(R.id.cancelUpload);
                    holder.progressbar=convertView.findViewById(R.id.progress);

                    Uri uri=Uri.parse(message.getMsgData().getText());
                    holder.msgGraphic.setImageURI(uri);
                    holder.msgGraphic.setBackgroundColor(Color.YELLOW);
                    holder.msgTime.setText(msgTimeFormat(message.getTime().toString()));

                    holder.downloadGraphics.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holder.downloadGraphics.setVisibility(View.GONE);
                            holder.stopDownloadGraphics.setVisibility(View.VISIBLE);
                            holder.progressbar.setVisibility(View.VISIBLE);
                        }
                    });
                    holder.stopDownloadGraphics.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holder.downloadGraphics.setVisibility(View.VISIBLE);
                            holder.stopDownloadGraphics.setVisibility(View.GONE);
                            holder.progressbar.setVisibility(View.GONE);
                        }
                    });
                break;

        }


        return convertView;
    }

    //message view holder
    //
    class MessageViewHolder {
        public ImageView profileImg;
        public ImageView msgGraphic;
        public TextView name;
        public TextView msgBody;
        public TextView msgTime;
        public ImageView msgTick;
        public ImageView downloadGraphics;
        public ImageView stopDownloadGraphics;
        public ProgressBar progressbar;
    }


    private boolean checkTime(String t1, String t2) {

        int hour1 = 0;
        int hour2 = 0;
        int minute1 = 0;
        int minute2 = 1;
        int firstColon;
        int secondColon;

        firstColon = t1.indexOf(':');
        secondColon = t1.indexOf(':', firstColon + 1);
        if ((firstColon > 0) & (secondColon > 0) &
                (secondColon < t1.length() - 1)) {
            hour1 = Integer.parseInt(t1.substring(0, firstColon));
            minute1 =
                    Integer.parseInt(t1.substring(firstColon + 1, secondColon));
        }

        firstColon = t2.indexOf(':');
        secondColon = t2.indexOf(':', firstColon + 1);
        if ((firstColon > 0) & (secondColon > 0) &
                (secondColon < t2.length() - 1)) {
            hour2 = Integer.parseInt(t2.substring(0, firstColon));
            minute2 =
                    Integer.parseInt(t2.substring(firstColon + 1, secondColon));
        }

        if ((minute1 <= minute2 + 1) & (hour1 == hour2))
            return true;
        else
            return false;

    }

    private String msgTimeFormat(String s) {
        int firstColon;
        int secondColon;
        String sub = "";
        firstColon = s.indexOf(':');
        secondColon = s.indexOf(':', firstColon + 1);
        if ((firstColon > 0) & (secondColon > 0) &
                (secondColon < s.length() - 1)) {
            sub = s.substring(0, secondColon);

        }
        return sub;
    }
}
