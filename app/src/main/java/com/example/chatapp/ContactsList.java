package com.example.chatapp;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactsList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        Toolbar toolbar=findViewById(R.id.mainBar);
        setSupportActionBar(toolbar);

        ViewPager viewPager=findViewById(R.id.viewPager);
        ChatAppPagerFragmentAdapter ad=new ChatAppPagerFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(ad);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh : Toast.makeText(ContactsList.this,"Refreshed",Toast.LENGTH_SHORT).show();
                break;
            case R.id.search : Toast.makeText(ContactsList.this,"Not Found",Toast.LENGTH_SHORT).show();
                break;
            case R.id.addContact : Toast.makeText(ContactsList.this,"invalid",Toast.LENGTH_SHORT).show();
                break;
            case R.id.hide : Toast.makeText(ContactsList.this,"not found",Toast.LENGTH_SHORT).show();
                break;
            case R.id.block : Toast.makeText(ContactsList.this,"blocked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting : Toast.makeText(ContactsList.this,"opens setting",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
