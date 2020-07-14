package com.example.videotophotoclone.View;

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

import com.example.videotophotoclone.Controler.FolderRecycleView;
import com.example.videotophotoclone.Controler.ImageAdapter;
import com.example.videotophotoclone.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SlideshowMakerFragment extends Fragment {
    List<File> folderList=new ArrayList<>();
    List<File> imageList = new ArrayList<>();
    List<File> imageSelected = new ArrayList<>();
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
        File file= Environment.getExternalStorageDirectory();
        folderList.clear();
        imageList.clear();
        storingFolderExternal(file);
        getListImage();
        imageSelected.clear();
        GvImage.setAdapter(new ImageAdapter(imageList,getContext()));
        GvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

    private void getListImage() {

    }


    private void storingFolderExternal(File file) {
        File[] files = file.listFiles();
        for(File f: files ){
            if(f.isDirectory())
            {
                storingFolderExternal(f);
            }
            if(f.getName().endsWith(".jpg")||f.getName().endsWith(".png")){
                folderList.add(new File(f.getParent()));
            }
        }
        for (int i=0;i<folderList.size()-1;i++){
            for (int j=1;j<folderList.size();j++){
                if (folderList.get(i).equals(folderList.get(j))){
                    folderList.remove(j);
                }
            }
        }
        adapterRecycleView=new FolderRecycleView(folderList,getContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        GvFolder.setLayoutManager(linearLayoutManager);
        GvFolder.setHasFixedSize(true);
        GvFolder.setAdapter(adapterRecycleView);
    }
}
