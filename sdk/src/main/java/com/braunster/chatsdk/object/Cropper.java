package com.braunster.chatsdk.object;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.soundcloud.android.crop.Crop;
import com.soundcloud.android.crop.CropImageActivity;

public class Cropper extends Crop {
    private Uri source;

    static interface Extra {
        String ASPECT_X = "aspect_x";
        String ASPECT_Y = "aspect_y";
    }

    public Cropper(Uri source) {
        super(source);
        this.source = source;
    }

    /**
     * @return Intent that will open the crop activity with an adjustable bounds for the cropping square.
     * * * */
    public Intent getIntent(Context context, Uri output){
        Intent cropIntent = new Intent();
        cropIntent.setData(source);
        cropIntent.setClass(context, CropImageActivity.class);
        cropIntent.putExtra(Extra.ASPECT_X, 1);
        cropIntent.putExtra(Extra.ASPECT_Y, 1);
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, output);

        return cropIntent;
    }

    /**
     * @return Intent that will open the crop activity with an adjustable bounds for the cropping quare.
     * * * */
    public Intent getAdjustIntent(Context context, Uri output){
        Intent cropIntent = new Intent();
        cropIntent.setData(source);
        cropIntent.setClass(context, CropImageActivity.class);
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, output);

        return cropIntent;
    }
}
