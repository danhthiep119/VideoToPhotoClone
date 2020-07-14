package com.example.videotophotoclone.View;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.videotophotoclone.Model.TypeSetting;
import com.example.videotophotoclone.R;

public class FileFormatDialog extends Dialog {
    RadioGroup rdFileFormat;
    RadioButton rdButton;
    int mode = 0;
    final String TAG = "FileFormat:";
    public FileFormatDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.file_format_list);
        addControls();
        addEvents();
    }

    private void addEvents() {
        try{
            int itemSelected= rdFileFormat.getCheckedRadioButtonId();
            rdButton = findViewById(itemSelected);
            switch (rdButton.getText().toString()){
                case "JPG":
                    TypeSetting.type = "JPG";
                    break;
                case "PNG":
                    TypeSetting.type = "PNG";
                    break;
            }
        }
        catch (Exception e){
            Log.e(TAG,""+e);
        }
        dismiss();
        Log.w(TAG,""+TypeSetting.type);
    }

    private void addControls() {
        rdFileFormat=findViewById(R.id.rdFileFormat);
//        rdJPG=findViewById(R.id.rdJPG);
//        rdPNG=findViewById(R.id.rdPNG);
    }
}
