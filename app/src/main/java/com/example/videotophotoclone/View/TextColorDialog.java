package com.example.videotophotoclone.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import androidx.annotation.NonNull;

import com.example.videotophotoclone.Controler.ColorAdapter;
import com.example.videotophotoclone.Controler.ColorTextAdapter;
import com.example.videotophotoclone.R;

import ja.burhanrashid52.photoeditor.PhotoEditor;

public class TextColorDialog extends Dialog {
    Activity mActivity;
    public Button button;
    GridView gvColor;
    public PhotoEditor editor;
    public EditText text;
    String[] color = {"#FF2D00","#FF9E00","#FFF700","#A6FF00","#00FF13","#00BDFF","#000CFF","#EC00FF","#FF00C9"};
    public TextColorDialog(@NonNull Activity context, Button button, EditText text, PhotoEditor editor) {
        super(context);
        this.mActivity = context;
        this.button = button;
        this.editor = editor;
        this.text =text;
        setContentView(R.layout.list_color_dialog);
        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {
        gvColor = findViewById(R.id.gvColor);
        gvColor.setAdapter(new ColorTextAdapter(getContext(),color,this));
    }
}
