package com.example.quit.models;

import androidx.annotation.NonNull;

public enum AddictionType {
    EVENT, TIME_WASTING, MONEY_WASTING;

    @NonNull
    @Override
    public String toString() {
        switch (this) {
            case EVENT:
                return "bad_event";
            case MONEY_WASTING:
                return "money_wasting";
            case TIME_WASTING:
                return "time_wasting";
        }
        return "";
    }

    public static AddictionType stringTypeToAddictionType(String stringAddictionType) {
        switch (stringAddictionType) {
            case "bad_event":
                return EVENT;
            case "money_wasting":
                return MONEY_WASTING;
            case "time_wasting":
                return TIME_WASTING;
        }
        return null;
    }
}