package com.michaelmagdy.publicHealthCareDemo.dbDirectery.user

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class UserEntity(


    var username: String,
    var password: String,
    var locationId: Int = 0

):Parcelable  {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int=0
}
