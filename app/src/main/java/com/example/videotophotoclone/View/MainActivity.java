package com.example.videotophotoclone.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;

import com.example.videotophotoclone.R;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    public Toolbar toolbar;
    final int REQUEST_CODE = 1;
    final String defType = "JPG";
    final String defQuality = "High";
    final String TAG = "Main Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestPermissions();
        createFolder();
        readDataSharedReference();
        toolbar = findViewById(R.id.my_toolbar);
        //Điều hướng navigation
        navController= Navigation.findNavController(this,R.id.fragment);
        AppBarConfiguration configuration=new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar,navController,configuration);
    }

    private void readDataSharedReference() {
        SharedPreferences sharePref = this.getPreferences(Context.MODE_PRIVATE);
    }

    private void createFolder() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Videos");
        File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/ScreenShots");
        if(!file.exists()){
            file.mkdirs();
        }
        if(!file2.exists()){
            file2.mkdirs();
        }
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

            }
            else requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE);
            if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            } else  requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }
}
