package com.maraujo.requestproject.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.maraujo.requestproject.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by maraujo on 6/20/17.
 */

public class FileUtils {

    public static final String saveImageInFile(Context context, Bitmap bitmap, String fileUrl) {
        String filename = fileUrl.replaceAll(".*/([^/]+)", "$1");
        File file = new File(context.getFilesDir(), filename);
        if(file.exists()) {
            file.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            return file.getPath();
        } catch (Exception ex) {
            Log.e(FileUtils.class.getSimpleName(), ex.getLocalizedMessage(), ex);
        }
        return null;
    }

    public static final void deleteImageInFile(Context context, String fileUrl) {
        String filename = fileUrl.replaceAll(".*/([^/]+)", "$1");
        File file = new File(context.getFilesDir(), filename);
        if (file.exists()) {
            file.delete();
        }
    }

    public static final void rawToFile(File file, InputStream inputStream) {
        //File file = new File(this.getFilesDir() + File.separator + "DefaultProperties.xml");
        try {
            //InputStream inputStream = resources.openRawResource(R.raw.);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            byte buf[]=new byte[1024];
            int len;
            while((len=inputStream.read(buf))>0) {
                fileOutputStream.write(buf,0,len);
            }

            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e1) {}
    }
}
