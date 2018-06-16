package br.com.alissonfpmorais.tiralama.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import br.com.alissonfpmorais.tiralama.R
import com.jakewharton.rxbinding2.view.RxMenuItem
import com.jakewharton.rxbinding2.widget.RxAdapterView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NavigationUI.setupActionBarWithNavController(this, findNavController(R.id.mainNavHost), drawerLayout)
        NavigationUI.setupWithNavController(navigation, findNavController(R.id.mainNavHost))

        RxMenuItem.clicks(navigation.menu.findItem(R.id.logout))
                .subscribe { Log.d("lgoout", "clicado!") }
    }

    override fun onSupportNavigateUp(): Boolean {
        NavigationUI.navigateUp(drawerLayout, findNavController(R.id.mainNavHost))
        return super.onSupportNavigateUp()
    }
}
