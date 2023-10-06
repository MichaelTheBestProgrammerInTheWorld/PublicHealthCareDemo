package com.michaelmagdy.publicHealthCareDemo.dbDirectery

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.booking.BookingDao
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.booking.BookingEntity
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.location.LocationDao
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.location.LocationEntity
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider.Category
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider.ProviderDao
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider.ProviderEntity
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider.ProviderServices
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.service.ServiceDao
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.service.ServiceEntity
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.user.UserDao
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.user.UserEntity

@Database(entities = [UserEntity::class, ServiceEntity::class,
    ProviderEntity::class, Category::class, ProviderServices::class, LocationEntity::class,
    BookingEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class HealthCareDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun serviceDao(): ServiceDao
    abstract fun providerDao(): ProviderDao
    abstract fun locationDao(): LocationDao
    abstract fun bookingDao(): BookingDao
    companion object{
     @Volatile   private var inastance: HealthCareDatabase? =null
        private val LOCK = Any()

   operator  fun invoke(context: Context): HealthCareDatabase {
         return inastance ?: synchronized(LOCK){
             inastance ?: Builddatabase(context).also {
                 inastance=it
             }
         }
     }
       private fun Builddatabase(context: Context)= Room.databaseBuilder(context.applicationContext,HealthCareDatabase::class.java,"HealthCaresystemDb").build()
    }
}
