package com.example.videotophotoclone.Controler;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.videotophotoclone.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends BaseAdapter {
    List<File> music;
    Context mContext;
    ArrayList<String> data;
    public MusicAdapter(List<File> music, Context mContext,ArrayList<String> data) {
        this.music = music;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {
        return music.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_music,parent,false);
        TextView txtNameMusic,txtCapactyMusic,txtTimeMusic;
        final String path = music.get(position).getAbsolutePath();
        File file =new File(path);
        MediaPlayer mp =new MediaPlayer();
        try {
            mp.setDataSource(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        txtNameMusic = view.findViewById(R.id.txtNameMusic);
        txtCapactyMusic = view.findViewById(R.id.txtCapacityMusic);
        txtTimeMusic = view.findViewById(R.id.txtTimeMusic);
        txtNameMusic.setText(file.getName());
        txtTimeMusic.setText(MilisecondsToTimer(mp.getDuration()/1000));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("MUSICPATH",path);
                bundle.putStringArrayList("IMAGESELECTED",data);
                NavController nav = Navigation.findNavController(v);
                nav.navigate(R.id.action_musicList_to_createVideo,bundle);
            }
        });
        return view;
    }

    String MilisecondsToTimer(long milisec) {
        String finalTimerString = "";
        String secondString;
        String minuteString;
        int seconds = (int) milisec % 60;
        int minutes = seconds % 60;
        int hours = (int) milisec / (60 * 60);
        if (hours > 0) {
            finalTimerString = hours + ":";
        }
        if (seconds < 10) {
            secondString = "0" + seconds;
        } else secondString = "" + seconds;
        if (minutes < 10) {
            minuteString = "0" + minutes;
        } else minuteString = "" + minutes;
        finalTimerString = finalTimerString + minuteString + ":" + secondString;
        return finalTimerString;
    }
}
