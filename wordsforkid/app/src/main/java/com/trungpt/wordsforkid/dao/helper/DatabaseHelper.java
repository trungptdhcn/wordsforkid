package com.trungpt.wordsforkid.dao.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.trungpt.wordsforkid.model.FolderEntity;
import com.trungpt.wordsforkid.model.WordEntity;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper
{
// ------------------------------ FIELDS ------------------------------

    public static String DATABASE_NAME = "wordsforkids.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;
    private Dao<FolderEntity, Integer> folderEntityDao;
    private Dao<WordEntity, Integer> wordEntityDao;

// --------------------------- CONSTRUCTORS ---------------------------

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public Dao<FolderEntity, Integer> getFolderEntityDao() throws SQLException
    {
        if (folderEntityDao == null)
        {
            folderEntityDao = getDao(FolderEntity.class);
            ((BaseDaoImpl) folderEntityDao).initialize();
        }
        return folderEntityDao;
    }

    public Dao<WordEntity, Integer> getWordEntityDao() throws SQLException
    {
        if (wordEntityDao == null)
        {
            wordEntityDao = getDao(WordEntity.class);
            ((BaseDaoImpl) wordEntityDao).initialize();
        }
        return wordEntityDao;
    }

// -------------------------- OTHER METHODS --------------------------
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource)
    {
        try
        {
            TableUtils.createTable(connectionSource, FolderEntity.class);
            TableUtils.createTable(connectionSource, WordEntity.class);
        }
        catch (SQLException e)
        {
            Log.e(this.getClass().getName(), e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion,
                          int newVersion)
    {
        try
        {
            TableUtils.dropTable(connectionSource, FolderEntity.class, true);
            TableUtils.dropTable(connectionSource, WordEntity.class, true);
            TableUtils.createTable(connectionSource, FolderEntity.class);
            TableUtils.createTable(connectionSource, WordEntity.class);

        }
        catch (SQLException e)
        {
        }
    }

//    public void dropAllDatabase() throws SQLException
//    {
//        TableUtils.dropTable(connectionSource, Vehicle.class, true);
//        TableUtils.dropTable(connectionSource, Witness.class, true);
//        TableUtils.dropTable(connectionSource, Police.class, true);
//        TableUtils.dropTable(connectionSource, Image.class, true);
//
//    }
//
//    public void createTables() throws SQLException
//    {
//        TableUtils.createTable(connectionSource, Vehicle.class);
//        TableUtils.createTable(connectionSource, Witness.class);
//        TableUtils.createTable(connectionSource, Police.class);
//        TableUtils.createTable(connectionSource, Image.class);
//    }
}
