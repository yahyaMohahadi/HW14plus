package com.example.hw14;

import com.example.hw14.model.Language;

public class OnlineLanguege {
    private static OnlineLanguege mOnlineLanguege;
    private Language mLanguage;

    public static OnlineLanguege newInstance() {
        if (mOnlineLanguege == null) {
            mOnlineLanguege = new OnlineLanguege();
        }
        return mOnlineLanguege;
    }

    public Language getLanguage() {
        return mLanguage;
    }

    public void setLanguage(Language language) {
        mLanguage = language;
    }
}
