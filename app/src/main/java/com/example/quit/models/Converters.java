package com.example.quit.models;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static String addictionTypeToString(AddictionType type) {
        return type.toString();
    }
    @TypeConverter
    public static AddictionType stringToAddictionType(String strType) {
        return AddictionType.stringTypeToAddictionType(strType);
    }
}
