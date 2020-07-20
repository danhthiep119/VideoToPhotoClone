package com.example.videotophotoclone.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.videotophotoclone.Controler.VideoAdapter;
import com.example.videotophotoclone.Model.Video;
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
    VideoListFragment video;
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
                        Toast.makeText(mContext,"Bạn Chọn Open",Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("VIDEOPATH", path);
                        NavController nav = Navigation.findNavController(v);
                        nav.navigate(R.id.action_galleryFragment_to_showVideo, bundle);
                        dismiss();
                        break;
                    case 1:
                        Toast.makeText(mContext,"Bạn Chọn Rename",Toast.LENGTH_SHORT).show();
                        RenameDialog renameDialog = new RenameDialog(getContext(),path);
                        renameDialog.setTitle(R.string.Rename);
                        renameDialog.show();
                        break;
                    case 2:
                        Toast.makeText(mContext,"Bạn Chọn Delete",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder dialog =new AlertDialog.Builder(getContext());
                        dialog.setTitle(R.string.Delete).setMessage("Are you sure?").setNegativeButton(R.string.Cancel, new OnClickListener() {
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
//                                    GalleryFragment galleryFragment = new GalleryFragment();
//                                    galleryFragment.adapter.notifyItemRemoved(position);
                                }
                                dismiss();
                            }
                        }).create();
                        dialog.show();
                        break;
                    case 3:
                        Toast.makeText(mContext,"Bạn Chọn Share",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(mContext,"Bạn Chọn Details",Toast.LENGTH_SHORT).show();
                        showDialog();
                        break;
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showDialog() {
        try {
            File file = new File(path);
            double  size = file.length()/(1024*1024);
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            BasicFileAttributes attr = Files.readAttributes(Paths.get(path),BasicFileAttributes.class);
            System.out.println("Created time:" +attr.creationTime());
            System.out.println("File size:"+decimalFormat.format(size)+"mb");
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setTitle(R.string.Details).setMessage("" +
                    "File name:\n"+file.getName()+
                    "\nFile size:\n"+decimalFormat.format(size)+ "mb\nDate:\n"+attr.creationTime()+
                    "\nPath:\n"+path).create();
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
