package com.example.hw14.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.hw14.model.Convertor;
import com.example.hw14.model.Word;

@TypeConverters({Convertor.class})
@Database(entities = Word.class, version = WordsSchema.VERSION, exportSchema = false)
public abstract class WordDataBaseRoom extends RoomDatabase {
    public abstract WordDAO getDao();
}
