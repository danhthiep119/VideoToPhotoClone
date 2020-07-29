package com.example.videotophotoclone.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;

import com.example.videotophotoclone.R;

public class QualityDialog extends Dialog {
    Activity mActivity;
    RadioButton rdBest,rdVrHigh,rdHigh,rdMedium,rdLow;
    public QualityDialog(@NonNull Activity context) {
        super(context);
        mActivity=context;
        setContentView(R.layout.quality_dialog);
        addControls();
        addEvents();
    }

    private void addEvents() {
        rdBest.setOnCheckedChangeListener(listener);
        rdVrHigh.setOnCheckedChangeListener(listener);
        rdHigh.setOnCheckedChangeListener(listener);
        rdMedium.setOnCheckedChangeListener(listener);
        rdLow.setOnCheckedChangeListener(listener);
    }
    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                String quality = buttonView.getText().toString();
                saveQuality(quality);
                dismiss();
            }
        }
    };

    private void saveQuality(String quality) {
        SharedPreferences sharedPreferences = mActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("QUALITY",quality);
        editor.commit();
    }

    private void addControls() {
        rdBest=findViewById(R.id.rdBest);
        rdVrHigh=findViewById(R.id.rdVrHigh);
        rdHigh=findViewById(R.id.rdHigh);
        rdMedium=findViewById(R.id.rdMedium);
        rdLow=findViewById(R.id.rdLow);
    }
}
