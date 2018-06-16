package br.com.alissonfpmorais.tiralama.auth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.findNavController
import br.com.alissonfpmorais.tiralama.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.authNavHost).navigateUp()
}
