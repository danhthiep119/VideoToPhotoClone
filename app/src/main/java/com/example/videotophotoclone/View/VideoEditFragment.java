package com.example.videotophotoclone;

import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;

import wseemann.media.FFmpegMediaMetadataRetriever;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoEditFragment extends Fragment {
    SeekBar seekBar;
    TextView txtNameVideo,txtCurrentTime,txtEndTime;
    VideoView vdView;
    ImageButton imgControlsVideo;
    boolean isplayed=false;
    public VideoEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        seekBar = view.findViewById(R.id.seek_bar_video);
        txtNameVideo = view.findViewById(R.id.txtNameVideo);
        txtCurrentTime = view.findViewById(R.id.txtCurrentTime);
        txtEndTime = view.findViewById(R.id.txtEndTime);
        vdView = view.findViewById(R.id.vdView);
        imgControlsVideo = view.findViewById(R.id.imgControlsVideo);
        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getData() throws IOException {
        String videoPath = getArguments().getString("VIDEOPATH");
        Uri uri=Uri.parse(videoPath);
        vdView.setVideoURI(uri);
//        MediaPlayer mp =new MediaPlayer();
//        try {
//            mp.reset();
//            mp.setDataSource(videoPath);
//            mp.prepare();
//        }
//        catch (IOException e){
//
//        }
//        txtCurrentTime.setText(MilisecondsToTimer(mp.getCurrentPosition()/1000));
//        txtEndTime.setText(MilisecondsToTimer(mp.getDuration()/1000));
        imgControlsVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo(isplayed);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void playVideo(boolean isPlayed) {
        if(isPlayed){
            vdView.start();
            isplayed = false;
            imgControlsVideo.setEnabled(true);
        }
        else  {
            vdView.pause();
            isPlayed=true;
            imgControlsVideo.setEnabled(false);
        }
    }

    String MilisecondsToTimer(long milisec){
        String finalTimerString = "";
        String secondString;
        int seconds = (int) milisec %(60*60)%60/60;
        int minutes = (int) milisec%(60*60)/60;
        int hours = (int) milisec /(60*60);
        if(hours>0){
            finalTimerString = hours+":";
        }
        if(milisec<10)
        {
            secondString = "0"+seconds;
        }
        else  secondString = ""+seconds;
        finalTimerString = finalTimerString + minutes+":"+secondString;
        return finalTimerString;
    }
}
