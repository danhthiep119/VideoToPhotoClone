package com.example.videotophotoclone.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.videotophotoclone.Model.TypeSetting;
import com.example.videotophotoclone.R;

public class SettingDialog extends Dialog {
    Button btnFileFormat,btnQuality,btnSize;
    int fileFormat=0;
    String defVal = "JPG";
    final String TAG = "Setting dialog";
    Activity mActivity;
//    static String type = "";
    public SettingDialog(@NonNull Activity context) {
        super(context);
        this.mActivity = context;
        setContentView(R.layout.setting_dialog);
//        getSharedPreference();
        addControls();
        addEvents();
    }

    private void getSharedPreference() {
        try
        {
            SharedPreferences sharedPreferences = mActivity.getPreferences(Context.MODE_PRIVATE);
            String type = sharedPreferences.getString("TYPE",defVal);
            btnFileFormat.setText(type);
        }
        catch (Exception e){
            Log.w(TAG,e);
        }

    }

    private void addEvents() {
        btnFileFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileFormatDialog dialog = new FileFormatDialog(mActivity);
                dialog.show();
            }
        });
    }

    private void addControls() {
        btnFileFormat = findViewById(R.id.btnFileFormat);
        getSharedPreference();
    }
}
