package com.example.videotophotoclone.View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.videotophotoclone.R;

public class AboutDialog extends Dialog {
    Context mContext;
    Button btnAuthor,btnPrivacy,btnOk;
    public AboutDialog(@NonNull Context mContext) {
        super(mContext);
        this.mContext=mContext;
        setContentView(R.layout.about_dialog);
        AddControls();
        AddEvents();
    }

    private void AddEvents() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Webview.class);
                intent.putExtra("PUSHDATA","https://hdpsolutions.com");
                mContext.startActivity(intent);
            }
        });
        btnPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(), Webview.class);
                intent.putExtra("PUSHDATA","https://hdpsolution.com/privacy_policy/CameraApps/PrivacyPolicyCameraApps.html");
                mContext.startActivity(intent);
            }
        });
    }

    private void AddControls() {
        btnAuthor=findViewById(R.id.btnAuthor);
        btnPrivacy=findViewById(R.id.btnPrivacy);
        btnOk=findViewById(R.id.btnOk);
    }
}
