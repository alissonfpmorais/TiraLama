package br.com.alissonfpmorais.tiralama.common

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.changeActivity(activityClass: Class<out AppCompatActivity>) {
    val intent = Intent(this, activityClass)
    startActivity(intent)
}

fun AppCompatActivity.replaceFragment(fragment: Fragment) {
    TODO()
}