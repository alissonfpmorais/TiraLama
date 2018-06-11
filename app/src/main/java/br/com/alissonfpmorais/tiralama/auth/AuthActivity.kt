package br.com.alissonfpmorais.tiralama.auth

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.auth.login.LoginFragment
import br.com.alissonfpmorais.tiralama.auth.register.RegisterFragment
import br.com.alissonfpmorais.tiralama.auth.splash.SplashFragment
import br.com.alissonfpmorais.tiralama.common.NavigationHost

class AuthActivity : AppCompatActivity(), NavigationHost {
    private val currentScreen = "CURRENT_SCREEN"

    lateinit var currentAuthScreen: AuthScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_auth)
        super.onCreate(savedInstanceState)

        val fragment = if (savedInstanceState != null) {
            val authScreen = savedInstanceState.getParcelable(currentScreen) as AuthScreen?

            authScreen?.let {
                when (authScreen) {
                    is LoginScreen -> LoginFragment()
                    is RegisterScreen -> RegisterFragment()
                    else -> SplashFragment()
                }
            }
        } else {
            SplashFragment()
        }

        replaceFragment(fragment as Fragment, false)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelable(currentScreen, currentAuthScreen)
        super.onSaveInstanceState(outState)
    }

    override fun replaceFragment(fragment: Fragment, addToBackstack: Boolean) {
        val transaction = supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)

        if (addToBackstack) transaction.addToBackStack(null)

        transaction.commit()

        currentAuthScreen = when (fragment) {
            is LoginFragment -> LoginScreen
            is RegisterFragment -> RegisterScreen
            else -> SplashScreen
        }
    }
}
