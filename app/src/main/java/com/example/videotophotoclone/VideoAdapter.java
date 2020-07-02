package com.example.videotophotoclone;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videotophotoclone.Model.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.DataBinding> {
    SendDataFromFragment listener;
    List<Video> videoList;
    Context mContext;
    TextView txtVideoName,txtCapacity,txtTime;
    VideoView vdVideo;
    final String TAG = "Video Adapter";
    public interface SendDataFromFragment{
        void onDataSending(CharSequence videoPath);
    }


    public VideoAdapter(List<Video> videoList, Context mContext) {
        this.videoList = videoList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DataBinding onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video,parent,false);
        return new DataBinding(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataBinding holder, final int position) {
        txtVideoName.setText(videoList.get(position).getTitle());
        txtCapacity.setText(videoList.get(position).getDuration());
        txtTime.setText(videoList.get(position).getTime());
        Uri uri = Uri.parse(videoList.get(position).getVideoPath());
        vdVideo.setVideoURI(uri);
        //Nhận sự kiện NSD click vào item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionDialog dialog = new OptionDialog(mContext);
                dialog.show();
                try{
                    Bundle bundle=new Bundle();
                    bundle.putString("VIDEOPATH",videoList.get(position).getVideoPath());
                    NavController nav= Navigation.findNavController(v);
                    nav.navigate(R.id.action_galleryFragment_to_showVideo,bundle);

                }
                catch (Exception e){
                    Log.w(TAG,""+e);
                }
            }
        });
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        try {
            if (recyclerView instanceof SendDataFromFragment) {
                listener = (SendDataFromFragment) recyclerView;
            }
        }
        catch (RuntimeException e){
            Log.w(TAG,""+e);

        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        listener=null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class DataBinding extends RecyclerView.ViewHolder{
        public DataBinding(@NonNull View itemView) {
            super(itemView);
            vdVideo=itemView.findViewById(R.id.vdVideo);
            txtVideoName=itemView.findViewById(R.id.txtVideoName);
            txtCapacity=itemView.findViewById(R.id.txtCapacity);
            txtTime=itemView.findViewById(R.id.txtTime);
        }
    }
}
