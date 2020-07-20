package com.example.videotophotoclone.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.videotophotoclone.Controler.VideoViewPager;
import com.example.videotophotoclone.R;
import com.example.videotophotoclone.View.TimeCaptureVideo;
import com.example.videotophotoclone.View.VideoEditFragment;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabVideoFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager video_fragment;
    TabItem tab_time,tab_quick;
    String path="";
    public TabVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tab_layout_video);
        path = getArguments().getString("VIDEOPATH");
        video_fragment = view.findViewById(R.id.videoFragment);
        VideoViewPager adapter = new VideoViewPager(getChildFragmentManager(),getContext());
        adapter.AddFragment(new VideoEditFragment(path));
        adapter.AddFragment(new TimeCaptureVideo(path));
        video_fragment.setAdapter(adapter);
        tabLayout.setupWithViewPager(video_fragment);
    }
}
