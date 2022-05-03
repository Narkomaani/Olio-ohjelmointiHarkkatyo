package com.example.ht.user;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Data access object for SQLite database.
 */
@Dao
public interface UserDao {

    @Insert()
    void insert(User user);

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Delete
    void delete(User user);

    @Update
    void update(User... users);

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE username LIKE :username")
    User findByUsername(String username);
}
