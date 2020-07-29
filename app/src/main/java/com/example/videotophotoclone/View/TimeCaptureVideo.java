package com.example.videotophotoclone.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.videotophotoclone.R;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import wseemann.media.FFmpegMediaMetadataRetriever;


public class TimeCaptureVideo extends Fragment{
    List<Bitmap> listImage = new ArrayList<>();
    RangeSeekBar range_seekbar;
    VideoView vdViewTimeCapture;
    TextView txtName,txtCurrentTime,txtEndTime;
    ImageButton imgController,btnSnap;
    Button btnDone,btnSetTime;
    ImageView imgSnap;
    String path,endWiths;
    public int duration = 2000;
    String type = "";
    String quality = "";
    FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
    final String TAG="TimeCapture";
    volatile boolean stopThread = false;
    public TimeCaptureVideo(String path) {
        // Required empty public constructor
        this.path = path;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences mShared = getActivity().getPreferences(Context.MODE_PRIVATE);
        type = mShared.getString("TYPE","JPG");
        quality = mShared.getString("QUALITY","High");
    }

    @Override
    public void onStop() {
        super.onStop();
        stopThread = true;
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
        addControls(view);
        addEvents();
        File file = new File(path);
        txtName.setText(file.getName());
        Uri uri = Uri.parse(path);
        vdViewTimeCapture.setVideoURI(uri);
        mmr.setDataSource(path);
        vdViewTimeCapture.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                txtCurrentTime.setText(MilisecondsToTimer(vdViewTimeCapture.getCurrentPosition()/1000));
                txtEndTime.setText(MilisecondsToTimer(vdViewTimeCapture.getDuration()/1000));
                range_seekbar.setRangeValues(0,mp.getDuration()/1000);
                range_seekbar.setEnabled(true);
            }
        });
        range_seekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(final RangeSeekBar bar, Object minValue, Object maxValue) {
                vdViewTimeCapture.seekTo((int)minValue*1000);
                int max_duration = (int)bar.getSelectedMaxValue();
                int min_duration = (int)bar.getSelectedMinValue();
                txtEndTime.setText(MilisecondsToTimer(max_duration));
                txtCurrentTime.setText(MilisecondsToTimer(min_duration));
                btnSnap.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        for(int currentTime = vdViewTimeCapture.getCurrentPosition()+duration;
                            currentTime <= (int)bar.getSelectedMaxValue()*1000;
                            currentTime+=duration){
                            Bitmap bmFrame = mmr.getFrameAtTime(currentTime*1000,FFmpegMediaMetadataRetriever.OPTION_CLOSEST);
                            listImage.add(bmFrame);
                            createImage(bmFrame);
                        }
                        imgSnap.setImageBitmap(listImage.get(listImage.size()-1));
                    }
                });
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
                    else {
                        Message msg = new Message();
                        msg.what = vdViewTimeCapture.getCurrentPosition();
                        handler.sendMessage(msg);
                    }
                    if(stopThread){
                        return;
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

    private void addEvents() {
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nav = Navigation.findNavController(v);
                nav.navigate(R.id.action_tabVideoFragment_to_galleryFragment);
            }
        });
        btnSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        imgController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nav = Navigation.findNavController(v);
                nav.navigate(R.id.action_tabVideoFragment_to_galleryFragment);
            }
        });
    }

    private void openDialog() {
        final EditText txtDuration;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.set_time_dialog,null);
        txtDuration=view.findViewById(R.id.txtDuration);
        builder.setView(view)
                .setTitle(R.string.EnterDuration)
                .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       if(!txtDuration.getText().toString().isEmpty()){
                           duration = (int) (Float.parseFloat(txtDuration.getText().toString())*1000);
                           Log.w(TAG, String.valueOf(duration));
                       }
                       else Toast.makeText(getContext(),"Bạn Chưa nhập Thời gian",Toast.LENGTH_SHORT).show();
                    }
                }).create();
        builder.show();
    }

    private void addControls(View view) {
        range_seekbar = view.findViewById(R.id.range_seekbar);
        txtName = view.findViewById(R.id.txtNameVideo);
        txtCurrentTime = view.findViewById(R.id.txtCurrentTime);
        txtEndTime = view.findViewById(R.id.txtEndTime);
        imgController = view.findViewById(R.id.imgControlsVideo);
        vdViewTimeCapture = view.findViewById(R.id.vdViewTimeCapture);
        btnSetTime = view.findViewById(R.id.btnSetTime);
        btnDone = view.findViewById(R.id.btnDone);
        btnSnap = view.findViewById(R.id.btnSnap);
        imgSnap = view.findViewById(R.id.imgSnap);
        btnSetTime.setText("Snap every "+(float)duration/1000+" sec");
    }

    private void createImage(Bitmap bmFrame) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/ScreenShots");
        if(!file.exists()){
            file.mkdirs();
        }
        DateTimeFormatter formatter = null;
        String fileName = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            fileName = String.format(formatter.format(now)+endWiths);
        }
        else fileName = System.currentTimeMillis()+endWiths;
        Bitmap.CompressFormat bitmap = Bitmap.CompressFormat.JPEG;
        if(type.equals("JPG")){
            endWiths = ".jpg";
            bitmap = Bitmap.CompressFormat.JPEG;
        }
        if(type.equals("PNG")){
            endWiths = ".png";
            bitmap = Bitmap.CompressFormat.PNG;
        }
        File outFile = new File(file,fileName);
        try {
            if(bmFrame!=null) {
                FileOutputStream fos = new FileOutputStream(outFile);
                bmFrame.compress(bitmap, setQuality(quality), fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            Log.w(TAG,e);
        } catch (IOException e) {
            Log.w(TAG,e);
        }
    }

    int setQuality(String quality){
        if(quality.equals(R.string.Best)){
            return 100;
        }
        if(quality.equals(R.string.VeryHigh)){
            return 85;
        }
        if(quality.equals(R.string.High)){
            return 75;
        }
        if(quality.equals(R.string.Medium)){
            return 65;
        }
        if(quality.equals(R.string.Low)){
            return 50;
        }
        return 75;
    }

    private Handler handler =  new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            txtCurrentTime.setText(MilisecondsToTimer(msg.what/1000));
            range_seekbar.setSelectedMinValue(msg.what/1000);
        }
    };

    String MilisecondsToTimer(long milisec) {
        String finalTimerString = "";
        String hoursString = "";
        String secondString;
        String minuteString;
        int seconds = (int) milisec % 60;
        int minutes = (int) milisec / 60;
        int hours = (int) milisec / (60 * 60);
        if (hours > 0) {
            hoursString = hours + ":";
        }
        if (seconds < 10) {
            secondString = "0" + seconds;
        } else secondString = "" + seconds;
        if (minutes < 10) {
            minuteString = "0" + minutes;
        } else minuteString = "" + minutes;
        finalTimerString = hoursString + minuteString + ":" + secondString;
        return finalTimerString;
    }
}
