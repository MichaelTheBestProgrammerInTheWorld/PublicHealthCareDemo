package com.michaelmagdy.publicHealthCareDemo

import android.content.Context
import android.widget.Toast

var currentUserId: Int = 0

fun Context.toast(massage:String)=Toast.makeText(this,massage,Toast.LENGTH_LONG).show()