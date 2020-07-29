package com.example.videotophotoclone.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.videotophotoclone.R;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

public class OptionDialog extends Dialog {
    Context mContext;
    ListView lvOption;
    final int[] options =   {   R.string.Open,
                                R.string.Rename,
                                R.string.Delete,
                                R.string.Share,
                                R.string.Details
                            };
    List<String> optionsList = new ArrayList<>();
    final String TAG="OptionDialog";
    Button btnClose;
    String path = "";
    ArrayAdapter<String> adapter;
    int position;
    View v;
    public OptionDialog(Context mContext,String path,int position,View v) {
        super(mContext);
        this.mContext = mContext;
        this.path = path;
        this.position=position;
        this.v = v;
        setContentView(R.layout.option_dialog);
        setTitle(R.string.Option);
        AddControls();
        AddEvents();
    }

    private void AddEvents() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        lvOption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                switch (position){
                    case 0:
                        Bundle bundle = new Bundle();
                        bundle.putString("VIDEOPATH", path);
                        NavController nav = Navigation.findNavController(v);
                        nav.navigate(R.id.action_galleryFragment_to_showVideo, bundle);
                        dismiss();
                        break;
                    case 1:
                        RenameDialog renameDialog = new RenameDialog(getContext(),path);
                        renameDialog.setTitle(R.string.Rename);
                        renameDialog.show();
                        break;
                    case 2:
                        AlertDialog.Builder dialog =new AlertDialog.Builder(getContext());
                        dialog.setTitle(R.string.Delete).setMessage(R.string.DeleteQuestion).setNegativeButton(R.string.Cancel, new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismiss();
                            }
                        }).setPositiveButton(R.string.Ok, new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                File file = new File(path);
                                if(file.exists()) {
                                    file.delete();
                                }
                                dismiss();
                            }
                        }).create();
                        dialog.show();
                        break;
                    case 3:
                        break;
                    case 4:
                        showDialog();
                        break;
                }
            }
        });
    }

    private void showDialog() {
        try {
            File file = new File(path);
            double  size = file.length()/(1024*1024);
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            BasicFileAttributes attr = null;
            FileTime creationTime = null;
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                attr = Files.readAttributes(Paths.get(path), BasicFileAttributes.class);
                creationTime = attr.creationTime();
                dialog.setTitle(R.string.Details).setMessage("" +
                        mContext.getResources().getString(R.string.FileName)+":\n"+file.getName()+
                        "\n"+mContext.getResources().getString(R.string.FileSize)+":\n"+decimalFormat.format(size)+" MB"+
                        "\nDate:\n"+attr.creationTime()+
                        "\n"+mContext.getResources().getString(+R.string.Path)+":\n"+path)
                        .setPositiveButton(R.string.Ok, new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismiss();
                            }
                        })
                        .create();
            }
            else {
                dialog.setTitle(R.string.Details).setMessage("" +
                        mContext.getResources().getString(R.string.FileName)+":\n"+file.getName()+
                        "\n"+mContext.getResources().getString(R.string.FileSize)+":\n"+decimalFormat.format(size)+" MB"+
                        "\n"+mContext.getResources().getString(R.string.Path)+":\n"+path)
                        .setPositiveButton(R.string.Ok, new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismiss();
                            }
                        })
                        .create();
            }
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void AddControls() {
        for(int i=0;i<options.length;i++){
            optionsList.add(mContext.getResources().getString(options[i]));
        }
        lvOption=findViewById(R.id.lvListOption);
        try {
            adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, optionsList);
            lvOption.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        catch (Exception e){
            Log.w(TAG,"Null Object:"+e);
        }
        btnClose=findViewById(R.id.btnClose);
    }
}
