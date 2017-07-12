package com.example.alex.nasapp.helpers;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.view.View;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageHelper {

public static Bitmap createBitmapFromView (View view) {
    Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
            Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    view.draw(canvas);

    return bitmap;
}

public static File convertBitmapIntoJPEG (Context context, Bitmap bitmap) {
    if (isExternalStorageAvailable()) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "postcard_"+timeStamp;
        File mediaStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try {
            File file = File.createTempFile(fileName, ".jpg", mediaStorageDir);
            FileOutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    return null;
}

    private static boolean isExternalStorageAvailable () {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}

