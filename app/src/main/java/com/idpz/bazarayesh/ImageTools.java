package com.idpz.bazarayesh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;


import com.idpz.bazarayesh.Utils.crop.CropUtil;

import java.io.File;


public class ImageTools {


    public static Bitmap createBitmap(byte[] DataBlobs, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = true;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(DataBlobs, 0, DataBlobs.length, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(DataBlobs, 0, DataBlobs.length, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap rotateBitmap(File src, Bitmap bitmap) {
        int orientation = CropUtil.getExifRotation(src);

        if (orientation == 1) {
            return bitmap;
        }

        Matrix matrix = new Matrix();
        switch (orientation) {
            case 2:
                matrix.setScale(-1, 1);
                break;
            case 3:
                matrix.setRotate(180);
                break;
            case 4:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case 5:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case 6:
                matrix.setRotate(90);
                break;
            case 7:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case 8:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }

        try {
            Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return oriented;
        } catch (OutOfMemoryError e) {
//            Events.error(e);
            return bitmap;
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static int getDominantColor(Bitmap bitmap) {

        long redBucket = 0;
        long greenBucket = 0;
        long blueBucket = 0;
        long pixelCount = 0;

        for (int y = 0; y < bitmap.getHeight(); y++) {

            for (int x = 0; x < bitmap.getWidth(); x++) {

                int c = bitmap.getPixel(x, y);

                pixelCount++;
                redBucket += Color.red(c);
                greenBucket += Color.green(c);
                blueBucket += Color.blue(c);
                // does alpha matter?
            }
        }

        return Color.rgb((int)(redBucket / pixelCount),
                (int)(greenBucket / pixelCount),
                (int)(blueBucket / pixelCount));

        //Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
        //final int color = newBitmap.getPixel(0, 0);
        //newBitmap.recycle();
        //return color;
    }

    public static Bitmap rs(Context context, Bitmap bitmap, int radius) throws RSRuntimeException {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            RenderScript rs = null;

            try {

                rs = RenderScript.create(context);
                rs.setMessageHandler(new RenderScript.RSMessageHandler());
                Allocation input = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
                Allocation output = Allocation.createTyped(rs, input.getType());
                ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));


                blur.setInput(input);

                blur.setRadius(radius);
                blur.forEach(output);
                output.copyTo(bitmap);

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                if (rs != null) {
                    rs.destroy();
                }
            }
        }
        return bitmap;
    }
}
