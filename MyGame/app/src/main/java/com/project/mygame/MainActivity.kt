package com.project.mygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvGameBoard : RecyclerView
    private lateinit var tvNumberMoves : TextView
    private lateinit var tvNumberPairs : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvGameBoard = findViewById(R.id.rvGameBoard)
        tvNumberMoves = findViewById(R.id.tvNumberMoves)
        tvNumberPairs = findViewById(R.id.tvNumberPairs)
    }
}