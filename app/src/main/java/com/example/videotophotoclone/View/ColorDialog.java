package com.example.videotophotoclone.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;

import com.example.videotophotoclone.Controler.ColorAdapter;
import com.example.videotophotoclone.R;

import ja.burhanrashid52.photoeditor.PhotoEditor;

public class ColorDialog extends Dialog {
    Activity mActivity;
    public Button button;
    GridView gvColor;
    public PhotoEditor editor;
    public String pickColor;
    String[] color = {"#FF2D00","#FF9E00","#FFF700","#A6FF00","#00FF13","#00BDFF","#000CFF","#EC00FF","#FF00C9"};
    public ColorDialog(@NonNull Activity context, Button button, PhotoEditor editor) {
        super(context);
        this.mActivity = context;
        this.button = button;
        this.editor = editor;
        setContentView(R.layout.list_color_dialog);
        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {
        gvColor = findViewById(R.id.gvColor);
        gvColor.setAdapter(new ColorAdapter(getContext(),color,this));
    }
}
