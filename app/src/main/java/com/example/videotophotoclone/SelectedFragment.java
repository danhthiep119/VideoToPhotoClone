package com.example.videotophotoclone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectedFragment extends Fragment {
    List<String> listFile = new ArrayList<String>();
    ListFolderAdapter adapter;
    GridView gvFolder;
    final String TAG="VIDEO FRAGMENT";
    public SelectedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selected, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gvFolder=view.findViewById(R.id.gvFolder);
        //Duyệt danh sách file trong bộ nhớ
        File root = new File(Environment.getRootDirectory().getName());
        try
        {
            ListDir(root);
        }
        catch (Exception e) {
            Log.e(TAG, ": " + e);
        }
    }

    public void ListDir(File f){
        listFile.clear();
        File[] files = f.listFiles();
        Log.w(TAG,"has :"+files.length+" files");
        for(File file:files){
            listFile.add(file.getName());
        }
        adapter = new ListFolderAdapter(listFile,getContext());
        gvFolder.setAdapter(adapter);
    }
}
