package com.example.videotophotoclone.View;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.videotophotoclone.Model.TypeSetting;
import com.example.videotophotoclone.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Struct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import wseemann.media.FFmpegMediaMetadataRetriever;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoEditFragment extends Fragment {
    SeekBar seekBar;
    TextView txtNameVideo, txtCurrentTime, txtEndTime;
    VideoView vdView;
    ImageButton imgControlsVideo, btnSnap;
    Button btnDone;
    ImageView imgSnap;
    List<Bitmap> captureImageList = new ArrayList<>();
    final String TAG = "Video Edit Fragment:";
    TypeSetting setting;
    String videoPath="";
    static String endWiths = ".jpg";
    Thread playVideo;
    FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();

    public VideoEditFragment(String path) {
        // Required empty public constructor
        this.videoPath=path;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        seekBar = view.findViewById(R.id.seek_bar_video);
        txtNameVideo = view.findViewById(R.id.txtNameVideo);
        txtCurrentTime = view.findViewById(R.id.txtCurrentTime);
        txtEndTime = view.findViewById(R.id.txtEndTime);
        vdView = view.findViewById(R.id.vdView);
        imgControlsVideo = view.findViewById(R.id.imgControlsVideo);
        btnDone = view.findViewById(R.id.btnDone);
        btnSnap = view.findViewById(R.id.btnSnap);
        imgSnap = view.findViewById(R.id.imgSnap);
        imgControlsVideo.setImageResource(R.drawable.ic_play_arrow_white_24dp);
//        final String videoPath = getArguments().getString("VIDEOPATH");
        try {
            getData(videoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        btnSnap.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                vdView.pause();
                Bitmap bmFrame = mmr.getFrameAtTime(vdView.getCurrentPosition()*1000,FFmpegMediaMetadataRetriever.OPTION_CLOSEST);
                captureImageList.add(bmFrame);
                createFileImage(bmFrame,videoPath);
                imgSnap.setImageBitmap(bmFrame);
            }
        });
        try {
            if (!captureImageList.isEmpty()) {
                imgSnap.setImageBitmap(captureImageList.get(captureImageList.size() - 1));
            }
        }
        catch (Exception e){
            Log.w(TAG,""+e);
        }
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vdView.pause();
                vdView.suspend();
                Thread.currentThread().interrupt();
                NavController nav = Navigation.findNavController(v);
                nav.navigate(R.id.action_tabVideoFragment_to_imageListFragment);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
//        vdView.pause();
//        vdView.suspend();
//        Thread.currentThread().interrupt();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        vdView.pause();
        vdView.suspend();
        Thread.currentThread().interrupt();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createFileImage(Bitmap bimap,String videoPath) {
        File filePath = new File(videoPath);
        filePath = new File(filePath.getName());
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/ScreenShots");
        if(!file.exists()){
            file.mkdirs();
        }
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
//        LocalDateTime now = LocalDateTime.now();

        Bitmap.CompressFormat bitmap = Bitmap.CompressFormat.JPEG;
        if(setting.type.equals("JPG")){
            endWiths = ".jpg";
            bitmap = Bitmap.CompressFormat.JPEG;
        }
        if(setting.type.equals("PNG")){
            endWiths = ".png";
            bitmap = Bitmap.CompressFormat.PNG;
        }
//        String fileName = String.format(formatter.format(now)+endWiths);
        String fileName = System.currentTimeMillis()+endWiths;
        File outFile = new File(file,fileName);
        try {
            FileOutputStream fos = new FileOutputStream(outFile);
            bimap.compress(bitmap,100,fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getData(String videoPath) throws IOException {
        File videoFile = new File(videoPath);
        videoFile = new File(videoFile.getPath());
        Uri uri = Uri.parse(videoPath);
        mmr.setDataSource(videoPath);
        vdView.setVideoURI(uri);
        vdView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(vdView.getDuration());
                String duration = MilisecondsToTimer(vdView.getDuration() / 1000);
                txtEndTime.setText(duration);
            }
        });
        vdView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        txtNameVideo.setText(videoFile.getName());

        imgControlsVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!vdView.isPlaying()) {
                    vdView.start();
                    imgControlsVideo.setImageResource(R.drawable.ic_pause_white_24dp);
                } else {
                    vdView.pause();
                    imgControlsVideo.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    vdView.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (vdView != null) {
                            try {
                                if (vdView.isPlaying()) {
                                    Message msg = new Message();
                                    msg.what = vdView.getCurrentPosition();
                                    handler.sendMessage(msg);
                                    Thread.sleep(1);
                                }
                            } catch (InterruptedException ie) {
                                Log.w(TAG, "" + ie);
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


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            txtCurrentTime.setText(MilisecondsToTimer(msg.what / 1000));
            seekBar.setProgress(msg.what);
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
