package com.example.ht.User;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Database class
 */
@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    // singleton instance
    private static UserDatabase userDatabase;
    private static  String DATABASE_NAME = "user_database";
    // DAO inside the database
    public abstract UserDao userDao();


    public synchronized static UserDatabase getInstance(Context context) {
        // if the database hasn't been initialized, initialize
        if (userDatabase == null) {
            userDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class,
                    DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return  userDatabase;
    }
}
