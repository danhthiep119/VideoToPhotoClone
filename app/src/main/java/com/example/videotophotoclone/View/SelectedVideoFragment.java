package com.example.videotophotoclone.View;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.videotophotoclone.Controler.ListVideoAdapter;
import com.example.videotophotoclone.R;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectedVideoFragment extends Fragment {
    List<File> videoFile = new ArrayList<>();
    public SelectedVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selected_video, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridView gvListView = view.findViewById(R.id.gvListVideo);
        String folderPath = getArguments().getString("PATH");
        File file = new File(folderPath);
        file = new File(file.getPath());
        videoFile = getVideoByPath(file);
        gvListView.setAdapter(new ListVideoAdapter(videoFile,getContext()));
    }

    ArrayList<File> getVideoByPath(File file){
        ArrayList<File> temp =new ArrayList<>();
        File[]  files = file.listFiles();
        for(File f :files){
            if(f.getName().endsWith(".mp4")){
                temp.add(f);
            }
        }
        return temp;
    }
}
