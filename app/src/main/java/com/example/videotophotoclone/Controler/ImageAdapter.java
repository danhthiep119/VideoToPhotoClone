package com.example.videotophotoclone.Controler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.videotophotoclone.R;

import java.io.File;
import java.util.List;

public class ImageAdapter extends BaseAdapter {
    List<File> list;
    Context mContext;
    public ImageAdapter(List<File> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return list.size();
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image,parent,false);
        ImageButton btnDel=view.findViewById(R.id.btnDel);
        ImageView imgImage=view.findViewById(R.id.imgImage);
        final String path = list.get(position).getAbsolutePath();
        File file = new File(path);
        if(file.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imgImage.setImageBitmap(bitmap);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("IMAGEPATH",path);
                NavController nav = Navigation.findNavController(v);
                nav.navigate(R.id.action_imageListFragment_to_editPhotoFragment2,bundle);
            }
        });
        btnDel.setVisibility(View.INVISIBLE);
        return view;
    }
}
