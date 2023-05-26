package com.example.chatapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ChatAppJSON {
    JSONObject json;
    Context context;


    public ChatAppJSON(String fileName, Context context){
        try {
            this.json =new JSONObject(fileName);
            this.context=context;
        }catch (JSONException e){

        }

    }
    /*public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void changeJSONFile(String filename){
        try {

        }catch (IOException ex) {
            ex.printStackTrace();

        }

    }*/


    /*try {
//to write file
	String FILENAME = "hello_file";
	String FOLDERNAME = "sub";
	String string = "hello world!";

	Context context = getApplicationContext();
	String folder = context.getFilesDir().getAbsolutePath() + File.separator + FOLDERNAME;

	File subFolder = new File(folder);

	if (!subFolder.exists()) {
		subFolder.mkdirs();
	}

	FileOutputStream outputStream = new FileOutputStream(new File(subFolder, FILENAME));

	outputStream.write(string.getBytes());
	outputStream.close();

} catch (FileNotFoundException e) {
	Log.e("ERROR", e.toString());
} catch (IOException e) {
	Log.e("ERROR", e.toString());
}*/

    /*try {
//to read file
	String FILENAME = "hello_file";
	String FOLDERNAME = "sub";
	byte[] bytes = new byte[1024];

	Context context = getApplicationContext();
	String folder = context.getFilesDir().getAbsolutePath() + File.separator + FOLDERNAME;

	File subFolder = new File(folder);

	FileInputStream outputStream = new FileInputStream(new File(subFolder, FILENAME));

	outputStream.read(bytes);
	outputStream.close();

	String string = new String(bytes);
	Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();

} catch (FileNotFoundException e) {
	Log.e("ERROR", e.toString());
} catch (IOException e) {
	Log.e(TAG, e.toString());
}*/

    public ChatInfo getChatData(String chatter){
        ChatInfo chatInfo=new ChatInfo();


        try {
            JSONArray jArr = json.getJSONArray("chats");
            JSONObject joChat= jArr.getJSONObject(idInArray(jArr,chatter));
            chatInfo.setName(joChat.getString("name"));
            chatInfo.setLastSeen(joChat.getString("lastSeen"));
            chatInfo.setType(joChat.getString("cType"));
            chatInfo.setProfilePic(joChat.getString("dp"));

            JSONArray jMsgArr=joChat.getJSONArray("messages");
            for(int i=0;i<jMsgArr.length();i++){
                JSONObject jMsg=jMsgArr.getJSONObject(i);
                Message msg=new Message(jMsg.getBoolean("isUser"),Time.valueOf(jMsg.getString("time")),jMsg.getInt("tick"));
                JSONObject jMsgData=jMsg.getJSONObject("msgData");
                msg.setMsgData(jMsgData.getString("mType"),jMsgData.getString("msg"));
                JSONObject jMember=jMsg.getJSONObject("memberData");
                msg.setMemberData(jMember.getString("name"),jMember.getString("image"));
                chatInfo.addMsg(msg);
            }

        }catch (JSONException e){

        }

        return chatInfo;
    }

    public List<MyContacts> getContactsList(String source){
        List<MyContacts> cList=new ArrayList<MyContacts>();
        try {
            JSONArray jArr = json.getJSONArray(source);
            for(int i=0;i<jArr.length();i++){
                JSONObject jContact=jArr.getJSONObject(i);
                JSONObject jMsg=jContact.getJSONObject("lMsg");
                Message msg=new Message(jMsg.getBoolean("isUser"),Time.valueOf(jMsg.getString("time")),jMsg.getInt("tick"));
                JSONObject jMsgData=jMsg.getJSONObject("msgData");
                msg.setMsgData(jMsgData.getString("mType"),jMsgData.getString("msg"));
                JSONObject jMember=jMsg.getJSONObject("memberData");
                msg.setMemberData(jMember.getString("name"),jMember.getString("image"));
                MyContacts contact=new MyContacts(jContact.getString("name"),jContact.getString("img"),jContact.getString("type"));
                contact.setMsg(msg);

                cList.add(contact);
            }

        }catch (JSONException e){

        }
        return cList;
    }

    public MyContacts getStatus(String name){
        MyContacts contact=new MyContacts();
        try {
            JSONArray jArr = json.getJSONArray("Status");
            JSONObject jContact= jArr.getJSONObject(idInArray(jArr,name));
            JSONObject jMsg=jContact.getJSONObject("lMsg");
            Message msg=new Message(jMsg.getBoolean("isUser"),Time.valueOf(jMsg.getString("time")),jMsg.getInt("tick"));
            JSONObject jMsgData=jMsg.getJSONObject("msgData");
            msg.setMsgData(jMsgData.getString("mType"),jMsgData.getString("msg"));
            JSONObject jMember=jMsg.getJSONObject("memberData");
            msg.setMemberData(jMember.getString("name"),jMember.getString("image"));
            MyContacts contact1=new MyContacts(jContact.getString("name"),jContact.getString("img"),jContact.getString("type"));
            contact1.setMsg(msg);
            contact=contact1;
        }catch (JSONException e){

        }
        return contact;
    }

    public String addMsg(Message msg){
        try {

            JSONObject jMsg=new JSONObject();
            JSONObject jmemberData=new JSONObject();
            JSONObject jmsgData=new JSONObject();
            jmemberData.put("name",msg.getMemberData().getName());
            jmemberData.put("image",msg.getMemberData().getImage());
            jmsgData.put("mType",msg.getMsgData().getType());
            jmsgData.put("msg",msg.getMsgData().getText());
            jMsg.put("isUser",msg.isBelongsToCurrentUser());
            jMsg.put("time",msg.getTime());
            jMsg.put("memberData",jmemberData);
            jMsg.put("msgData",jmsgData);


            JSONArray jArr = json.getJSONArray("chats");
            int id=idInArray(jArr,msg.getMemberData().getName());
            JSONObject joChat= jArr.getJSONObject(id);
            JSONArray jMsgArr=joChat.getJSONArray("messages");
            jMsgArr.put(jMsg);
            joChat.put("messages",jMsgArr);
            jArr.put(id,joChat);
            json.put("chats",jArr);
        }catch (JSONException e){
            e.printStackTrace();
        }

        return json.toString();
    }
    public String deleteMsg(String name,int[] A){

        try {
            JSONArray jArr = json.getJSONArray("chats");
            int id=idInArray(jArr,name);
            JSONObject joChat= jArr.getJSONObject(id);
            JSONArray jMsgArr=joChat.getJSONArray("messages");
            for (int i=0;i<A.length;i++) {
                jMsgArr.remove(A[i]);
            }
            joChat.put("messages",jMsgArr);
            jArr.put(id,joChat);
            json.put("chats",jArr);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return json.toString();
    }
    public String addStatus (Message msg){

        return null;
    }


    public int idInArray(JSONArray jArr,String chatter){
        int i=-1;
        try {
            for(i=0;i<jArr.length();i++){
                JSONObject joChat=jArr.getJSONObject(i);
                if(joChat.getString("name")==chatter){
                    break;
                }
            }


        }catch (JSONException e){

        }

        return i;
    }
}
