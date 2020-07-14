package com.example.videotophotoclone.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.videotophotoclone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private File directory;
    public String[] allPath;
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton btnSlVideo,btnGallery,btnSlshowMaker,btnSetting;
        btnSlVideo=view.findViewById(R.id.btnSlVideo);
        btnGallery=view.findViewById(R.id.btnGallery);
        btnSlshowMaker=view.findViewById(R.id.btnSlshow);
        btnSetting=view.findViewById(R.id.btnSetting);
        getPathVideo();
        final NavController nav= Navigation.findNavController(view);
        btnSlVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav.navigate(R.id.action_mainFragment_to_selectedFragment);
            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav.navigate(R.id.action_mainFragment_to_galleryFragment);
            }
        });
        btnSlshowMaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav.navigate(R.id.action_mainFragment_to_slideshowMakerFragment);
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingDialog dialog=new SettingDialog(getContext());
                dialog.show();
            }
        });
        BottomNavigationView bottomNav= view.findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private void getPathVideo() {
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
             new BottomNavigationView.OnNavigationItemSelectedListener() {
         @Override
         public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
             switch (menuItem.getItemId()){
                 case R.id.Share:
                     try {
                         Intent shareIntent = new Intent(Intent.ACTION_SEND);
                         shareIntent.setType("text/plain");
                         shareIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                         shareIntent.putExtra(Intent.EXTRA_TEXT, "");
                         startActivity(Intent.createChooser(shareIntent, "choose one"));
                     } catch(Exception e) {
                         //e.toString();
                     }
                     break;
                 case R.id.About:
                     AboutDialog dialog= new AboutDialog(getContext());
                     dialog.show();
                     break;
                 case R.id.Rate:
                         startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.asquaremobileapps.videotoimageconverter")));

                     break;
             }
             return true;
         }

     };

}
