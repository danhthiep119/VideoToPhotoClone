package com.example.videotophotoclone.Controler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.videotophotoclone.R;
import com.example.videotophotoclone.View.SlideshowMakerFragment;

import java.io.File;
import java.util.List;

public class GetImageAdapter extends BaseAdapter {
    List<File> listFile;
    Context mContext;
    SlideshowMakerFragment slide;
    final String TAG = "Get image adapter";
    public GetImageAdapter(List<File> file, Context mContext, SlideshowMakerFragment slide) {
        this.listFile = file;
        this.mContext = mContext;
        this.slide = slide;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image,parent,false);
        ImageButton btnDel=view.findViewById(R.id.btnDel);
        ImageView imgImage=view.findViewById(R.id.imgImage);
        String path = listFile.get(position).getAbsolutePath();
        File file = new File(path);
        if(file.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imgImage.setImageBitmap(bitmap);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    slide.imageSelected.add(listFile.get(position));
                    slide.selectedImage();
                }
                catch (Exception e){
                    Log.w(TAG,e);
                }
            }
        });
        btnDel.setVisibility(View.INVISIBLE);
        return view;
    }
}
