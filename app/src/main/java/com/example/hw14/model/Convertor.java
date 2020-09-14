package com.example.hw14.model;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

import java.util.UUID;

public class Convertor {
    @TypeConverter
    public static Language langugeFromString(@NonNull String string) {
        return Language.valueOf(string);
    }

    @TypeConverter
    public static String stringFromLanguege(@NonNull Language language) {
        //return language.toString();
        //todo return online
        return Language.ENGLISH_PERSIAN.toString();
    }

    @TypeConverter
    public static String StringFromUUID(@NonNull UUID uuid) {
        return uuid.toString();
    }

    @TypeConverter
    public static UUID StringFromUUID(@NonNull String uuid) {
        return UUID.fromString(uuid);
    }

}
