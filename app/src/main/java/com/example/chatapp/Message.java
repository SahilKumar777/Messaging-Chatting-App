package com.example.chatapp;

import android.media.Image;
import android.widget.ImageView;

import java.sql.Time;

public class Message {
    private MsgData msgData;
    private MemberData memberData;
    private boolean belongsToCurrentUser;
    private Time time;
    private int tick;


    public Message(boolean belongsToCurrentUser, Time time, int tick) {
        this.belongsToCurrentUser = belongsToCurrentUser;
        this.time = time;
        this.tick = tick;
    }

    public void setMsgData(String type,String text) {
        this.msgData =new MsgData(type,text);
    }

    public void setMemberData(String name,String image) {
        this.memberData = new MemberData(name,image);
    }

    public MsgData getMsgData() {
        return msgData;
    }

    public MemberData getMemberData() {
        return memberData;
    }

    public boolean isBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }

    public Time getTime() {
        return time;
    }

    public int getTick() {
        return tick;
    }


    //message data class
    //
 public  class MsgData {
        private String type;
        private String text;


        public MsgData(String type, String text) {
            this.type = type;
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public String getText() {
            return text;
        }
    }

    //member data class
    //
   public class MemberData {
        private String name;
        private String image;

        public MemberData(String name,String image) {
            this.name = name;
            this.image = image;
        }

        // Add an empty constructor so we can later parse JSON into MemberData using Jackson
        public MemberData() {
        }

        public String getName() {
            return name;
        }

        public String getImage() {
            return image;
        }
    }
}
