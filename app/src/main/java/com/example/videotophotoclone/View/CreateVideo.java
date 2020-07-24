package com.example.videotophotoclone.View;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.arthenica.mobileffmpeg.Config;
//import com.arthenica.mobileffmpeg.FFmpeg;
import com.example.videotophotoclone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateVideo extends Fragment {
    BottomNavigationView bottomNavigationView;
    RangeSeekBar seekBar;
    List<File> imageList = new ArrayList<>();
    static int duration = 2;
    String musicPath = "";
    String addImage = "";
//    FFmpeg mffmpeg;
    Button btnMakeVideo;
    EditText txtSetNameVideo;
    String path = "";
    final String TAG = "Create Video";

    public CreateVideo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        seekBar = view.findViewById(R.id.range_music);
        seekBar.setVisibility(View.INVISIBLE);
        btnMakeVideo = view.findViewById(R.id.btnMakeVideo);
        btnMakeVideo.setVisibility(View.INVISIBLE);
        txtSetNameVideo = view.findViewById(R.id.txtSetNameVideo);
        final ArrayList<String> listImage = getArguments().getStringArrayList("IMAGESELECTED");
        getData(listImage);
        if (path != "" && !imageList.isEmpty()) {
            btnMakeVideo.setVisibility(View.VISIBLE);
            btnMakeVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeVideo();
                }
            });
        }
        System.out.println(imageList.size());
        for (int i = 0; i < imageList.size(); i++) {
            System.out.println(imageList.get(i).getAbsolutePath());
        }
        bottomNavigationView = view.findViewById(R.id.nav_edit_create_video);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_add_more_photo:
                        break;
                    case R.id.nav_set_slide_per_sec:
                        setDuration();
                        System.out.println(duration);
                        break;
                    case R.id.nav_music:
                        passingData(view, listImage);
                        break;
                }
                return true;
            }
        });
    }

    void makeVideo() {
        String addAudio = " -i " + path;
        String addName = "";
        addImage = "";
        for (File file : imageList) {
            addImage += " -loop 1 -t " + duration + " -i " + file.getAbsolutePath();
        }
        if (txtSetNameVideo.getText().toString() != null) {
            addName = " -c:v libx264 -c:a copy -shortest " + Environment.getExternalStorageDirectory().getAbsolutePath() + "/Videos/" + txtSetNameVideo.getText().toString() + ".mp4";
        }
        String total = "ffmpeg"+ addImage+ addAudio+ addName;
//        mffmpeg.execute(total);
    }

    private void passingData(View view, ArrayList<String> listImage) {
        try {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("IMAGESELECTED", listImage);
            NavController nav = Navigation.findNavController(view);
            nav.navigate(R.id.action_createVideo_to_musicList, bundle);
        } catch (Exception e) {

        }
    }

    private void setDuration() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setTitle(R.string.EnterDuration);
        dialog.setContentView(R.layout.set_slide_secound);
        final EditText txtSetDuration = dialog.findViewById(R.id.txtSetDuration);
        Button btnCancel, btnOk;
        btnCancel = dialog.findViewById(R.id.btnCancel);
        btnOk = dialog.findViewById(R.id.btnOk);
        dialog.show();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtSetDuration != null) {
                    duration = Integer.parseInt(txtSetDuration.getText().toString());
                }
                dialog.dismiss();
            }
        });
    }

    private void getData(ArrayList<String> listImage) {
        imageList.clear();
        try {
            for (String imagePath : listImage) {
                File file = new File(imagePath);
                imageList.add(file);
            }
            path = getArguments().getString("MUSICPATH");
            seekBar.setVisibility(View.VISIBLE);
            MediaPlayer mp = new MediaPlayer();
            mp.setDataSource(path);
            seekBar.setSelectedMinValue(mp.getCurrentPosition());
            seekBar.setSelectedMaxValue(mp.getDuration());
        } catch (Exception e) {

        }
    }
}
