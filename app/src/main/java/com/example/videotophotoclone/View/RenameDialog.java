package com.example.videotophotoclone.View;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.videotophotoclone.R;

import java.io.File;

public class RenameDialog extends Dialog {
    Context mContex;
    TextView txtRename;
    Button btnCancel,btnDone;
    String path = "";
    public RenameDialog(@NonNull Context context,String path) {
        super(context);
        this.mContex=context;
        this.path = path;
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
                if(!txtRename.getText().toString().isEmpty()) {
                    String name = txtRename.getText().toString();
                    File file = new File(path);
                    file.renameTo(new File(name+".mp4"));
                }
                dismiss();
            }
        });
    }

    private void AddControls() {
        txtRename=findViewById(R.id.txtRename);
        btnCancel=findViewById(R.id.btnCancel);
        btnDone=findViewById(R.id.btnDone);
    }
}
