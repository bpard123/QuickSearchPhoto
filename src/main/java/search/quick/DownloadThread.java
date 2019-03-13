package search.quick;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Asus on 12-03-2019.
 */
public class DownloadThread extends AsyncTask<String,Void,byte[]> {


    @Override
    protected byte[] doInBackground(String... strings) {
        String imageURL = strings[0];
        byte imageInByte[]=null;
        Bitmap bitmap = null;
        try {
            // Download Image from URL
            InputStream input = new java.net.URL(imageURL).openStream();
            // Decode Bitmap
            bitmap = BitmapFactory.decodeStream(input);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            imageInByte=stream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageInByte;
    }
}
