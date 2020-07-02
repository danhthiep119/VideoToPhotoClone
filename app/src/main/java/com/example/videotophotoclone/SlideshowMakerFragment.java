package com.example.videotophotoclone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SlideshowMakerFragment extends Fragment {
    List<String> folderList=new ArrayList<>();
    List<Integer> imageList = new ArrayList<Integer>();
    List<Integer> imageSelected = new ArrayList<Integer>();
    Button btnClear;
    GridView GvImage,GvSelected;
    RecyclerView GvFolder;
    FolderRecycleView adapterRecycleView;
    ImageAdapter adapter;
    public SlideshowMakerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slideshow_maker, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.item_slideshows,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GvFolder=view.findViewById(R.id.GvFolder);
        GvImage=view.findViewById(R.id.GvImage);
        GvSelected=view.findViewById(R.id.GvSelected);
        btnClear=view.findViewById(R.id.btnClear);
        storingFolderExternal();
        fakeData();
        imageSelected.clear();
        GvImage.setAdapter(new ImageAdapter(imageList,getContext()));
        GvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imageSelected.add(imageList.get(position));
                Toast.makeText(getContext(),"Bạn chọn "+imageList.get(position),Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new ImageAdapter(imageSelected,getContext());
        GvSelected.setAdapter(adapter);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelected.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }

    void fakeData(){
        imageList.clear();
        imageList.add(R.drawable.ic_brush_black_24dp);
        imageList.add(R.drawable.ic_info_black_24dp);
        imageList.add(R.drawable.ic_personal_video_black_75dp);
        imageList.add(R.drawable.ic_star_half_black_35dp);
        imageList.add(R.drawable.ic_picture_in_picture_black_75dp);
        imageList.add(R.drawable.ic_folder_black_24dp);
        imageList.add(R.drawable.ic_content_cut_black_35dp);
    }

    private void storingFolderExternal() {
        folderList.clear();
        File file=new File(Environment.getRootDirectory().getName());
        File[] files = file.listFiles();
        for(File f: files ){
            folderList.add(f.getName());
        }
        adapterRecycleView=new FolderRecycleView(folderList,getContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        GvFolder.setLayoutManager(linearLayoutManager);
        GvFolder.setHasFixedSize(true);
        GvFolder.setAdapter(adapterRecycleView);
    }
}
