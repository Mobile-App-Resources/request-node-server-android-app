package com.maraujo.requestproject.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by maraujo on 8/2/17.
 */

public class ImageUtils {

    public static Bitmap fixOrientation(String imagePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        ExifInterface ei;

        try {
            ei = new ExifInterface(imagePath);
        } catch (IOException e) {
            Log.e("PictureUtils", e.getLocalizedMessage(), e);
            return bitmap;
        }

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        Bitmap rotatedBitmap;

        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:

            default:
                rotatedBitmap = bitmap;
                break;
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(imagePath);
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            Log.e("PictureUtils", e.getLocalizedMessage(), e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Log.e("PictureUtils", e.getLocalizedMessage(), e);
            }
        }

        return rotatedBitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
