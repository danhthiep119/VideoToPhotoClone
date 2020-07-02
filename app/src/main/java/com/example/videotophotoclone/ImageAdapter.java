package com.example.videotophotoclone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    List<Integer> list;
    Context mContext;
    public ImageAdapter(List<Integer> list, Context mContext) {
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
        imgImage.setImageResource(list.get(position));
        btnDel.setVisibility(View.INVISIBLE);
        return view;
    }
}
