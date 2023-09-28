package com.michaelmagdy.publicHealthCareDemo.dbDirectery.service

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class ServiceEntity(


    var serviceName: String,
    var categoryId: Int,

    ): Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int=0
}
