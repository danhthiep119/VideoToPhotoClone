package com.example.videotophotoclone;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class RenameDialog extends Dialog {
    Context mContex;
    TextView txtRename;
    Button btnCancel,btnDone;
    public RenameDialog(@NonNull Context context) {
        super(context);
        this.mContex=context;
        setContentView(R.layout.rename_dialog);
        AddControls();
        AddEvents();

    }

    private void AddEvents() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtRename.getText().toString();
            }
        });
    }

    private void AddControls() {
        txtRename=findViewById(R.id.txtRename);
        btnCancel=findViewById(R.id.btnCancel);
        btnDone=findViewById(R.id.btnDone);
    }
}
