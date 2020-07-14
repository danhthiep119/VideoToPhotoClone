package com.example.videotophotoclone.Model;

import android.graphics.Bitmap;

public class TypeSetting {
    public static String type = "JPG";
    public Bitmap.CompressFormat bitmap = setChangeFileFormat(type);
    public int[] quality = {50, 75, 100, 150, 200};
    public float[] size = {0.5f, 1f, 1.5f, 2f, 3f};

    public static Bitmap.CompressFormat setChangeFileFormat(String item) {
        Bitmap.CompressFormat bitmapFormat = null;
        if (item.equals("JPG")) {
            bitmapFormat = Bitmap.CompressFormat.JPEG;
        } else if (item.equals("PNG")) {
            bitmapFormat = Bitmap.CompressFormat.PNG;
        }
        return bitmapFormat;
    }
}
