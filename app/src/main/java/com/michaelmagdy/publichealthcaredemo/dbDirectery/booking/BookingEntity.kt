package com.michaelmagdy.publicHealthCareDemo.dbDirectery.booking

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class BookingEntity(


    var dateAndTime: String,
    var serviceId: Int,
    var providerId: Int,
    var userId: Int,

    ): Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int=0
}

