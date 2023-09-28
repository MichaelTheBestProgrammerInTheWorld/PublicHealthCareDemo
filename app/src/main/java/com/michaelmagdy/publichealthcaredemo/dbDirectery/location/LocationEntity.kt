package com.michaelmagdy.publicHealthCareDemo.dbDirectery.location

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class LocationEntity(


    var locationName: String,

): Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int=0
}
