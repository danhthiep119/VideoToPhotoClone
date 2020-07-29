package com.example.videotophotoclone.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.videotophotoclone.Controler.ImageAdapter;
import com.example.videotophotoclone.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageListFragment extends Fragment {
    List<File> imageList = new ArrayList<>();
    String path;
    ImageAdapter adapter;


    public ImageListFragment() {
        // Required empty public constructor
//        this.path=path;
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
//        adapter = new ImageAdapter(R.id.action_imageListFragment_to_editPhotoFragment2);
        adapter = new ImageAdapter(imageList,getContext(),R.id.action_imageListFragment_to_editPhotoFragment2);
        gvListImage.setAdapter(adapter);
    }


    private void readImageFromExternalStorage() {
        imageList.clear();
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/ScreenShots");
        if(!file.exists()){
            file.mkdirs();
        }
        File[] files = file.listFiles();
        for(File f :files){
            if(f.getName().endsWith(".jpg")||f.getName().endsWith(".png")){
                imageList.add(f);
            }
        }
    }
}
