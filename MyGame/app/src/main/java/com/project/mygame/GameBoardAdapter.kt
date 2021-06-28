package com.project.mygame

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.min

class GameBoardAdapter(private val context:Context, private val NumPieces : Int) : RecyclerView.Adapter<GameBoardAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageButton = itemView.findViewById<ImageButton>(R.id.imageButton)

        fun bind(position: Int) {

            imageButton.setOnClickListener {
                Log.i(TAG,"Clicked on position $position")
            }
        }
    }

    companion object {

        private const val MARGIN_SIZE=10
        private const val TAG="GameBoardAdapter"

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //how to create one view in the rv
        var cardWidth :Int =parent.width / 2 - (2* MARGIN_SIZE)
        var cardHeight :Int =parent.height / 4 - (2* MARGIN_SIZE)
        var cardSideLength :Int = min(cardWidth,cardHeight)
        val view = LayoutInflater.from(context).inflate(R.layout.memory_card,parent,false)
        val layoutParams : ViewGroup.MarginLayoutParams =view.findViewById<CardView>(R.id.cardView).layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width=cardSideLength
        layoutParams.height=cardSideLength
        layoutParams.setMargins(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

      holder.bind(position)
    }

    override fun getItemCount() = NumPieces

}
