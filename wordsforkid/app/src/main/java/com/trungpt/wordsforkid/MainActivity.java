package com.trungpt.wordsforkid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.trungpt.wordsforkid.dao.helper.DatabaseHelper;
import com.trungpt.wordsforkid.model.WordEntity;
import com.trungpt.wordsforkid.ui.Utils;
import com.trungpt.wordsforkid.ui.activity.AboutActivity;
import com.trungpt.wordsforkid.ui.activity.SaveImageActivity;
import com.trungpt.wordsforkid.ui.adapter.SwipeFlingAdapter;
import com.trungpt.wordsforkid.ui.adapter.ViewPagerAdapter;

import java.io.File;
import java.sql.SQLException;
import java.util.*;


public class MainActivity extends Activity
{
    private ImageButton ivTakePhoto;
    ImageView ivPicture;
    SwipeFlingAdapter adapter;
    public static int REQUEST_IMAGE_CAPTURE = 111;
    Uri uriSavedImage;
    DatabaseHelper dataHelper;
    Integer level;
    List<WordEntity> wordEntityList;
    DisplayImageOptions options;
    EditText editTextWord;
    ImageButton btNext;
    ImageButton btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataHelper = new DatabaseHelper(this);
        level = getIntent().getExtras().getInt("level");
        ivTakePhoto = (ImageButton) findViewById(R.id.imbTakePhoto);
        editTextWord = (EditText) findViewById(R.id.activity_main_edtWord);
        ivPicture = (ImageView) findViewById(R.id.activity_main_ivPicture);
        btNext = (ImageButton) findViewById(R.id.activity_main_edtNext);
        btBack = (ImageButton) findViewById(R.id.activity_main_edtBack);
        final Display display = getWindowManager().getDefaultDisplay();
        final int width = display.getWidth();  // deprecated
        final int height = display.getHeight();
        Bitmap bitmapDefaultDes;
        final Bitmap bmpDefault = ((BitmapDrawable) this.getResources().getDrawable(R.drawable.image_not_found)).getBitmap();
        if (width < height)
        {
            bitmapDefaultDes = Utils.scaleCenterCrop(bmpDefault, height / 2, width);
        }
        else
        {
            bitmapDefaultDes = Utils.scaleCenterCrop(bmpDefault, width / 2, height);
        }
        ivPicture.setImageBitmap(bitmapDefaultDes);
        wordEntityList = new ArrayList<>();
        new DisplayImageOptions.Builder()
                .showImageOnLoading(0)
                .showImageForEmptyUri(0)
                .showImageOnFail(0)
                .delayBeforeLoading(100)
                .cacheOnDisc(false)
                .cacheInMemory(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(1000))
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();
        try
        {
            wordEntityList = dataHelper.getWordEntityDao().queryForEq("folderId", level);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Collections.shuffle(wordEntityList);
        final Map<String, WordEntity> mapWords = new LinkedHashMap<>();
        for (WordEntity wordEntity : wordEntityList)
        {
            mapWords.put(wordEntity.getWord(), wordEntity);
        }
        if (mapWords != null && mapWords.size() > 0)
        {
            editTextWord.setText(wordEntityList.get(0).getWord());
        }
        ivTakePhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                File imagesFolder = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.trungpt.wordsforkid/b1/");
                imagesFolder.mkdirs();
                File image = new File(imagesFolder, "image_" + wordEntityList.size() + ".jpg");
                uriSavedImage = Uri.fromFile(image);
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null)
                {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        ivPicture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                WordEntity wordEntity = mapWords.get(editTextWord.getText().toString());
                Bitmap bmp = ImageLoader.getInstance().loadImageSync(wordEntity.getUrl(), options);
                Bitmap destBitmap;
                if (width < height)
                {
                    destBitmap = Utils.scaleCenterCrop(bmp, height / 2, width);
                }
                else
                {
                    destBitmap = Utils.scaleCenterCrop(bmp, width / 2, height);
                }
                ivPicture.setImageBitmap(destBitmap);
            }
        });
        btNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                WordEntity wordEntity = mapWords.
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Intent intent = new Intent(this, SaveImageActivity.class);
            intent.setData(uriSavedImage);
            intent.putExtra("level", level);
            intent.putExtra("position", wordEntityList.size());
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }
}
