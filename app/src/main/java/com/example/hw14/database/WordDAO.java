package com.example.hw14.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.hw14.model.Language;
import com.example.hw14.model.Word;

import java.util.List;

@Dao
public interface WordDAO {

    int INT_LIMITED_RECYCLER = 10;

    @Insert
    void insertWord(Word word);

    @Insert
    void insertWords(List<Word> words);

    @Delete
    void deleteWord(Word word);

    @Query("DELETE FROM WORDS")
    void clear();

    @Query("DELETE FROM WORDS WHERE languge = :language")
    void clear(Language language);

    @Query("SELECT * FROM WORDS")
    List<Word> getAllWords();

    @Query("SELECT * FROM WORDS WHERE languge =:language")
    List<Word> getLanguegeWords(Language language);


    @Query("SELECT COUNT(*) FROM WORDS WHERE `left`=:left ")
    int getCointLeftWord(String left);

    @Query("SELECT COUNT(*) FROM WORDS WHERE `right`=:right ")
    int getCountRightWord(String right);

    @Query("SELECT COUNT(*) FROM WORDS ")
    int getCountWords();

    @Query("SELECT * FROM WORDS WHERE `left` LIKE  '%' || :search || '%'  OR `right` LIKE '%' || :search || '%'  LIMIT "+ INT_LIMITED_RECYCLER)
    List<Word> getLimitedWords( String search);

}
