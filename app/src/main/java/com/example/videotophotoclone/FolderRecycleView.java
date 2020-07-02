package com.example.videotophotoclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FolderRecycleView extends RecyclerView.Adapter<FolderRecycleView.DataViewHolder> {
    List<String> folderList;
    Context mContext;

    public FolderRecycleView(List<String> folderList, Context mContext) {
        this.folderList = folderList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public FolderRecycleView.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.folder_adapter,parent,false);
            return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FolderRecycleView.DataViewHolder holder, final int position) {
        holder.txtName.setText(folderList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(000000);
                Toast.makeText(mContext,"Bạn Mở Thư mục: "+ folderList.get(position),Toast.LENGTH_SHORT).show();
            }
        });
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
