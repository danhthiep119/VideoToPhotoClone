package com.example.videotophotoclone.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.videotophotoclone.R;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.io.File;


public class TimeCaptureVideo extends Fragment {
    RangeSeekBar range_seekbar;
    VideoView vdViewTimeCapture;
    TextView txtName,txtCurrentTime,txtEndTime;
    ImageButton imgController;
    String path;
    public TimeCaptureVideo(String path) {
        // Required empty public constructor
        this.path = path;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_capture_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        range_seekbar = view.findViewById(R.id.range_seekbar);
        txtName = view.findViewById(R.id.txtNameVideo);
        txtCurrentTime = view.findViewById(R.id.txtCurrentTime);
        txtEndTime = view.findViewById(R.id.txtEndTime);
        imgController = view.findViewById(R.id.imgControlsVideo);
        vdViewTimeCapture = view.findViewById(R.id.vdViewTimeCapture);
//        String path = getArguments().getString("VIDEOPATH");
        File file = new File(path);
        txtName.setText(file.getName());
        Uri uri = Uri.parse(path);
        vdViewTimeCapture.setVideoURI(uri);
        vdViewTimeCapture.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                range_seekbar.setSelectedMinValue(vdViewTimeCapture.getCurrentPosition());
                range_seekbar.setSelectedMaxValue(vdViewTimeCapture.getDuration());
                txtEndTime.setText(MilisecondsToTimer(vdViewTimeCapture.getDuration()/1000));
            }
        });
        range_seekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                int max_duration = (int)bar.getSelectedMaxValue();
                int min_duration = (int)bar.getSelectedMinValue();

                txtEndTime.setText(MilisecondsToTimer(max_duration/1000));
                txtCurrentTime.setText(MilisecondsToTimer(min_duration/1000));
            }
        });
        imgController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!vdViewTimeCapture.isPlaying()) {
                    vdViewTimeCapture.start();
                    imgController.setImageResource(R.drawable.ic_pause_white_24dp);
                } else {
                    vdViewTimeCapture.pause();
                    imgController.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (vdViewTimeCapture != null){
                    if(vdViewTimeCapture.isPlaying()){
                        Message msg = new Message();
                        msg.what = vdViewTimeCapture.getCurrentPosition();
                        handler.sendMessage(msg);
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private Handler handler =  new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            txtCurrentTime.setText(MilisecondsToTimer(msg.what/1000));
            range_seekbar.setSelectedMinValue(msg.what);
        }
    };

    String MilisecondsToTimer(long milisec) {
        String finalTimerString = "";
        String secondString;
        String minuteString;
        int seconds = (int) milisec % 60;
        int minutes = (int) milisec / 60;
        int hours = (int) milisec / (60 * 60);
        if (hours > 0) {
            finalTimerString = hours + ":";
        }
        if (seconds < 10) {
            secondString = "0" + seconds;
        } else secondString = "" + seconds;
        if (minutes < 10) {
            minuteString = "0" + minutes;
        } else minuteString = "" + minutes;
        finalTimerString = finalTimerString + minuteString + ":" + secondString;
        return finalTimerString;
    }
}
