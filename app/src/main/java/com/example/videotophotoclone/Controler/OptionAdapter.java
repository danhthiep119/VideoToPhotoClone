package com.example.videotophotoclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class OptionAdapter extends BaseAdapter {

    String[] options = {"@string/Open","@string/Rename","@string/Delete","@string/Share","@string/Details"};
    List<String> optionList;
    Context mContext;

    public OptionAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        for (int i=0;i<options.length;i++){
            optionList.add(options[i]);
        }
        return optionList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.option_dialog,parent,false);
        return view;
    }
}
