package com.trungpt.wordsforkid;

        import android.annotation.TargetApi;
        import android.app.Application;
        import android.content.Context;
        import android.os.Build;
        import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
        import com.nostra13.universalimageloader.core.ImageLoader;
        import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
        import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Trung on 3/27/2015.
 */
public class MyApplication extends Application
{
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressWarnings("unused")
    @Override
    public void onCreate()
    {
        super.onCreate();
        initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context context)
    {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        ImageLoader.getInstance().init(config.build());
    }
}
