package com.trungpt.wordsforkid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.trungpt.wordsforkid.dao.helper.DatabaseHelper;
import com.trungpt.wordsforkid.model.FolderEntity;
import com.trungpt.wordsforkid.ui.adapter.ListLevelAdapter;

import java.sql.SQLException;
import java.util.List;


public class MainMenuScreen extends Activity
{
    List<FolderEntity> folderEntityList;
    ListView lvLevel;
    ListLevelAdapter adapter;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_screen);
        lvLevel = (ListView) findViewById(R.id.main_menu_screen_lvListLevel);
        DatabaseHelper dataHelper = new DatabaseHelper(this);
        try
        {
            folderEntityList = dataHelper.getFolderEntityDao().queryForAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        adapter = new ListLevelAdapter(this, folderEntityList);
        lvLevel.setAdapter(adapter);
        lvLevel.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(MainMenuScreen.this, MainActivity.class);
                intent.putExtra("level", position);
                startActivity(intent);
            }
        });
    }
}
