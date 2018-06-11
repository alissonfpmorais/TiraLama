package br.com.alissonfpmorais.tiralama.common

import android.support.v4.app.Fragment

interface NavigationHost {
    fun replaceFragment(fragment: Fragment, addToBackstack: Boolean)
}