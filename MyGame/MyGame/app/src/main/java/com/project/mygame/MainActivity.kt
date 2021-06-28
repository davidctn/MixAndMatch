package com.project.mygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
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
    private lateinit var clRoot : ConstraintLayout
    private var boardSize : BoardSize =BoardSize.HARD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvGameBoard = findViewById(R.id.rvGameBoard)
        tvNumberMoves = findViewById(R.id.tvNumberMoves)
        tvNumberPairs = findViewById(R.id.tvNumberPairs)
        clRoot=findViewById(R.id.clRoot)
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
            Snackbar.make(clRoot,"You already won!",Snackbar.LENGTH_LONG).show()
            return
        }
        if(memoryGame.isFaceUp(position)){
            Snackbar.make(clRoot,"Invalid move!",Snackbar.LENGTH_LONG).show()
            return
        }
        if(memoryGame.flipCard(position)){
            Log.i(TAG,"Match found ! Number of matches founded ${memoryGame.numberPairs}")
        }
        adapter.notifyDataSetChanged()
    }
}