package com.example.videotophotoclone.Controler;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.videotophotoclone.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryViewPager extends FragmentPagerAdapter {

    public List<Fragment> fragmentlist= new ArrayList<>();
    public List<String> titleList = new ArrayList<>();
    int[] title = {R.string.Videos,R.string.Images};
    Context mContext;
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        for (int i=0;i<title.length;i++){
            titleList.add(mContext.getResources().getString(title[i]));
        }
        return titleList.get(position);
    }

    public GalleryViewPager(@NonNull FragmentManager fm,Context context) {
        super(fm);
        this.mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return fragmentlist.size();
    }

    public void AddFragment(Fragment fragment){
        fragmentlist.add(fragment);
    }
}
