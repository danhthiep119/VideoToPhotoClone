package com.example.videotophotoclone.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.videotophotoclone.Controler.ImageAdapter;
import com.example.videotophotoclone.Model.Video;
import com.example.videotophotoclone.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageListFragment extends Fragment {
    List<File> imageList = new ArrayList<>();
    public ImageListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridView gvListImage = view.findViewById(R.id.gvListImage);
        readImageFromExternalStorage();
        gvListImage.setAdapter(new ImageAdapter(imageList,getContext()));

    }

    private void readImageFromExternalStorage() {
        imageList.clear();
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/ScreenShots");
        File[] files = file.listFiles();
        for(File f :files){
            if(f.getName().endsWith(".jpg")){
                imageList.add(f);
            }
        }
    }
}
