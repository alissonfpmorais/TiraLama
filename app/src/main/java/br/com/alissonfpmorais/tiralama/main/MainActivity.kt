package br.com.alissonfpmorais.tiralama.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.auth.AuthActivity
import br.com.alissonfpmorais.tiralama.common.data.local.DatabaseHolder
import com.jakewharton.rxbinding2.view.RxMenuItem
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NavigationUI.setupActionBarWithNavController(this, findNavController(R.id.mainNavHost), drawerLayout)
        NavigationUI.setupWithNavController(navigation, findNavController(R.id.mainNavHost))
    }

    override fun onSupportNavigateUp(): Boolean {
        NavigationUI.navigateUp(drawerLayout, findNavController(R.id.mainNavHost))
        return super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()

        val logoutClickStream = RxMenuItem.clicks(navigation.menu.findItem(R.id.logout))
                .flatMap {
                    DatabaseHolder.getSingleInstance(this)
                            .map { database ->
                                val user = database.userDao().listByLogged(true).first()
                                database.userDao().update(user.copy(isLogged = false)) > 0
                            }
                            .toObservable()
                }
                .filter { isUserUpdated -> isUserUpdated }
                .subscribe {
                    val intent = Intent(this, AuthActivity::class.java)

                    startActivity(intent)
                    finish()
                }

        disposables.add(logoutClickStream)
    }

    override fun onStop() {
        super.onStop()
        if (disposables.isDisposed) disposables.dispose()
    }
}
