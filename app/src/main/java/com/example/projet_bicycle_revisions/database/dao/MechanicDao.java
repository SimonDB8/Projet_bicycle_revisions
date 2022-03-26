package com.example.projet_bicycle_revisions.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet_bicycle_revisions.database.entity.MechanicEntity;

@Dao
public interface MechanicDao {

    @Query("SELECT * FROM mechanic WHERE email=:email")
    LiveData<MechanicEntity> getMechanic(String email);

    @Insert
    void insert(MechanicEntity mechanic);

    @Update
    void update(MechanicEntity mechanic);

    @Delete
    void delete(MechanicEntity mechanic);
}
