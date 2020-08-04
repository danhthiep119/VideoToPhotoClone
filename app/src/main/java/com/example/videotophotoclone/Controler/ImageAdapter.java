package com.example.videotophotoclone.Controler;

import android.animation.StateListAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.videotophotoclone.R;
import com.example.videotophotoclone.View.ImageFragment;
import com.example.videotophotoclone.View.SlideshowMakerFragment;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageAdapter extends BaseAdapter {
    private Map<String, Parcelable> scrollState = new HashMap<>();
    List<File> list;
    public List<File> selectedList = new ArrayList<>();
    Context mContext;
    int id=0;
    boolean choosed = false;
    boolean selectMode=false;
    boolean isChoose = false;
    boolean delMode = false;
    ImageFragment fragment;
    public ImageAdapter(List<File> list, Context mContext,int id,boolean selectMode,boolean delMode,ImageFragment fragment) {
        this.list = list;
        this.mContext = mContext;
        this.id = id;
        this.selectMode = selectMode;
        this.delMode = delMode;
        this.fragment = fragment;
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
                if(id!=0&&!selectMode) {
                    Bundle bundle = new Bundle();
                    bundle.putString("IMAGEPATH", path);
                    NavController nav = Navigation.findNavController(v);
                    nav.navigate(id, bundle);
                }
                if(selectMode){
                    if(!isChoose){
                        isChoose = true;
                        selectedList.add(list.get(position));
                    }
                    else {
                        isChoose = false;
                        if (!selectedList.isEmpty()){
                            selectedList.remove(list.get(position));
                        }
                    }
                    fragment.txtNumSelected.setText("Selected "+selectedList.size()+" images");
                }
                fragment.btnDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder dialog =new AlertDialog.Builder(mContext);
                        dialog.setTitle(R.string.Delete).setMessage(R.string.DeleteQuestion).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for(File file :selectedList){
                                    if(file.exists()) {
                                        file.delete();
                                    }
                                    list.remove(file);
                                }
                                for(int i=selectedList.size()-1;i>=0;i--){
                                    selectedList.remove(i);
                                }
                                fragment.txtNumSelected.setText("Selected "+selectedList.size()+" images");
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        }).create();
                        dialog.show();
                    }
                });
                fragment.btnReload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isChoose=false;
                        if(!selectedList.isEmpty()){
                        for(int i=selectedList.size()-1;i>=0;i--){
                            selectedList.remove(i);
                        }}
                        fragment.txtNumSelected.setText("Selected "+selectedList.size()+" images");
                        notifyDataSetChanged();
                    }
                });
                if(isChoose){
                    btnDel.setImageResource(R.drawable.ic_check_circle_black_24dp);
                }
                else
                    btnDel.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectMode = true;
                notifyDataSetChanged();
                if(fragment!=null){
                    fragment.SelectedZone.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        btnDel.setVisibility(View.INVISIBLE);
        if(selectMode) {
            btnDel.setVisibility(View.VISIBLE);
            btnDel.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
        }
        if(delMode){
            btnDel.setVisibility(View.VISIBLE);
            btnDel.setImageResource(R.drawable.ic_red_cancel_24);
            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
        return view;
    }
}
