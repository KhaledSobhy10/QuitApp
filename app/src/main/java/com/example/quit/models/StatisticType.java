package com.example.quit.models;

import androidx.annotation.NonNull;

public enum StatisticType {
       DAY_YOU_QUIT,MAX_PERIOD,MIN_PERIOD,AVE_PERIOD,PREV_PERIOD,NUM_TIMER_RES
       ,TIME_SPENT,TIME_INVESTED,MONEY_SPENT,MONEY_SAVED;

       @NonNull
       @Override
       public String toString() {
              switch (this){
                     case DAY_YOU_QUIT:
                            return "The day you quit";
                     case MAX_PERIOD:
                            return "Max abstinence period";
                     case MIN_PERIOD:
                            return "Min abstinence period";
                     case AVE_PERIOD:
                            return "Average abstinence period";
                     case NUM_TIMER_RES:
                            return "Number of timer resets";
                     case PREV_PERIOD:
                            return "Previous abstinence period";
                     case TIME_SPENT:
                            return "Time spent on addiction";
                     case TIME_INVESTED:
                            return "Time invested in useful things";
                     case MONEY_SPENT:
                            return "Money spent on addiction";
                     case MONEY_SAVED:
                            return "Money saved";
                     default:
                            return "";

              }
       }
}
