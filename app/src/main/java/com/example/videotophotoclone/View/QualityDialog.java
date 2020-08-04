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

public class QualityDialog extends Dialog {
    Activity mActivity;
    RadioButton rdBest,rdVrHigh,rdHigh,rdMedium,rdLow;
    Button btnQuality;
    ImageButton btnClose;
    String defQuality = "High";
    String quality = "";
    public QualityDialog(@NonNull Activity context,Button btnQuality) {
        super(context);
        mActivity=context;
        this.btnQuality = btnQuality;
        setContentView(R.layout.quality_dialog);
        addControls();
        addEvents();
    }

    private void getDataShareReference() {
        SharedPreferences sharedPreferences = mActivity.getPreferences(Context.MODE_PRIVATE);
        quality = sharedPreferences.getString("QUALITY",defQuality);
        checkQuality(quality);

    }

    private void checkQuality(String quality) {
        if(quality.equals(mActivity.getResources().getString(R.string.Best))){
            rdBest.setChecked(true);
        }
        if(quality.equals(mActivity.getResources().getString(R.string.VeryHigh))){
            rdVrHigh.setChecked(true);
        }if(quality.equals(mActivity.getResources().getString(R.string.High))){
            rdHigh.setChecked(true);
        }if(quality.equals(mActivity.getResources().getString(R.string.Medium))){
            rdMedium.setChecked(true);
        }if(quality.equals(mActivity.getResources().getString(R.string.Low))){
            rdLow.setChecked(true);
        }
    }

    private void addEvents() {
        rdBest.setOnCheckedChangeListener(listener);
        rdVrHigh.setOnCheckedChangeListener(listener);
        rdHigh.setOnCheckedChangeListener(listener);
        rdMedium.setOnCheckedChangeListener(listener);
        rdLow.setOnCheckedChangeListener(listener);
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
                String quality = buttonView.getText().toString();
                buttonView.setChecked(true);
                saveQuality(quality);
                btnQuality.setText(quality);
//                dismiss();
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
        btnClose=findViewById(R.id.btnClose);
        getDataShareReference();
    }
}
