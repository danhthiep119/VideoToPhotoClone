package com.example.videotophotoclone.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.videotophotoclone.Model.TypeSetting;
import com.example.videotophotoclone.R;

public class FileFormatDialog extends Dialog {
//    RadioGroup rdFileFormat;
    RadioButton rdJPG,rdPNG;
    int mode = 0;
    final String TAG = "FileFormat:";
    Activity mContext;
    public FileFormatDialog(@NonNull Activity context) {
        super(context);
        this.mContext = context;
        setContentView(R.layout.file_format_list);
        addControls();
        addEvents();
    }

    private void addEvents() {
        rdJPG.setOnCheckedChangeListener(listener);
        rdPNG.setOnCheckedChangeListener(listener);
    }

    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                String type =  buttonView.getText().toString();
                saveType(type);
                dismiss();
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
    }
}
