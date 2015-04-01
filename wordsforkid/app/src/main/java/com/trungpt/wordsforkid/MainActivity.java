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
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends Activity
{
    private ImageButton ivTakePhoto;
    ImageView ivPicture;
    //    ViewPager viewPager;
//    PagerAdapter pagerAdapter;
    SwipeFlingAdapter adapter;
    public static int REQUEST_IMAGE_CAPTURE = 111;
    Uri uriSavedImage;
    DatabaseHelper dataHelper;
    Integer level;
    List<WordEntity> wordEntityList;
    DisplayImageOptions options;
    EditText editTextWord;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataHelper = new DatabaseHelper(this);
//        viewPager = (ViewPager) findViewById(R.id.pager);
        level = getIntent().getExtras().getInt("level");
        ivTakePhoto = (ImageButton) findViewById(R.id.imbTakePhoto);
        editTextWord = (EditText) findViewById(R.id.activity_main_edtWord);
        ivPicture = (ImageView) findViewById(R.id.activity_main_ivPicture);
        final Bitmap bmpDefault = ((BitmapDrawable) this.getResources().getDrawable(R.drawable.image_not_found)).getBitmap();
        ivPicture.setImageBitmap(bmpDefault);
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
        editTextWord.setText(wordEntityList.get(0).getWord());
//        pagerAdapter = new ViewPagerAdapter(this, wordEntityList);
//        viewPager.setAdapter(pagerAdapter);
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
//        {
//            public void onPageScrollStateChanged(int state)
//            {
//            }
//
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
//            {
//            }
//
//            public void onPageSelected(int position)
//            {
//                ivPicture.setImageBitmap(bmpDefault);
//            }
//        });
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
//                int position = viewPager.getCurrentItem();
//                Bitmap bmp = ImageLoader.getInstance().loadImageSync(wordEntityList.get(position).getUrl(), options);
                Display display = getWindowManager().getDefaultDisplay();
                int width = display.getWidth();  // deprecated
                int height = display.getHeight();  // deprecated
                Bitmap destBitmap;
//                if (width < height)
//                {
//                    destBitmap = Utils.scaleCenterCrop(bmp, height / 2, width);
//                }
//                else
//                {
//                    destBitmap = Utils.scaleCenterCrop(bmp, width / 2, height);
//                }
//                ivPicture.setImageBitmap(destBitmap);
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
