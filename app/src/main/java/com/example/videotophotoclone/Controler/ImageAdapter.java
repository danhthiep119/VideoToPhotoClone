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
import com.example.videotophotoclone.View.SlideshowMakerFragment;

import java.io.File;
import java.util.List;

public class ImageAdapter extends BaseAdapter {
    List<File> list;
    Context mContext;
    int id=0;
    boolean selectMode=false;
    boolean isChoose = false;
    public ImageAdapter(List<File> list, Context mContext,int id) {
        this.list = list;
        this.mContext = mContext;
        this.id = id;
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
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_image,parent,false);
        final ImageButton btnDel=view.findViewById(R.id.btnDel);
        final ImageView imgImage=view.findViewById(R.id.imgImage);
        final String path = list.get(position).getAbsolutePath();
        File file = new File(path);
        if(file.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imgImage.setImageBitmap(bitmap);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id!=0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("IMAGEPATH", path);
                    NavController nav = Navigation.findNavController(v);
                    nav.navigate(id, bundle);
                }
                if(selectMode){
                    btnDel.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
                    if(!isChoose){
                        isChoose = true;
                        btnDel.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    }
                    else  {
                        isChoose = false;
                        btnDel.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
                    }
                }


            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                if(selectMode = false)
//                {
//                    btnDel.setVisibility(View.VISIBLE);
//                    selectMode=true;
//                }
//                else {
//                    btnDel.setVisibility(View.INVISIBLE);
//                    selectMode=false;
//                }
                selectMode = true;
                btnDel.setVisibility(View.VISIBLE);
                return true;
            }
        });
        btnDel.setVisibility(View.INVISIBLE);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
