package com.trungpt.wordsforkid.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.trungpt.wordsforkid.MainActivity;
import com.trungpt.wordsforkid.R;
import com.trungpt.wordsforkid.dao.helper.DatabaseHelper;
import com.trungpt.wordsforkid.model.WordEntity;
import com.trungpt.wordsforkid.ui.Utils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SaveImageActivity extends ActionBarActivity
{
    private EditText editName;
    private ImageButton imbYes;
    private ImageButton imbNo;
    private ImageView ivTake;
    DisplayImageOptions options;
    DatabaseHelper dataHelper;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_image);
        options = new DisplayImageOptions.Builder()
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();
        dataHelper = new DatabaseHelper(this);
        editName = (EditText) findViewById(R.id.save_image_edtName);
        imbYes = (ImageButton) findViewById(R.id.btYes);
        imbNo = (ImageButton) findViewById(R.id.btNo);
        editName = (EditText) findViewById(R.id.save_image_edtName);
        ivTake = (ImageView) findViewById(R.id.save_image_ivImageSave);
        uri = getIntent().getData();
        Bitmap bmp = ImageLoader.getInstance().loadImageSync("file://" + uri.getPath(), options);
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();  // deprecated
        Bitmap destBitmap;
        if (width < height)
        {
            destBitmap = Utils.scaleCenterCrop(bmp, height / 2, width);
        }
        else
        {
            destBitmap = Utils.scaleCenterCrop(bmp, width / 2, height);
        }
        ivTake.setImageBitmap(destBitmap);
//        ImageLoader.getInstance().displayImage("file://" + uri.getPath(), ivTake, options);
        final Integer level = getIntent().getExtras().getInt("level");
        final Integer position = getIntent().getExtras().getInt("position");
        imbYes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                WordEntity wordEntity = new WordEntity();
                wordEntity.setFolderId(level);
                wordEntity.setId(level);
                wordEntity.setWord(editName.getText().toString());
                wordEntity.setUrl("file://" + uri.getPath());
                try
                {
                    if (editName.getText() != null && StringUtils.isNotEmpty(editName.getText().toString()))
                    {
                        List<WordEntity> wordEntityLocals = dataHelper.getWordEntityDao().queryForEq("word", editName.getText().toString());
                        if (wordEntityLocals == null || wordEntityLocals.size() <= 0)
                        {
                            wordEntity.setId(position);
                            dataHelper.getWordEntityDao().create(wordEntity);
                        }
                        else
                        {
                            wordEntity.setId(wordEntityLocals.get(0).getId());
                            dataHelper.getWordEntityDao().createOrUpdate(wordEntity);
                        }
                    }
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("level", level);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        File file = new File("file://" + uri.getPath());
        boolean deleted = file.delete();
    }
}
