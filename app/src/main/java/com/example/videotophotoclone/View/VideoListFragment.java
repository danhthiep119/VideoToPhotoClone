package com.example.videotophotoclone.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.videotophotoclone.Controler.VideoAdapter;
import com.example.videotophotoclone.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoListFragment extends Fragment {
    List<File> videoList=new ArrayList<>();
    String path;
    RecyclerView lvVideoList;
    public VideoAdapter adapter;
    public VideoListFragment(String path) {
        // Required empty public constructor
        this.path = path;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvVideoList=view.findViewById(R.id.lvVideoList);
        videoList.clear();
        File file = new File(path);
        File[] files = file.listFiles();
        for (File f:files){
            if(f.getName().endsWith(".mp4")){
                videoList.add(f);
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        lvVideoList.setLayoutManager(linearLayoutManager);
        lvVideoList.setHasFixedSize(true);
        adapter = new VideoAdapter(videoList,getContext());
        lvVideoList.setAdapter(adapter);

    }
}
