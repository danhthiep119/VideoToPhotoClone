package com.example.videotophotoclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class ListFolderAdapter extends BaseAdapter {
    List<File> listFile;
    Context mContext;

    public ListFolderAdapter(List<File> listFile, Context mContext) {
        this.listFile = listFile;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return listFile.size();
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.folder_adapter,parent,false);
        ImageView imgFolder = view.findViewById(R.id.imgFolder);
        TextView textView = view.findViewById(R.id.txtNameFolder);
        textView.setText(listFile.get(position).getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,""+listFile.get(position).getAbsolutePath(),Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
