package com.example.videotophotoclone.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.videotophotoclone.Controler.MusicAdapter;
import com.example.videotophotoclone.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicList extends Fragment {
    List<File> musicList = new ArrayList<>();
    final String TAG = "MusicList";
//    ListView lvListMusic;

    public MusicList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView lvListMusic = view.findViewById(R.id.lvListMusic);
        musicList.clear();
        File file = Environment.getExternalStorageDirectory();
        readMusic(file);
        ArrayList<String> imageList = getArguments().getStringArrayList("IMAGESELECTED");
        MusicAdapter adapter = new MusicAdapter(musicList, getContext(), imageList);
        lvListMusic.setAdapter(adapter);
    }

    void readMusic(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                readMusic(f);
            }
            if (f.getName().endsWith(".mp3")) {
                musicList.add(f);
            }
        }
        Log.w(TAG, "external has " + musicList.size());
    }
}
