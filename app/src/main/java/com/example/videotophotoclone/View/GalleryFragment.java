package com.example.videotophotoclone.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.videotophotoclone.Controler.GalleryViewPager;
import com.example.videotophotoclone.R;
import com.example.videotophotoclone.Controler.VideoAdapter;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {
    public static List<File> videoList = new ArrayList<>();
    ViewPager viewGallery;
    TabLayout tabHost;
    final String TAG = "GalleryFragment";
    public VideoAdapter adapter;
    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabHost=view.findViewById(R.id.tabHost);
        viewGallery=view.findViewById(R.id.viewGallery);
        videoList.clear();
        GalleryViewPager adapter = new GalleryViewPager(getChildFragmentManager(),getContext());

        adapter.AddFragment(new VideoListFragment(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Videos"));
        adapter.AddFragment(new ImageFragment(Environment.getExternalStorageDirectory().getAbsolutePath()+"/ScreenShots"));
        viewGallery.setAdapter(adapter);
        tabHost.setupWithViewPager(viewGallery);
     }

    public void readVideoFromFile(File file) {
        if(!file.exists()){
            file.mkdirs();
        }
        File[] files = file.listFiles();
        for(File f:files){
            if(f.isDirectory()){
                readVideoFromFile(f.getAbsoluteFile());
            }
            if(f.getName().endsWith(".mp4")||f.getName().endsWith(".mkv")){
                videoList.add(f);
            }
        }
    }
}
