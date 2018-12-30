package com.parsjavid.supernuts.di;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.parsjavid.supernuts.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by KingStar on 3/2/2018.
 */

@Module
public class ImageLoaderMoudle {

    public Context context;

    public ImageLoaderMoudle(Context context) {
        this.context = context;
    }


    @MainScope
    @Provides
    @Singleton
    public ImageLoader getImageLoader(DisplayImageOptions options) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .defaultDisplayImageOptions(options)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 2048 * 2048).build();

        ImageLoader.getInstance().init(config);
        return ImageLoader.getInstance();
    }

    @MainScope
    @Provides
    @Singleton
    public DisplayImageOptions options() {
        return new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .displayer(new RoundedBitmapDisplayer(1))
                .displayer(new FadeInBitmapDisplayer(500))
                .showImageOnLoading(R.drawable.ic_launcher)
                .cacheInMemory(true).cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }
}
