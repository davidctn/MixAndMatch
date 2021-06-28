package com.project.mygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.mygame.models.BoardSize
import com.project.mygame.models.Game

class MainActivity : AppCompatActivity() {

    companion object{
        private const val TAG="MainActivity"
    }


    private lateinit var rvGameBoard : RecyclerView
    private lateinit var tvNumberMoves : TextView
    private lateinit var tvNumberPairs : TextView
    private lateinit var memoryGame: Game
    private lateinit var adapter: GameBoardAdapter
    private var boardSize : BoardSize =BoardSize.HARD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvGameBoard = findViewById(R.id.rvGameBoard)
        tvNumberMoves = findViewById(R.id.tvNumberMoves)
        tvNumberPairs = findViewById(R.id.tvNumberPairs)

     memoryGame = Game(boardSize)

           adapter = GameBoardAdapter(this,boardSize,memoryGame.cards, object : GameBoardAdapter.CardClickListener{
            override fun ClickOnCard(position: Int) {
                updateGame(position)
            }


        })
        rvGameBoard.adapter = adapter
        rvGameBoard.setHasFixedSize(true)
        rvGameBoard.layoutManager=GridLayoutManager(this,boardSize.getWitdh())
    }

    private fun updateGame(position: Int) {

        if(memoryGame.winGame()){
            //mesaj
            return
        }
        if(memoryGame.isFaceUp(position)){
            //mesaj
            return
        }
        memoryGame.flipCard(position)
        adapter.notifyDataSetChanged()
    }


}