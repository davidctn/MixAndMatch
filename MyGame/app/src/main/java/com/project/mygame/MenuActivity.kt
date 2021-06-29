package com.project.mygame

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import java.util.*


class MenuActivity : AppCompatActivity() {
    private val auxTime = Calendar.getInstance().timeInMillis + 10000
    private var notified = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
            if (!notified) {
                NotificationUtils().setNotification(auxTime, this@MenuActivity)
            }

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