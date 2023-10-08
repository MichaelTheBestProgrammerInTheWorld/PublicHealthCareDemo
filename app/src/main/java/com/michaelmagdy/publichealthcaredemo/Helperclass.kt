package com.michaelmagdy.publicHealthCareDemo

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast

var currentUserId: Int = 0

fun Context.toast(massage:String)=Toast.makeText(this,massage,Toast.LENGTH_LONG).show()

fun Context.alert(message: String, action:DialogInterface.OnClickListener) = AlertDialog.Builder(this)
    .setTitle("Success")
    .setMessage(message)
    .setCancelable(false)
    .setPositiveButton(android.R.string.ok, action)
    .show()