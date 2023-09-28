package com.michaelmagdy.publicHealthCareDemo.dbDirectery.location

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LocationDao {

    @Insert
    suspend  fun insertLocation(locationEntity: LocationEntity)

    @Query("SELECT * FROM locationentity ORDER BY id DESC")
    suspend fun getAllLocations(): List<LocationEntity>

    @Query("SELECT id FROM locationentity WHERE locationName= :name")
    suspend fun getLocationId(name: String): Int

    @Query("SELECT locationName FROM locationentity WHERE id= :id")
    suspend fun getLocationName(id: Int): String

    @Delete
    suspend fun delete(locationEntity: LocationEntity)
}