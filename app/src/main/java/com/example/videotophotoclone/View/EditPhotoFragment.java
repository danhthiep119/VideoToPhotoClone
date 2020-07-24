package com.example.videotophotoclone.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.videotophotoclone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class EditPhotoFragment extends Fragment {
    BottomNavigationView bottomNavigationView;
    static String path="";
    ConstraintLayout customLayout;
    PhotoEditor mPhotoEditor;
    Typeface mTextFont;
    LinearLayout textZone,brushZone;
    EditText txtInsertText;
    Button btnOk,btnColor,btnErase;
    ImageButton btnInfo;
    SeekBar sizeBrush;
    PhotoEditorView mPhotoEditorView;
    CropImageView mCropImage;
    final int PIC_CROP=1;
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
        mPhotoEditorView = view.findViewById(R.id.photo_editor);
        mCropImage = view.findViewById(R.id.crop_image);
        sizeBrush = view.findViewById(R.id.seek_bar_size_brush);
        txtInsertText = view.findViewById(R.id.txtInsertText);
        btnOk = view.findViewById(R.id.btnOk);
        btnInfo = view.findViewById(R.id.info_image);
        btnErase = view.findViewById(R.id.btnErase);

        textZone = view.findViewById(R.id.textZone);
        textZone.setVisibility(View.INVISIBLE);

        brushZone = view.findViewById(R.id.brush_zone);
        brushZone.setVisibility(View.INVISIBLE);

        customLayout = view.findViewById(R.id.CustomLayout);
        path = getArguments().getString("IMAGEPATH");
        File file = new File(path);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        mPhotoEditorView.getSource().setImageBitmap(bitmap);
//        Typeface mTextFont = ResourcesCompat.getFont(getContext(),R.font.greatvibes_regular);
        mPhotoEditor =new PhotoEditor.Builder(getContext(),mPhotoEditorView)
                .setPinchTextScalable(true)
//                .setDefaultTextTypeface(mTextFont)
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
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        mPhotoEditorView.getSource().setImageBitmap(bitmap);
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
        @RequiresApi(api = Build.VERSION_CODES.O)
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
                    dialog.setTitle(R.string.Delete).setMessage("Bạn Có muốn xóa ko?").setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
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
                    mPhotoEditor.setBrushDrawingMode(false);
                    mPhotoEditorView.setVisibility(View.VISIBLE);
                    textZone.setVisibility(View.VISIBLE);
                    brushZone.setVisibility(View.INVISIBLE);
                    mCropImage.setVisibility(View.INVISIBLE);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPhotoEditor.addText(mTextFont,txtInsertText.getText().toString(),Color.parseColor("#FFFFFF"));
                            txtInsertText.setText("");
                        }
                    });
                    break;
                case R.id.nav_paint:
                    mPhotoEditorView.setVisibility(View.VISIBLE);
                    textZone.setVisibility(View.INVISIBLE);
                    brushZone.setVisibility(View.VISIBLE);
                    mCropImage.setVisibility(View.INVISIBLE);
                    drawInImage();
                    break;
                case R.id.nav_cut:
                    mPhotoEditor.setBrushDrawingMode(false);
                    mPhotoEditorView.setVisibility(View.INVISIBLE);
                    textZone.setVisibility(View.INVISIBLE);
                    brushZone.setVisibility(View.INVISIBLE);
                    mCropImage.setVisibility(View.VISIBLE);
                    cropImage(mCropImage);
                    break;
                case R.id.nav_sticker:
                    mPhotoEditor.setBrushDrawingMode(false);
                    mPhotoEditorView.setVisibility(View.VISIBLE);
                    textZone.setVisibility(View.INVISIBLE);
                    brushZone.setVisibility(View.INVISIBLE);
                    mCropImage.setVisibility(View.INVISIBLE);
                    insertEmojiorImage();
                    break;
            }
            return true;
        }
    };

    private void cropImage(final CropImageView cropImage){
        File file = new File(path);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        cropImage.setImageBitmap(bitmap);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(path);
                cropImage.saveCroppedImageAsync(uri);
                Toast.makeText(getContext(),"Save Success!",Toast.LENGTH_SHORT).show();
            }
        });
        cropImage.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
            @Override
            public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
                Bitmap croped = result.getBitmap();
                mPhotoEditorView.getSource().setImageBitmap(croped);
            }
        });
    }

    private void insertEmojiorImage() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_folder_black_24dp);
        mPhotoEditor.addImage(bitmap);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void drawInImage() {
        mPhotoEditor.setBrushDrawingMode(true);
        sizeBrush.setMax((int) 24.0f);
        sizeBrush.setMin((int) 1.0f);
        sizeBrush.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mPhotoEditor.setBrushSize(progress);
                    mPhotoEditor.setBrushEraserSize(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btnErase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoEditor.brushEraser();
            }
        });
        mPhotoEditor.setBrushColor(Color.parseColor("#FFFFFF"));
    }
}
