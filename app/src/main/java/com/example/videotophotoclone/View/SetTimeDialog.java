package com.example.videotophotoclone.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.videotophotoclone.R;

import java.util.ArrayList;

public class SetTimeDialog extends AppCompatDialogFragment {
    private EditText txtDuration;
    private SetTimeDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder =new  AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.set_time_dialog,null);
        txtDuration = view.findViewById(R.id.txtDuration);
        builder.setView(view)
                .setTitle(R.string.EnterDuration)
                .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(txtDuration.getText().toString()!= null){
                            listener.getDuration(Float.parseFloat(txtDuration.getText().toString()));
                        }
                    }
                });
        return builder.create();
    }

    public interface SetTimeDialogListener {
        void getDuration(float duration);
    }
}
