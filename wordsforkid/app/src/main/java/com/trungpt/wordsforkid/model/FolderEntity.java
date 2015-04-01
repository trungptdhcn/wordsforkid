package com.trungpt.wordsforkid.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Trung on 3/25/2015.
 */
@DatabaseTable(tableName = "folder")
public class FolderEntity
{
    @DatabaseField(id = true)
    private Integer id;
    @DatabaseField
    private String folderName;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getFolderName()
    {
        return folderName;
    }

    public void setFolderName(String folderName)
    {
        this.folderName = folderName;
    }
}
