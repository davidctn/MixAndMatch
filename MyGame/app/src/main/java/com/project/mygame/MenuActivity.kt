package com.project.mygame

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar


class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }

    fun toPlay(view: View) {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }
    fun toAbout(view: View) {
        val snackbar = Snackbar.make(view,"Android final project made by David Constantinecu and Alexandru Georgescu", Snackbar.LENGTH_LONG)
        snackbar.show()
    }
}