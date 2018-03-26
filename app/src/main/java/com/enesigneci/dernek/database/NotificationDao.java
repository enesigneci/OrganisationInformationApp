package com.enesigneci.dernek.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.enesigneci.dernek.model.Notification;

import java.util.List;

/**
 * Created by rdcmac on 23.03.2018.
 */

@Dao
public interface NotificationDao {

    @Query("SELECT * FROM notification")
    List<Notification> getAll();

    @Query("SELECT * FROM notification where type LIKE  :type")
    Notification findByType(String type);

    @Query("SELECT * FROM notification where type = :id")
    Notification findById(int id);

    @Query("SELECT COUNT(*) from notification")
    int countNotifications();

    @Insert
    void insertAll(Notification... notifications);

    @Delete
    void delete(Notification notification);
}