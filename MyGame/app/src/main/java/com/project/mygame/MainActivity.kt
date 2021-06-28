package com.project.mygame

import android.animation.ArgbEvaluator
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.jinatonic.confetti.CommonConfetti
import com.google.android.material.snackbar.Snackbar
import com.project.mygame.models.BoardSize
import com.project.mygame.models.Game
import com.project.mygame.utility.EXTRA_BOARD_SIZE

class MainActivity : AppCompatActivity() {

    companion object{
        private const val TAG="MainActivity"
        private const val CREATE_REQUEST_CODE = 888
    }


    private lateinit var rvGameBoard : RecyclerView
    private lateinit var tvNumberMoves : TextView
    private lateinit var tvNumberPairs : TextView
    private lateinit var memoryGame: Game
    private lateinit var adapter: GameBoardAdapter
    private lateinit var clRoot : ConstraintLayout
    private var boardSize : BoardSize =BoardSize.EASY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvGameBoard = findViewById(R.id.rvGameBoard)
        tvNumberMoves = findViewById(R.id.tvNumberMoves)
        tvNumberPairs = findViewById(R.id.tvNumberPairs)
        clRoot=findViewById(R.id.clRoot)
        setupBoard()
    }

    private fun setupBoard() {

        when(boardSize){
            BoardSize.EASY -> {
                tvNumberMoves.text="Easy : 4 x 2"
                tvNumberPairs.text="Pairs : 0 / 4"
            }
            BoardSize.MEDIUM -> {
                tvNumberMoves.text="Medium : 6 x 3"
                tvNumberPairs.text="Pairs : 0 / 9"
            }
            BoardSize.HARD -> {
                tvNumberMoves.text="Hard : 6 x 4"
                tvNumberPairs.text="Pairs : 0 / 12"
            }
        }
        tvNumberPairs.setTextColor(ContextCompat.getColor(this,R.color.color_progress_0))
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuitem_refresh -> {
                if(!memoryGame.winGame() && memoryGame.getNumberMoves()>0){
                    displayAlertDialog("Are you sure you want to quit the current game ?",null,View.OnClickListener {
                        setupBoard()

                    })
                    return true
                }
                else{
                    setupBoard()
                }
            }

            R.id.menuitem_chooseSize ->{
                displayNewSizeDialog()
                return true
            }
            R.id.mi_custom -> {
                showCreationDialog()
            return true;
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showCreationDialog() {
        var boardSizeView =  LayoutInflater.from(this).inflate(R.layout.dialog_board_size,null)
        var radioGroup=boardSizeView.findViewById<RadioGroup>(R.id.radioGroup)
        displayAlertDialog("Create new memory board",boardSizeView,View.OnClickListener {
            val desiredBoardSize = when (radioGroup.checkedRadioButtonId) {
                R.id.gamemodeEasy -> BoardSize.EASY
                R.id.gamemodeMedium -> BoardSize.MEDIUM
                else -> BoardSize.HARD
            }
            //trimite la activitate noua
            val intent = Intent(this, CreateActivity::class.java)
            intent.putExtra(EXTRA_BOARD_SIZE, desiredBoardSize)
            startActivityForResult(intent, CREATE_REQUEST_CODE)
        })
    }

    private fun displayNewSizeDialog() {
      var boardSizeView =  LayoutInflater.from(this).inflate(R.layout.dialog_board_size,null)
        var radioGroup=boardSizeView.findViewById<RadioGroup>(R.id.radioGroup)
        when(boardSize){
            BoardSize.EASY -> radioGroup.check(R.id.gamemodeEasy)
            BoardSize.MEDIUM -> radioGroup.check(R.id.gamemodeMedium)
            BoardSize.HARD -> radioGroup.check(R.id.gamemodeHard)
        }
            displayAlertDialog("Choose new size",boardSizeView,View.OnClickListener {
                    boardSize=when(radioGroup.checkedRadioButtonId){
                        R.id.gamemodeEasy->BoardSize.EASY
                        R.id.gamemodeMedium->BoardSize.MEDIUM
                        else->BoardSize.HARD
                    }
                setupBoard()
            })
    }

    private fun displayAlertDialog(title : String, view : View?, positiveClickListenener : View.OnClickListener) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(view)
            .setNegativeButton("Cancel",null)
            .setPositiveButton("Ok") {_,_->
            positiveClickListenener.onClick(null)
            }.show()

    }

    private fun updateGame(position: Int) {

        if(memoryGame.winGame()){
            Snackbar.make(clRoot,"You already won!",Snackbar.LENGTH_LONG).show()
            return
        }
        if(memoryGame.isFaceUp(position)){
            Snackbar.make(clRoot,"Invalid move!",Snackbar.LENGTH_SHORT).show()
            return
        }
        if(memoryGame.flipCard(position)){
            var color = ArgbEvaluator().evaluate(
                memoryGame.numberPairs.toFloat()/boardSize.getNumberPairs(),
                ContextCompat.getColor(this,R.color.color_progress_0),
                ContextCompat.getColor(this,R.color.color_progress_100)
            ) as Int
            tvNumberPairs.setTextColor(color)
            tvNumberPairs.text="Pairs : ${memoryGame.numberPairs} / ${boardSize.getNumberPairs()}"
            Log.i(TAG,"Match found ! Number of matches founded ${memoryGame.numberPairs}")
            if(memoryGame.winGame()){
                Snackbar.make(clRoot,"You won!",Snackbar.LENGTH_LONG).show()
                CommonConfetti.rainingConfetti(clRoot, intArrayOf(Color.CYAN,Color.GREEN,Color.MAGENTA,Color.YELLOW,Color.RED)).oneShot()
            }
        }
        tvNumberMoves.text="Moves : ${memoryGame.getNumberMoves()}"
        adapter.notifyDataSetChanged()
    }
}