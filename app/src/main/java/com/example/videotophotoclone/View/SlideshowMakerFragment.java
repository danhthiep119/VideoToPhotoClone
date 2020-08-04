package com.example.videotophotoclone.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.videotophotoclone.Controler.FolderRecycleView;
import com.example.videotophotoclone.Controler.GetImageAdapter;
import com.example.videotophotoclone.Controler.ImageAdapter;
import com.example.videotophotoclone.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SlideshowMakerFragment extends Fragment {
    List<File> folderList = new ArrayList<>();
    static List<File> imageList = new ArrayList<>();
    public List<File> imageSelected = new ArrayList<>();
    Button btnClear, btnCreate;
    GridView GvImage, GvSelected;
    RecyclerView GvFolder;
    FolderRecycleView adapterRecycleView;
    ImageAdapter adapter;
    final String TAG = "Slide Show Maker";

    public SlideshowMakerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slideshow_maker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GvFolder = view.findViewById(R.id.GvFolder);
        GvImage = view.findViewById(R.id.GvImage);
        GvSelected = view.findViewById(R.id.GvSelected);
        btnClear = view.findViewById(R.id.btnClear);
        btnCreate = view.findViewById(R.id.btnCreate);
        File file = Environment.getExternalStorageDirectory();
        folderList.clear();
        try{
            getData();
        }
        catch (Exception e){

        }
        storingFolderExternal(file);
        try {
            getImage(imageList);
        } catch (Exception e) {
            Log.w(TAG, e);
        }
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageSelected.isEmpty()) {
                    imageSelected.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getData() {
        ArrayList<String> imageList = getArguments().getStringArrayList("IMAGESELECTED");
        for (String path:imageList){
            File file = new File(path);
            imageSelected.add(file);
        }
        selectedImage();
    }

    private void getImage(final List<File> imageList) {
        if (!imageList.isEmpty()) {
            GetImageAdapter adapter1;
            adapter1 = new GetImageAdapter(imageList, getContext(), this);
            GvImage.setAdapter(adapter1);
        }
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSlide(v);
            }
        });
    }

    private void createSlide(View v) {
        ArrayList<String> listPath = new ArrayList<>();
        for (File item : imageSelected) {
            listPath.add(item.getAbsolutePath());
        }
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("IMAGESELECTED", listPath);
        NavController nav = Navigation.findNavController(v);
        nav.navigate(R.id.action_slideshowMakerFragment_to_createVideo, bundle);
    }

    public void selectedImage() {
        adapter = new ImageAdapter(imageSelected, getContext(), 0,false,true,null);
        GvSelected.setAdapter(adapter);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageSelected.isEmpty()) {
                    imageSelected.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void getListImage(String path) {
        this.imageList.clear();
        File file = new File(path);
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.getName().endsWith(".jpg") || f.getName().endsWith(".png")) {
                imageList.add(f);
            }
        }
        getImage(imageList);
    }

    private void storingFolderExternal(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                storingFolderExternal(f);
            }
            if (f.getName().endsWith(".jpg") || f.getName().endsWith(".png")) {
                folderList.add(new File(f.getParent()));
            }
        }
        for (int i = 0; i < folderList.size() - 1; i++) {
            for (int j = i + 1; j < folderList.size(); j++) {
                if (folderList.get(i).equals(folderList.get(j))) {
                    folderList.remove(j);
                }
            }
        }
        adapterRecycleView = new FolderRecycleView(folderList, getContext(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        GvFolder.setLayoutManager(linearLayoutManager);
        GvFolder.setHasFixedSize(true);
        GvFolder.setAdapter(adapterRecycleView);
    }
}
