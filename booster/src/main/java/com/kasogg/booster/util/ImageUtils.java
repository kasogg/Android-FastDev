package com.kasogg.booster.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    private ImageUtils() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    /**
     * 90°翻转图像
     *
     * @param angle
     * @return Bitmap
     */
    public static Bitmap rotate(Bitmap bitmap, int angle) {
        Matrix m = new Matrix();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        m.setRotate(angle); // 旋转angle度
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);// 从新生成图片
        return bitmap;
    }

    /*
     * 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, (float) pixels, (float) pixels, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /*
     * Resource转Uri
     */
    public static Uri getDrawableUri(Context mContext, int id) {
        Resources r = mContext.getResources();
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + r.getResourcePackageName(id) + "/" + r.getResourceTypeName(id) + "/" + r.getResourceEntryName(id));
    }

    public static void saveBitmap(File f, Bitmap mBitmap) {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(f));
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            if (bos != null) {
                bos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 缩放Bitmap
     *
     * @param bitmap
     * @param width
     * @param height
     * @return Bitmap
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }

    /**
     * 等比缩放Bitmap
     *
     * @param bitmap
     * @param ratio
     * @return Bitmap
     */
    public static Bitmap zoomRatioBitmap(Bitmap bitmap, int ratio) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(ratio, ratio);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 768, 1024);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }
}