package com.example.videotophotoclone.View;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.videotophotoclone.Model.TypeSetting;
import com.example.videotophotoclone.R;

public class SettingDialog extends Dialog {
    Button btnFileFormat,btnQuality,btnSize;
    int fileFormat=0;
    public SettingDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.setting_dialog);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnFileFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileFormatDialog dialog = new FileFormatDialog(getContext());
                dialog.show();
            }
        });
        btnFileFormat.setText(TypeSetting.type);
    }

    private void addControls() {
        btnFileFormat = findViewById(R.id.btnFileFormat);
    }
}
