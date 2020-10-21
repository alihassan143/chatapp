package com.example.chat;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chat.Fragments.ChatFragment;
import com.example.chat.Fragments.ProfileFragment;
import com.example.chat.Fragments.UsersFragment;

public class SectionPagerAdopter extends FragmentPagerAdapter {

    @SuppressLint("SupportAnnotationUsage")
    @StringRes
    String title[] = {"Chat","Users","Profile"};
    private final Context mContext;

    public SectionPagerAdopter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
        {
            return new ChatFragment();
        }else if(position == 1){
            return new UsersFragment();
        }else
            return new ProfileFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(Integer.parseInt(title[position]));
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}
