package com.michaelmagdy.publicHealthCareDemo.dbDirectery.booking

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.location.LocationEntity
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider.ProviderEntity
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.service.ServiceEntity

@Dao
interface BookingDao {

    @Insert
    suspend  fun insertBooking(bookingEntity: BookingEntity)

    @Query("SELECT * FROM bookingentity ORDER BY id DESC")
    suspend fun getAllBookings(): List<BookingEntity>

    @Query("SELECT * FROM bookingentity WHERE serviceId= :service")
    suspend fun getAllBookingsByService(service: Int): List<BookingEntity>

    @Query("SELECT * FROM bookingentity WHERE providerId= :provider")
    suspend fun getAllBookingsByProvider(provider: Int): List<BookingEntity>

    @Query("SELECT * FROM bookingentity WHERE userId= :user")
    suspend fun getAllBookingsByUser(user: Int): List<BookingEntity>

    @Query("SELECT * FROM bookingentity WHERE id= :bookingId")
    suspend fun getBookingById(bookingId: Int): BookingEntity

    @Delete
    suspend fun delete(bookingEntity: BookingEntity)

}