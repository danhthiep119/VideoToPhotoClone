package com.example.videotophotoclone.Controler;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.videotophotoclone.R;
import com.example.videotophotoclone.View.ColorDialog;

public class ColorAdapter extends BaseAdapter {
    Context mContext;
    String[] colorList;
    ColorDialog dialog;
    public ColorAdapter(Context mContext, String[] colorList,ColorDialog dialog) {
        this.mContext = mContext;
        this.colorList = colorList;
        this.dialog = dialog;
    }

    @Override
    public int getCount() {
        return colorList.length;
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
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_color,parent,false);
        ImageView imgImage = view.findViewById(R.id.imgColor);
        imgImage.setBackgroundColor(Color.parseColor(colorList[position]));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.button.setBackgroundColor(Color.parseColor(colorList[position]));
                dialog.editor.setBrushColor(Color.parseColor(colorList[position]));
                dialog.dismiss();
            }
        });
        return view;
    }
}
