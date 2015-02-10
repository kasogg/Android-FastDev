package cn.kasogg.common.util;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageUtils {
    private volatile static ImageLoader mImageLoader;

    private ImageUtils() {
        throw new AssertionError();
    }

    public static void init(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
        ImageLoader.getInstance().init(config);
    }

    public static void displayImage(String uri, ImageView imageView) {
        mImageLoader.displayImage(uri, imageView);
    }

    public static void displayImage(String uri, ImageView imageView, DisplayImageOptions options) {
        mImageLoader.displayImage(uri, imageView, options);
    }

}