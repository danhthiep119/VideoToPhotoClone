package com.example.videotophotoclone.Controler;

import android.content.Context;
import android.os.Bundle;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videotophotoclone.R;

import java.io.File;
import java.util.ArrayList;
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
        String path = listFile.get(position).getAbsolutePath();
        File file = new File(path);
        file = new File(file.getPath());
        int count = countFiles(file);
        textView.setText(listFile.get(position).getName()+" ("+count+" videos)");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,""+listFile.get(position).getAbsolutePath(),Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("PATH",listFile.get(position).getAbsolutePath());
                NavController nav = Navigation.findNavController(v);
                nav.navigate(R.id.action_selectedFragment_to_selectedVideoFragment,bundle);
            }
        });
        return view;
    }

    private int countFiles(File file) {
        ArrayList<File> list= new ArrayList<>();
        File[] f=file.listFiles();
        for(File files:f){
            if(files.getName().endsWith(".mp4")){
                list.add(files);
            }
        }
        return list.size();
    }
}
