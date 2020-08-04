package com.example.videotophotoclone.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.videotophotoclone.Controler.ImageAdapter;
import com.example.videotophotoclone.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {
    List<File> imageList = new ArrayList<>();
    String path = "";
    ImageAdapter adapter;
    public ImageButton btnDel,btnReload,btnShare;
    public LinearLayout SelectedZone;
    public TextView txtNumSelected;
    private GridView gvImageList;
    static int index;
    public ImageFragment(String path) {
        // Required empty public constructor
        this.path = path;
    }

    @Override
    public void onResume() {
        gvImageList.setSelection(index);
        super.onResume();
    }

    @Override
    public void onPause() {
        index = gvImageList.getVisibility();
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageList.clear();
        gvImageList = view.findViewById(R.id.gvListImage);
        btnDel = view.findViewById(R.id.btnDel);
        btnReload = view.findViewById(R.id.btnReload);
        btnShare = view.findViewById(R.id.btnShare);
        SelectedZone = view.findViewById(R.id.SelectedZone);
        txtNumSelected = view.findViewById(R.id.txtNumSelected);
        SelectedZone.setVisibility(View.INVISIBLE);
        File file = new File(path);
        File[] files = file.listFiles();
        for(File f :files){
            if(f.getName().endsWith(".jpg")||f.getName().endsWith(".png")){
                imageList.add(f);
            }
        }
        adapter=new ImageAdapter(imageList,getContext(),R.id.action_galleryFragment_to_editPhotoFragment2,false,false,this);
        gvImageList.setAdapter(adapter);
    }
}
