package com.example.chatapp;

import java.util.List;

public class ChatInfo {
    private String name;
    private String lastSeen;
    private String type;
    private String profilePic;
    private List<Message> msgList;


    public void setName(String name) {
        this.name = name;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setMsgList(List<Message> msgList) {
        this.msgList = msgList;
    }


    public String getName() {
        return name;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getType() {
        return type;
    }

    public List<Message> getMsgList() {
        return msgList;
    }
    public void addMsg(Message msg){
        this.msgList.add(msg);
    }
}
