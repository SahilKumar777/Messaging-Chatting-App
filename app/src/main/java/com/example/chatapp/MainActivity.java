package com.example.chatapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

/*import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;*/

import java.security.Permission;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    String TAG="MainAct";

    ListView listView;
    MessageAdapter adapter;
    String type;
    List<Message> listM=new ArrayList<Message>();
    EditText editText;
    ImageView sendB,cameraB,attachFileB,scrollIcon;
    final private String[] CAMERA_PERMISSION={"android.permission.CAMERA"};
    final private int CAMERA_PERMISSION_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                       // String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getCharSequenceExtra("name"));
        toolbar.setSubtitle("Online");
        setSupportActionBar(toolbar);

        initList();
        listView=findViewById(R.id.message_view);
        adapter=new MessageAdapter(getApplicationContext(),listM,type);
        listView.setAdapter(adapter);

        View v=getLayoutInflater().inflate(R.layout.others_next_message,null);
        TextView t=v.findViewById(R.id.msg_body);
        t.setText("Today");
        TextView t1=v.findViewById(R.id.msg_time);
        t1.setText("");
        v.setBackgroundColor(Color.CYAN);

        listView.addHeaderView(v);




        editText=findViewById(R.id.editText);
        sendB=findViewById(R.id.sendB);
        cameraB=findViewById(R.id.cameraB);
        attachFileB=findViewById(R.id.filesB);
        scrollIcon=findViewById(R.id.downScrollIcon);


        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sendB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message=editText.getText().toString();
                if (message.length()>0){
                    sendMessage(message);
                }

            }
        });

        cameraB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,CAMERA_PERMISSION,1);
                }
                else{
                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,0);
                }

            }
        });

        attachFileB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,CAMERA_PERMISSION,2);
                }
                else {
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(intent, 1);
                }
            }
        });


        scrollIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.smoothScrollToPosition(listM.size());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_chat_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView =
                (SearchView) searchItem.getActionView();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mutenote :Toast.makeText(MainActivity.this,"muted", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Refresh :
                adapter.notifyDataSetChanged();
                listView.setSelection(listM.size()-1);
                Toast.makeText(MainActivity.this,"refreshed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.materials :Toast.makeText(MainActivity.this,"no materials", Toast.LENGTH_SHORT).show();
                break;
            case R.id.grInfo :Toast.makeText(MainActivity.this,"no info", Toast.LENGTH_SHORT).show();
                break;
            case R.id.clearChat :Toast.makeText(MainActivity.this,"clear", Toast.LENGTH_SHORT).show();
                                 dialogClearChat();
                break;
            case R.id.muteNotific :Toast.makeText(MainActivity.this,"bhbj", Toast.LENGTH_SHORT).show();
                break;
            case R.id.block :Toast.makeText(MainActivity.this,"blocked", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,0);
                } else {

                    Toast.makeText(this,"Camera permission denied",Toast.LENGTH_SHORT).show();

                }
                return;
            }
            case 2: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(intent,1);
                } else {

                    Toast.makeText(this,"Camera permission denied",Toast.LENGTH_SHORT).show();

                }
                return;
            }


        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0)
        {
            Bitmap bp = (Bitmap) data.getExtras().get("data");
        }
        else{
            if (requestCode==1){

            }
        }




    }

    public void sendMessage(String message){

        //send message
        Message m=new Message(true,new Time(new Long(102030)),1);
        m.setMemberData("Sahil Kumar","");
        m.setMsgData("T",message);
        adapter.add(m);
        listView.setSelection(listM.size()-1);
        editText.getText().clear();


    }

    void initList(){
        Time time=new Time(new Long(102030));
        Message m=new Message(true,time,3);
        m.setMemberData("Sahil Kumar","");
        m.setMsgData("T","njknvuado");
        listM.add(m);

        Message m1=new Message(false,time,2);
        m1.setMemberData("Anuj","");
        m1.setMsgData("T","Prepare for test today will be test for all the lessons teached in class");
        listM.add(m1);

        Message m11=new Message(false,time,2);
        m11.setMemberData("Anuj","");
        m11.setMsgData("T","All the Best Students");
        listM.add(m11);

        Message m2=new Message(false,time,2);
        m2.setMemberData("Bhaiu ","");
        m2.setMsgData("I","https://static.pexels.com/photos/6851/man-love-woman-kiss-medium.jpg");
        listM.add(m2);

        Message m3=new Message(true,time,2);
        m3.setMemberData("Sahil Kumar","");
        m3.setMsgData("T","not getting it right");
        listM.add(m3);

        Message m4=new Message(false,time,1);
        m4.setMemberData("Sirji","");
        m4.setMsgData("T","Help me out for todays test program");
        listM.add(m4);

        Message m5=new Message(true,time,0);
        m5.setMemberData("Sahil Kumar","");
        m5.setMsgData("T","Repeat agan the exercises");
        listM.add(m5);


    }

    public void dialogClearChat(){
        final Dialog dialogCustom=new Dialog(MainActivity.this);
        dialogCustom.setContentView(R.layout.dialog_layout);
        dialogCustom.setCancelable(true);
        TextView heading=dialogCustom.findViewById(R.id.heading);
        TextView T1=dialogCustom.findViewById(R.id.text1);
        TextView T2=dialogCustom.findViewById(R.id.text2);
        //TextView T3=dialog.findViewById(R.id.text3);

        heading.setText("Your all chat with this friend will be Deleted");
        T1.setText("Delete");
        T2.setText("Cancel");
        //T3.setText("");

        T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirm")
                        .setCancelable(false)
                        .setMessage("Do you really want to clear this chat")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,"Chat Cleared", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNeutralButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialogCustom.show();
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,"Cancelled", Toast.LENGTH_SHORT).show();
                                dialog.cancel();


                            }
                        });
                Dialog dialog1=builder.create();
                dialog1.show();
                dialogCustom.cancel();

            }
        });

        T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Cancelled", Toast.LENGTH_SHORT).show();
                dialogCustom.cancel();
            }
        });
        dialogCustom.show();


    }

}
