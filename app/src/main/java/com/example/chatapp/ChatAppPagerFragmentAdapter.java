package com.example.chatapp;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ChatAppPagerFragmentAdapter extends FragmentStatePagerAdapter {

    public ChatAppPagerFragmentAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment tabFragment=new TabFragment();
        Bundle arg=new Bundle();
        arg.putInt("Number",i);
        tabFragment.setArguments(arg);
        return tabFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence tabTitle="Title";
        switch (position){
            case 0:tabTitle="Contacts";
                break;
            case 1:tabTitle="Status";
                break;
            case 2:tabTitle="Calls";
                break;
        }
        return tabTitle;
    }
}
