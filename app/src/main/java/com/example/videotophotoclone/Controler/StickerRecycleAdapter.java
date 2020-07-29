package com.example.videotophotoclone.Controler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videotophotoclone.R;

import ja.burhanrashid52.photoeditor.PhotoEditor;

public class StickerRecycleAdapter extends RecyclerView.Adapter<StickerRecycleAdapter.DataViewHolder> {
    PhotoEditor photoEditor;
    Context mContext;
    int[] sticker;

    public StickerRecycleAdapter(PhotoEditor photoEditor, Context mContext, int[] sticker) {
        this.photoEditor = photoEditor;
        this.mContext = mContext;
        this.sticker = sticker;
    }

    @NonNull
    @Override
    public StickerRecycleAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image,parent,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerRecycleAdapter.DataViewHolder holder, final int position) {
        holder.imgImage.setImageResource(sticker[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),sticker[position]);
                photoEditor.addImage(bitmap);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sticker.length;
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        ImageView imgImage;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            imgImage = itemView.findViewById(R.id.imgImage);
        }
    }
}
