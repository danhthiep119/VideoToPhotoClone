package com.example.videotophotoclone.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import com.example.videotophotoclone.R;

public class FileFormatDialog extends Dialog {
    RadioButton rdJPG,rdPNG;
    Activity mContext;
    Button btnFileFormat;
    ImageButton btnClose;
    String fileFormat = "";
    String defType = "JPG";
    public FileFormatDialog(@NonNull Activity context, Button btnFileFormat) {
        super(context);
        this.mContext = context;
        this.btnFileFormat = btnFileFormat;
        setContentView(R.layout.file_format_list);
        addControls();
        addEvents();
    }
    private void getDataShareReference() {
        SharedPreferences sharedPreferences = mContext.getPreferences(Context.MODE_PRIVATE);
        fileFormat = sharedPreferences.getString("TYPE",defType);
        checkQuality(fileFormat);

    }

    private void checkQuality(String quality) {
        if(quality.equals("JPG")){
            rdJPG.setChecked(true);
        }
        if(quality.equals("PNG")){
            rdPNG.setChecked(true);
        }
    }

    private void addEvents() {
        rdJPG.setOnCheckedChangeListener(listener);
        rdPNG.setOnCheckedChangeListener(listener);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                String type =  buttonView.getText().toString();
                buttonView.setChecked(true);
                saveType(type);
                btnFileFormat.setText(type);
            }
        }
    };

    private void saveType(String type) {
        SharedPreferences sharedPreferences = mContext.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("TYPE",type);
        editor.commit();
    }

    private void addControls() {
        rdJPG=findViewById(R.id.rdJPG);
        rdPNG=findViewById(R.id.rdPNG);
        btnClose=findViewById(R.id.btnClose);
        getDataShareReference();
    }
}
