package com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.location.LocationEntity

@Dao
interface ProviderDao {

    @Insert
    suspend  fun insertCategory(category: Category)

    @Query("SELECT * FROM category ORDER BY categoryId DESC")
    suspend fun getAllCategories(): List<Category>

    @Query("SELECT title FROM category ORDER BY categoryId DESC")
    suspend fun getAllCategoriesTitles(): List<String>

    @Delete
    suspend fun deleteCategory(category: Category)

    @Insert
    suspend  fun insertProviderService(providerServices: ProviderServices)

    @Query("SELECT serviceId FROM providerservices WHERE providerId= :id")
    suspend fun getProviderServices(id: Int): List<Int>

    @Insert
    suspend  fun insertProvider(providerEntity: ProviderEntity)

    @Query("SELECT * FROM providerentity ORDER BY id DESC")
    suspend fun getAllProviders(): List<ProviderEntity>

    @Query("SELECT providerName FROM providerentity ORDER BY id DESC")
    suspend fun getAllProvidersTitles(): List<String>

    @Query("SELECT * FROM providerentity WHERE categoryId= :categoryId")
    suspend fun getAllProvidersByCategory(categoryId: Int): List<ProviderEntity>

    @Query("SELECT * FROM providerentity WHERE locationId= :location")
    suspend fun getAllProvidersByLocation(location: Int): List<ProviderEntity>

    @Query("SELECT * FROM providerentity WHERE categoryId= :categoryId and locationId= :location")
    suspend fun getAllProvidersByCategoryAndLocation(categoryId: Int, location: Int): List<ProviderEntity>


    @Query("SELECT * FROM providerentity WHERE id= :providerId")
    suspend fun getProviderById(providerId: Int): ProviderEntity

    @Query("SELECT id FROM providerentity WHERE providerName= :name")
    suspend fun getProviderId(name: String): Int

    @Delete
    suspend fun deleteProvider(providerEntity: ProviderEntity)
}