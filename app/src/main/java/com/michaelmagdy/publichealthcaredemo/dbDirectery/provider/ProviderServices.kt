package com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class ProviderServices(


    var providerId: Int,
    var serviceId: Int,

    ): Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int=0
}