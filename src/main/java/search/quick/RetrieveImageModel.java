package search.quick;

import android.graphics.Bitmap;

/**
 * Created by Asus on 13-03-2019.
 */

public class RetrieveImageModel {
    Bitmap bitmap;

    public RetrieveImageModel(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
