package com.example.videotophotoclone.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.videotophotoclone.Controler.ListFolderAdapter;
import com.example.videotophotoclone.R;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectedFragment extends Fragment {
    List<String> listFile = new ArrayList<String>();
    List<File> dir = new ArrayList<>();
    ListFolderAdapter adapter;
    GridView gvFolder;
    final String TAG = "VIDEO FRAGMENT";

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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gvFolder = view.findViewById(R.id.gvFolder);
        //Duyệt danh sách file trong bộ nhớ
        ArrayList<File> myVideo = ListDir(Environment.getExternalStorageDirectory());
        for (int i=0;i<dir.size()-1;i++){
            for (int j=1;j<dir.size();j++){
                if (dir.get(i).getName().equals(dir.get(j).getName())){
                    dir.remove(j);
                }
            }
        }
        adapter = new ListFolderAdapter(dir, getContext());
        gvFolder.setAdapter(adapter);
    }

    public ArrayList<File> ListDir(File f) {

        ArrayList<File> temp = new ArrayList<>();
        File[] files = f.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                temp.addAll(ListDir(file));
            }
            if (file.getName().endsWith(".mp4")) {
                dir.add(new File(file.getParent()));
                temp.add(file);
            }
        }
        return temp;
    }
}
