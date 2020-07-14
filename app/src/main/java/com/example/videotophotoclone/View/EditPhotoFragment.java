package com.example.videotophotoclone.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.videotophotoclone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class EditPhotoFragment extends Fragment {
    BottomNavigationView bottomNavigationView;
    static String path="";
    ConstraintLayout customLayout;
    PhotoEditor mPhotoEditor;
    Typeface mTextFont;
    LinearLayout textZone;
    EditText txtInsertText;
    Button btnOk;
    ImageButton btnInfo;
    public EditPhotoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.photo_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ImageView imgShow = view.findViewById(R.id.imgShow);
        PhotoEditorView mPhotoEditorView = view.findViewById(R.id.photo_editor);

        txtInsertText = view.findViewById(R.id.txtInsertText);
        btnOk = view.findViewById(R.id.btnOk);
        btnInfo = view.findViewById(R.id.info_image);

        textZone = view.findViewById(R.id.textZone);
        textZone.setVisibility(View.INVISIBLE);
        customLayout = view.findViewById(R.id.CustomLayout);
        path = getArguments().getString("IMAGEPATH");
        File file = new File(path);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//        imgShow.setImageBitmap(bitmap);
        mPhotoEditorView.getSource().setImageBitmap(bitmap);
        Typeface mTextFont = ResourcesCompat.getFont(getContext(),R.font.greatvibes_regular);
        mPhotoEditor =new PhotoEditor.Builder(getContext(),mPhotoEditorView)
                .setPinchTextScalable(true)
                .setDefaultTextTypeface(mTextFont)
                .build();
        bottomNavigationView = view.findViewById(R.id.nav_bottom_edit);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoEditor.saveAsFile(path, new PhotoEditor.OnSaveListener() {
                    @Override
                    public void onSuccess(@NonNull String imagePath) {
                        Toast.makeText(getContext(),"Saved",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getContext(),"Failed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.nav_edit:
                    bottomNavigationView.getMenu().removeItem(R.id.nav_share);
                    bottomNavigationView.getMenu().removeItem(R.id.nav_delete);
                    bottomNavigationView.getMenu().removeItem(R.id.nav_edit);
                    bottomNavigationView.inflateMenu(R.menu.item_edit_bottom);
                    break;
                case  R.id.nav_delete:
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle(R.string.Rename).setMessage("Bạn Có muốn xóa ko?").setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            File file = new File(path);
                            file.delete();
                            path = "";
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                    break;
                case R.id.nav_text:
                    textZone.setVisibility(View.VISIBLE);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPhotoEditor.addText(mTextFont,txtInsertText.getText().toString(),Color.parseColor("#FFFFFF"));
                            txtInsertText.setText("");
                        }
                    });

                    break;
                case R.id.nav_paint:
                    textZone.setVisibility(View.INVISIBLE);
                    mPhotoEditor.setBrushDrawingMode(true);
                    mPhotoEditor.setBrushSize(18.0f);
                    mPhotoEditor.setBrushColor(Color.parseColor("#FFFFFF"));
                    break;
                case R.id.nav_cut:
                    textZone.setVisibility(View.INVISIBLE);
                    break;
                case R.id.nav_sticker:
                    textZone.setVisibility(View.INVISIBLE);
                    break;
            }
            return true;
        }
    };


}
