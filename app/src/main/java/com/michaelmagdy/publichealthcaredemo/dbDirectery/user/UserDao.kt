package com.michaelmagdy.publicHealthCareDemo.dbDirectery.user

import androidx.room.*
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.location.LocationEntity

@Dao
interface UserDao {

    @Insert
    suspend  fun createUser(userEntity: UserEntity)

    @Query("SELECT * FROM userentity ORDER BY id DESC")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM userentity WHERE username= :name and password= :psw")
    suspend fun getUser(name: String, psw: String): UserEntity

    @Query("SELECT * FROM userentity WHERE id= :id")
    suspend fun getUserById(id: Int): UserEntity

    @Delete
    suspend fun delete(userEntity: UserEntity)
}