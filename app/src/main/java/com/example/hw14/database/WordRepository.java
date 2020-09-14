package com.example.hw14.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.room.Room;

import com.example.hw14.model.Language;
import com.example.hw14.model.Word;

import java.util.List;

public class WordRepository implements Reposible {
    private static WordRepository instance;
    private Context mContext;
    private static WordDataBaseRoom mDataBase;
    private static boolean isAssetLoad = false;
    private float mPersentAssetLoadWords = 0;
    private boolean sBooleanPermissionForLoad = false;


    private WordRepository(Context context) {

        this.mContext = context;
    }

    public static WordRepository newInstance(Context context) {
        if (instance == null) {
            instance = new WordRepository(context);
            mDataBase = Room.databaseBuilder(context, WordDataBaseRoom.class, WordsSchema.FILE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public boolean addAssetWord(Callbacks callbacks) {
        if (mDataBase.getDao().getCountWords() > 10000) {
            isAssetLoad = true;
        }
        if (!isAssetLoad && sBooleanPermissionForLoad) {
            getDBAsset(mContext, callbacks);
            return true;
        }
        return false;
    }

    public void setsBooleanPermissionForLoad(boolean sBooleanPermissionForLoad) {
        this.sBooleanPermissionForLoad = sBooleanPermissionForLoad;
    }

    private void getDBAsset(Context context, Callbacks callbacks) {
        try {
            AssetDatabaseOpenHelper assetDatabaseOpenHelper =
                    new AssetDatabaseOpenHelper(context);
            SQLiteDatabase assetDB = assetDatabaseOpenHelper.openDatabase();
            String queryStr = "SELECT * FROM " + AssetDatabaseOpenHelper.TABLE_NAME;
            Cursor cursor = assetDB.rawQuery(queryStr, null);
            cursor.moveToFirst();
            float all = cursor.getCount();
            int now = 1;
            while (!cursor.isAfterLast()) {

                String persian = cursor.getString(1);
                String english = cursor.getString(0);
                Word word = new Word();
                word.setStringLeft(english);
                word.setStringRight(persian);
                word.setLanguage(Language.ENGLISH_PERSIAN);
                mDataBase.getDao().insertWord(word);
                cursor.moveToNext();
                now++;
                mPersentAssetLoadWords = (now / all) * 100;
                Log.d("QQQ", now + "");
                if (now % 50 == 0)
                    callbacks.persentOfProsses(mPersentAssetLoadWords);

            }
        } catch (Exception e) {
            isAssetLoad = false;
        }
        callbacks.assetLoadFinish();
        isAssetLoad = true;

    }

    @Override
    public List<Word> getLimitedWord(String search) {
        return mDataBase.getDao().getLimitedWords(search);
    }

    public static boolean isAssetLoad() {
        return isAssetLoad;
    }

    @Override
    public List<Word> getList() {
        return mDataBase.getDao().getAllWords();
    }

    @Override
    public List<Word> getLangugeList(Language language) {
        return mDataBase.getDao().getLanguegeWords(language);
    }

    @Override
    public int getWordsCount() {
        return mDataBase.getDao().getCountWords();
    }


    @Override
    public boolean addWord(Word word) {
        if (mDataBase.getDao().getCountLeftWord(word.getStringLeft()) == 0 &&
                mDataBase.getDao().getCountLeftWord(word.getStringRight()) == 0) {
            mDataBase.getDao().insertWord(word);
            return true;

        } else {
            return false;
        }

    }

    @Override
    public boolean addWord(List<Word> words) {
        mDataBase.getDao().insertWords(words);
        return true;
    }

    @Override
    public void deleteWord(Word word) {
        mDataBase.getDao().deleteWord(word);
    }

    public interface Callbacks {
        void persentOfProsses(float persent);

        void assetLoadFinish();
    }
}

interface Reposible {
    List<Word> getLimitedWord(String search);

    List<Word> getList();

    List<Word> getLangugeList(Language language);

    int getWordsCount();

    boolean addWord(Word word);

    boolean addWord(List<Word> words);

    void deleteWord(Word word);

}
