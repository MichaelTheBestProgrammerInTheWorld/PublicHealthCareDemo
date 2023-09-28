package com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Category(


    var title: String,

    ): Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categoryId")
    var categoryId: Int=0
}
