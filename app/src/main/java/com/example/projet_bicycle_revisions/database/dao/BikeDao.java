package com.example.projet_bicycle_revisions.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet_bicycle_revisions.database.entity.BikesEntity;

import java.util.List;

@Dao
public interface BikeDao {

    @Query("SELECT * FROM bikes WHERE mechanic=:id")
    LiveData<List<BikesEntity>> getAllofMechanic(String id);

    @Query("SELECT * FROM bikes WHERE id=:id")
    LiveData<BikesEntity> getBike(long id);

    @Insert
    long insert(BikesEntity bike);

    @Update
    void update(BikesEntity bike);

    @Delete
    void delete(BikesEntity bike);

}
