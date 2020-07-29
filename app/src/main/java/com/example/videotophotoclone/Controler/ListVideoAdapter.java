package com.example.videotophotoclone.Controler;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.videotophotoclone.R;

import java.io.File;
import java.net.URI;
import java.util.List;

public class ListVideoAdapter extends BaseAdapter {
    List<File> videoList;
    Context mContext;

    public ListVideoAdapter(List<File> videoList, Context mContext) {
        this.videoList = videoList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return videoList.isEmpty()?0:videoList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_video_adapter,parent,false);
        VideoView vdThumpnail=view.findViewById(R.id.vdThumpnail);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        txtTitle.setText(videoList.get(position).getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = videoList.get(position).getAbsolutePath();
                Bundle bunn = new Bundle();
                bunn.putString("VIDEOPATH",path);
                NavController nav= Navigation.findNavController(v);
                nav.navigate(R.id.action_selectedVideoFragment_to_tabVideoFragment,bunn);
            }
        });
        Uri uri = Uri.parse(videoList.get(position).getAbsolutePath());
        vdThumpnail.setVideoURI(uri);
        vdThumpnail.seekTo(10000);
        return view;
    }
}
