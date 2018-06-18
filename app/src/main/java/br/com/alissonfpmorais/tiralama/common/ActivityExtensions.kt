package br.com.alissonfpmorais.tiralama.common

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

fun AppCompatActivity.changeActivity(activityClass: Class<out AppCompatActivity>) {
    val intent = Intent(this, activityClass)
    startActivity(intent)
}

fun AppCompatActivity.makeToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}