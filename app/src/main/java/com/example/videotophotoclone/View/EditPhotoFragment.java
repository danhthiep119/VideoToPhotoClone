package com.example.videotophotoclone.View;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videotophotoclone.Controler.ColorTextAdapter;
import com.example.videotophotoclone.Controler.StickerRecycleAdapter;
import com.example.videotophotoclone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DecimalFormat;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class EditPhotoFragment extends Fragment {
    BottomNavigationView bottomNavigationView;
    static String path = "";
    ConstraintLayout customLayout;
    PhotoEditor mPhotoEditor;
    Typeface mTextFont;
    LinearLayout textZone, brushZone;
    EditText txtInsertText;
    Button btnOk, btnColor, btnErase, btnCropImage,btnColorText;
    ImageButton btnInfo, btnSave;
    SeekBar sizeBrush;
    PhotoEditorView mPhotoEditorView;
    CropImageView mCropImage;
    RecyclerView rvSticker;
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
        btnSave = view.findViewById(R.id.btnSave);
        btnErase = view.findViewById(R.id.btnErase);
        btnColor = view.findViewById(R.id.btnColor);
        btnColorText = view.findViewById(R.id.btnColorText);
        btnCropImage = view.findViewById(R.id.btnCropImage);
        rvSticker = view.findViewById(R.id.rvSticker);
        textZone = view.findViewById(R.id.textZone);
        textZone.setVisibility(View.INVISIBLE);
        brushZone = view.findViewById(R.id.brush_zone);
        brushZone.setVisibility(View.INVISIBLE);
        customLayout = view.findViewById(R.id.CustomLayout);
        path = getArguments().getString("IMAGEPATH");
        File file = new File(path);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        mPhotoEditorView.getSource().setImageBitmap(bitmap);
        mPhotoEditor = new PhotoEditor.Builder(getContext(), mPhotoEditorView)
                .setPinchTextScalable(true)
                .build();
        bottomNavigationView = view.findViewById(R.id.nav_bottom_edit);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mPhotoEditor.saveAsFile(path, new PhotoEditor.OnSaveListener() {
                    @Override
                    public void onSuccess(@NonNull String imagePath) {
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        mPhotoEditorView.getSource().setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        try {
            File file = new File(path);
            double size = file.length() / (1024 * 1024);
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String endwith = "";
            String filesize = "";
            BasicFileAttributes attr = null;
            FileTime creationTime = null;
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            if(size>1024){
                decimalFormat = new DecimalFormat("#.00");
                filesize = decimalFormat.format(size);
                endwith = " MB";
            }
            else {
                filesize = String.valueOf(size);
                endwith = " Kb";
            }
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                attr = Files.readAttributes(Paths.get(path), BasicFileAttributes.class);
                creationTime = attr.creationTime();
                dialog.setTitle(R.string.Details).setMessage("" +
                        getContext().getResources().getString(R.string.FileName) + ":\n" + file.getName() +
                        "\n" + getContext().getResources().getString(R.string.FileSize) + ":\n" + filesize + endwith +
                        "\n"+ R.string.Resolution+":\n"+bitmap.getHeight()+" x "+bitmap.getWidth()+
                        "\nDate:\n" + attr.creationTime() +
                        "\n" + getContext().getResources().getString(+R.string.Path) + ":\n" + path)
                        .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
            } else {
                dialog.setTitle(R.string.Details).setMessage("" +
                        getContext().getResources().getString(R.string.FileName) + ":\n" + file.getName() +
                        "\n" + getContext().getResources().getString(R.string.FileSize) + ":\n" + filesize + endwith +
                        "\n"+ R.string.Resolution+":\n"+bitmap.getWidth()+" x "+bitmap.getHeight()+
                        "\n" + getContext().getResources().getString(R.string.Path) + ":\n" + path)
                        .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
            }
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_edit:
                    bottomNavigationView.getMenu().removeItem(R.id.nav_share);
                    bottomNavigationView.getMenu().removeItem(R.id.nav_delete);
                    bottomNavigationView.getMenu().removeItem(R.id.nav_edit);
                    bottomNavigationView.inflateMenu(R.menu.item_edit_bottom);
                    break;
                case R.id.nav_delete:
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle(R.string.Delete).setMessage(getContext().getResources().getString(R.string.DeleteQuestion)).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
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
                    rvSticker.setVisibility(View.INVISIBLE);
                    btnCropImage.setVisibility(View.INVISIBLE);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPhotoEditor.addText(mTextFont, txtInsertText.getText().toString(), Color.parseColor("#FFFFFF"));
                            txtInsertText.setText("");
                        }
                    });
                    btnColorText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextColorDialog dialog = new TextColorDialog(getActivity(),btnColorText,txtInsertText,mPhotoEditor);
                            dialog.show();
                        }
                    });
                    break;
                case R.id.nav_paint:
                    mPhotoEditorView.setVisibility(View.VISIBLE);
                    textZone.setVisibility(View.INVISIBLE);
                    brushZone.setVisibility(View.VISIBLE);
                    mCropImage.setVisibility(View.INVISIBLE);
                    rvSticker.setVisibility(View.INVISIBLE);
                    btnCropImage.setVisibility(View.INVISIBLE);
                    drawInImage();
                    break;
                case R.id.nav_cut:
                    mPhotoEditor.setBrushDrawingMode(false);
                    mPhotoEditorView.setVisibility(View.INVISIBLE);
                    textZone.setVisibility(View.INVISIBLE);
                    brushZone.setVisibility(View.INVISIBLE);
                    mCropImage.setVisibility(View.VISIBLE);
                    rvSticker.setVisibility(View.INVISIBLE);
                    btnCropImage.setVisibility(View.VISIBLE);
                    cropImage(mCropImage, mPhotoEditorView, mPhotoEditor);
                    break;
                case R.id.nav_sticker:
                    mPhotoEditor.setBrushDrawingMode(false);
                    mPhotoEditorView.setVisibility(View.VISIBLE);
                    textZone.setVisibility(View.INVISIBLE);
                    brushZone.setVisibility(View.INVISIBLE);
                    mCropImage.setVisibility(View.INVISIBLE);
                    rvSticker.setVisibility(View.VISIBLE);
                    btnCropImage.setVisibility(View.INVISIBLE);
                    insertEmojiorImage();
                    break;
            }
            return true;
        }
    };

    void cropImage(final CropImageView mCropImage, final PhotoEditorView view, final PhotoEditor editor) {
        File file = new File(path);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        mCropImage.setImageBitmap(bitmap);
        Rect rect = new Rect(3, 1, 1, 3);
        mCropImage.setCropRect(rect);
        btnCropImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Cắt", Toast.LENGTH_SHORT).show();
                Bitmap croped = mCropImage.getCroppedImage();
                mCropImage.setImageBitmap(croped);
                view.getSource().setImageBitmap(croped);
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                editor.saveAsFile(path, new PhotoEditor.OnSaveListener() {
                    @Override
                    public void onSuccess(@NonNull String imagePath) {
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        mPhotoEditorView.getSource().setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void insertEmojiorImage() {
        int[] sticker = {
                R.drawable.emoji_1,
                R.drawable.emoji_2,
                R.drawable.emoji_3,
                R.drawable.emoji_4,
                R.drawable.emoji_5
        };
        StickerRecycleAdapter adapter = new StickerRecycleAdapter(mPhotoEditor,getContext(),sticker);
        LinearLayoutManager manager = new  LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvSticker.setLayoutManager(manager);
        rvSticker.setHasFixedSize(true);
        rvSticker.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void drawInImage() {
        final String color = "#000000";
        mPhotoEditor.setBrushDrawingMode(true);
        sizeBrush.setMax((int) 24.0f);
        sizeBrush.setMin((int) 1.0f);
        mPhotoEditor.setBrushSize(1);
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
        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDialog dialog = new ColorDialog(getActivity(),btnColor,mPhotoEditor);
                dialog.show();
                System.out.println(color);
            }
        });
        mPhotoEditor.setBrushColor(Color.parseColor(color));
    }
}
