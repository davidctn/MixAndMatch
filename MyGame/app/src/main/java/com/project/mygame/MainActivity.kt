package com.project.mygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.mygame.models.BoardSize
import com.project.mygame.models.Game
import com.project.mygame.models.MemoryCard
import com.project.mygame.utility.DEFAULT_ICONS

class MainActivity : AppCompatActivity() {

    private lateinit var rvGameBoard : RecyclerView
    private lateinit var tvNumberMoves : TextView
    private lateinit var tvNumberPairs : TextView

    private var boardSize : BoardSize =BoardSize.HARD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvGameBoard = findViewById(R.id.rvGameBoard)
        tvNumberMoves = findViewById(R.id.tvNumberMoves)
        tvNumberPairs = findViewById(R.id.tvNumberPairs)

        val memoryGame = Game(boardSize)

        rvGameBoard.adapter = GameBoardAdapter(this,boardSize,memoryGame.cards)
        rvGameBoard.setHasFixedSize(true)
        rvGameBoard.layoutManager=GridLayoutManager(this,boardSize.getWitdh())
    }
}