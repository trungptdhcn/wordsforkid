package com.trungpt.wordsforkid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.trungpt.wordsforkid.dao.helper.DatabaseHelper;
import com.trungpt.wordsforkid.model.FolderEntity;

import java.sql.SQLException;

/**
 * Created by Trung on 3/25/2015.
 */
public class SplashScreen extends Activity
{
    private final int SPLASH_DISPLAY_LENGTH = 5000;

    public void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        DatabaseHelper dataHelper = new DatabaseHelper(this);
        for (int i = 0; i < 10; i++)
        {
            FolderEntity folderEntity = new FolderEntity();
            folderEntity.setId(i);
            folderEntity.setFolderName("Bài " + (i + 1));
            try
            {
                dataHelper.getFolderEntityDao().createOrUpdate(folderEntity);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        StartAnimations();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent mainIntent = new Intent(SplashScreen.this, MainMenuScreen.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    private void StartAnimations()
    {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim);
    }

}
