package com.trungpt.wordsforkid.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Trung on 3/25/2015.
 */
@DatabaseTable(tableName = "words")
public class WordEntity
{
    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField
    private String word;
    @DatabaseField
    private Integer folderId;
    @DatabaseField
    private String url;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    public Integer getFolderId()
    {
        return folderId;
    }

    public void setFolderId(Integer folderId)
    {
        this.folderId = folderId;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
