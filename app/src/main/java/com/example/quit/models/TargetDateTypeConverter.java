package com.example.quit.models;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

public class TargetDateTypeConverter {
    @TypeConverter
    public static String toString(@NonNull TargetDateType type) {
        return type.toString();
    }

    @TypeConverter
    public static TargetDateType toType(String strType) {
        return TargetDateType.fromString(strType);
    }

}
