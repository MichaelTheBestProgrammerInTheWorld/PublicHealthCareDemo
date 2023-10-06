package com.michaelmagdy.publicHealthCareDemo.dbDirectery.service

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.location.LocationEntity
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider.Category
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider.ProviderEntity

@Dao
interface ServiceDao {

    @Insert
    suspend  fun insertService(serviceEntity: ServiceEntity)

    @Query("SELECT * FROM serviceentity ORDER BY id DESC")
    suspend fun getAllServices(): List<ServiceEntity>

    @Query("SELECT * FROM serviceentity WHERE categoryId= :categoryId")
    suspend fun getAllServicesByCategory(categoryId: Int): List<ServiceEntity>

    @Query("SELECT * FROM serviceentity WHERE id= :serviceId")
    suspend fun getServiceById(serviceId: Int): ServiceEntity

    @Query("SELECT id FROM serviceentity WHERE serviceName= :name")
    suspend fun getServiceId(name: String): Int

    @Delete
    suspend fun delete(serviceEntity: ServiceEntity)
}