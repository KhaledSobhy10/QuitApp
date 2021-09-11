package com.example.quit.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.quit.dao.AddictionDao;
import com.example.quit.dao.RelapseDao;
import com.example.quit.models.Addiction;
import com.example.quit.models.Converters;
import com.example.quit.models.Relapse;

@Database(entities = {Addiction.class, Relapse.class}, version = 2 ,exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;


    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            synchronized (AppDatabase.class) {
                if (appDatabase == null) {
                    appDatabase = Room.databaseBuilder(context, AppDatabase.class, "app_database").addMigrations(new Migration(1,2) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {

                            String buildBackupTable = "CREATE TABLE IF NOT EXISTS " +
                                    "`backupT` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `addictionName` TEXT, `firstDateOfQuit` INTEGER NOT NULL," +
                                    " `lastDateOfQuit` INTEGER NOT NULL, `targetDate` INTEGER NOT NULL, `iconId` INTEGER NOT NULL, `addictionType` TEXT, " +
                                    "`timeWasting` REAL NOT NULL, `moneyWasting` REAL NOT NULL, `targetDateType` TEXT)";

                            database.execSQL(buildBackupTable);

                            String insertDataToBackupTable ="INSERT INTO backupT " +
                                    "SELECT id , addictionName , firstDateOfQuit ,lastDateOfQuit , targetDate , iconId , addictionType ,timeWasting , moneyWasting , targetDateType " +
                                    "FROM Addiction";
                            database.execSQL(insertDataToBackupTable);

                            database.execSQL("DROP TABLE Addiction");

                            database.execSQL("ALTER TABLE backupT RENAME to Addiction");
                        }
                    }).build();

                }
            }
        }

        return appDatabase;
    }

    public AppDatabase() {
    }

    public abstract AddictionDao getAddictionDao();

    public abstract RelapseDao getRelapseDao();
}
