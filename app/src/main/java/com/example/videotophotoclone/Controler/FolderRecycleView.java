package com.example.videotophotoclone.Controler;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videotophotoclone.R;
import com.example.videotophotoclone.View.SlideshowMakerFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderRecycleView extends RecyclerView.Adapter<FolderRecycleView.DataViewHolder> {
    List<File> folderList;
    Context mContext;
    SlideshowMakerFragment fmSideShow;
    boolean isChecked = false;
    public FolderRecycleView(List<File> folderList, Context mContext, SlideshowMakerFragment _fmSideShow) {
        this.folderList = folderList;
        this.mContext = mContext;
        this.fmSideShow = _fmSideShow;
    }

    @NonNull
    @Override
    public FolderRecycleView.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.folder_adapter,parent,false);
            return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FolderRecycleView.DataViewHolder holder, final int position) {
        String path = folderList.get(position).getAbsolutePath();
        File file = new File(path);
        file = new File(file.getPath());
        int count = countFiles(file);
        if(!isChecked){
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.txtName.setTextColor(Color.parseColor("#000000"));
        }
        holder.txtName.setText(folderList.get(position).getName()+" ("+count+" images)");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChecked = true;
                if(isChecked){
                    Toast.makeText(mContext,"Bạn Mở Thư mục: "+ folderList.get(position).getName(),Toast.LENGTH_SHORT).show();
                    String path = folderList.get(position).getAbsolutePath();
                    fmSideShow.getListImage(path);
                }
            }
        });
    }

    private int countFiles(File file) {
        ArrayList<File> list= new ArrayList<>();
        File[] f=file.listFiles();
        for(File files:f){
            if(files.getName().endsWith(".jpg")||files.getName().endsWith(".png")){
                list.add(files);
            }
        }
        return list.size();
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtNameFolder);
        }
    }
}
