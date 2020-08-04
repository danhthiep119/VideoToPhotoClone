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

class SizeDialog extends Dialog {
    RadioButton rd05x,rd1x,rd15x,rd2x,rd3x;
    Activity mActivity;
    Button btnSize;
    ImageButton btnClose;
    String size = "";
    String defSize = "1x";
    public SizeDialog(@NonNull Activity context,Button btnSize) {
        super(context);
        mActivity = context;
        this.btnSize = btnSize;
        setContentView(R.layout.size_dialog);
        addControls();
        addEvents();
    }
    private void getDataShareReference() {
        SharedPreferences sharedPreferences = mActivity.getPreferences(Context.MODE_PRIVATE);
        size = sharedPreferences.getString("SIZE",defSize);
        checkQuality(size);

    }

    private void checkQuality(String quality) {
        if(quality.equals("0.5x")){
            rd05x.setChecked(true);
        }
        if(quality.equals("1x")){
            rd1x.setChecked(true);
        }
        if(quality.equals("1.5x")){
            rd15x.setChecked(true);
        }
        if(quality.equals("2x")){
            rd2x.setChecked(true);
        }
        if(quality.equals("3x")){
            rd3x.setChecked(true);
        }
    }


    private void addEvents() {
        rd05x.setOnCheckedChangeListener(listener);
        rd1x.setOnCheckedChangeListener(listener);
        rd15x.setOnCheckedChangeListener(listener);
        rd2x.setOnCheckedChangeListener(listener);
        rd3x.setOnCheckedChangeListener(listener);
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
            if(isChecked)
            {
                String size = buttonView.getText().toString();
                saveSize(size);
                btnSize.setText(size);
            }
        }
    };

    private void saveSize(String size) {
        SharedPreferences sharedPreferences = mActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SIZE",size);
        editor.commit();
    }

    private void addControls() {
        rd05x=findViewById(R.id.rd05x);
        rd1x=findViewById(R.id.rd1x);
        rd15x=findViewById(R.id.rd15x);
        rd2x=findViewById(R.id.rd2x);
        rd3x=findViewById(R.id.rd3x);
        btnClose=findViewById(R.id.btnClose);
        getDataShareReference();
    }
}
