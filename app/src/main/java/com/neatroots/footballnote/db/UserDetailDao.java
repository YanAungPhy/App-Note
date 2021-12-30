package com.neatroots.footballnote.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDetailDao {

    @Query("Select * from UserInfo ")
    List<UserInfo> getUserList();

    @Insert
    void insertUser(UserInfo... userInfo);

    @Update
    void updateUser(UserInfo userInfo);

    @Delete
    void deleteUser(UserInfo userInfo);


    @Query("SELECT SUM(amount) from UserInfo WHERE isPaid=:paid")
    int getTotalAmount(boolean paid);
}
