package com.example.videotophotoclone.Controler;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videotophotoclone.R;
import com.example.videotophotoclone.View.OptionDialog;

import java.io.File;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.DataBinding> {
    List<File> videoList;
    Context mContext;
    TextView txtVideoName, txtCapacity, txtTime;
    VideoView vdVideo;
    public int function;
    final String TAG = "Video Adapter";
    String path = "";

    public VideoAdapter(List<File> videoList, Context mContext) {
        this.videoList = videoList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DataBinding onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false);
        return new DataBinding(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataBinding holder, final int position) {

        Uri uri = Uri.parse(videoList.get(position).getAbsolutePath());
        vdVideo.setVideoURI(uri);
        txtVideoName.setText(videoList.get(position).getName());
        txtCapacity.setText(MilisecondsToTimer(vdVideo.getDuration() / 1000));
        txtTime.setText("");
        vdVideo.seekTo(1000);
        //Nhận sự kiện NSD click vào item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionDialog dialog = new OptionDialog(mContext, videoList.get(position).getAbsolutePath(),position,v);
                dialog.show();
                try {
                    if (!path.isEmpty()) {
                    }
                } catch (Exception e) {
                    Log.w(TAG, "" + e);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return videoList.isEmpty() ? 0 : videoList.size();
    }

    public class DataBinding extends RecyclerView.ViewHolder {
        public DataBinding(@NonNull View itemView) {
            super(itemView);
            vdVideo = itemView.findViewById(R.id.vdVideo);
            txtVideoName = itemView.findViewById(R.id.txtVideoName);
            txtCapacity = itemView.findViewById(R.id.txtCapacity);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
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
