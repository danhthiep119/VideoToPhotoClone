package com.example.videotophotoclone.Controler;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.videotophotoclone.R;

public class SettingAdapter extends Dialog {
    Button btnFileFormat,btnQuality,btnSize;
    public SettingAdapter(@NonNull Context context) {
        super(context);
        setContentView(R.layout.setting_dialog);
        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {

    }
}
