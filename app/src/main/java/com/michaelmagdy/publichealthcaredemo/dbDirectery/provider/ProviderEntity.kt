package com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class ProviderEntity(


    var providerName: String,
    var categoryId: Int,
    var locationId: Int,

    ): Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int=0
}