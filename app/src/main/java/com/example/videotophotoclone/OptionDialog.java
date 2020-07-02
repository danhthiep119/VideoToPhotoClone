package com.example.videotophotoclone;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

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
    ArrayAdapter<String> adapter;
    public OptionDialog(@NonNull Context mContext) {
        super(mContext);
        this.mContext=mContext;
        setContentView(R.layout.option_dialog);
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
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Toast.makeText(mContext,"Bạn Chọn Open",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(mContext,"Bạn Chọn Rename",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(mContext,"Bạn Chọn Delete",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(mContext,"Bạn Chọn Share",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(mContext,"Bạn Chọn Details",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
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
