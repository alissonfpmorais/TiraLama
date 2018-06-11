package br.com.alissonfpmorais.tiralama.auth

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class AuthScreen : Parcelable

object LoginScreen : AuthScreen()
object RegisterScreen : AuthScreen()
object SplashScreen : AuthScreen()