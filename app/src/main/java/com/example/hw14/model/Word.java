package com.example.hw14.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.hw14.database.WordsSchema;

import java.util.UUID;

@Entity(tableName = WordsSchema.TABALE_NAME)
public class Word {

    @ColumnInfo(name = WordsSchema.CUL_RIGHT_WORD)
    private String mStringRight;
    @ColumnInfo(name = WordsSchema.CUL_LEFT_WORD)
    private String mStringLeft;
    @ColumnInfo(name = WordsSchema.CUL_LANGUGE)
    private Language mLanguage;
    @ColumnInfo(name = WordsSchema.CUL_UUID)
    private UUID mUUID;
    @PrimaryKey(autoGenerate = true)
    public long id;


    public Language getLanguage() {
        return mLanguage;
    }

    public void setLanguage(Language language) {
        mLanguage = language;
        mUUID = UUID.randomUUID();
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    @Ignore
    public Word(Language language, String right, String left) {
        mLanguage = language;
        mStringLeft = left;
        mStringRight = right;
        mUUID = UUID.randomUUID();
    }

    public Word() {
    }

    public String getStringRight() {
        return mStringRight;
    }

    public void setStringRight(String stringRight) {
        mStringRight = stringRight;
    }

    public String getStringLeft() {
        return mStringLeft;
    }

    public void setStringLeft(String stringLeft) {
        mStringLeft = stringLeft;
    }

    public String searchStr() {
        return mStringLeft + " " + mStringRight;
    }

    public String toStringShare() {
        return "In the languege " +
                mLanguage +
                " Word" +
                mStringRight +
                " meaning " +
                mStringLeft;
    }
}

