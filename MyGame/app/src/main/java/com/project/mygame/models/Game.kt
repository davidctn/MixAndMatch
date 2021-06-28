package com.project.mygame.models

import com.project.mygame.utility.DEFAULT_ICONS

class Game(private val boardSize: BoardSize) {

    val cards: List<MemoryCard>
    var numberPairs = 0
    private var firstCardIndex: Int? = null
    private var flippedCards = 0

    init {
        val chosenImages: List<Int> = DEFAULT_ICONS.shuffled().take(boardSize.getNumberPairs())
        val randomizedImages: List<Int> = (chosenImages + chosenImages).shuffled()
        cards = randomizedImages.map { MemoryCard(it) }

    }

    fun flipCard(position: Int) : Boolean {
        flippedCards++
        var foundMatch = false
        val card: MemoryCard = cards[position]
        if (firstCardIndex == null) {
            //0 sau 2 carti intoarse
            restoreDefaultState()
            firstCardIndex = position
        }
        else {
            //1 carte intoarsa
            foundMatch = checkCards(firstCardIndex!!,position)
            firstCardIndex=null
        }
        card.isFaceUp = !card.isFaceUp
        return foundMatch
    }

    private fun checkCards(firstCardIndex: Int, secondCardIndex: Int): Boolean {

        if(cards[firstCardIndex].identifier != cards[secondCardIndex].identifier){
            return false
        }
        cards[firstCardIndex].isMatched=true
        cards[secondCardIndex].isMatched=true
        numberPairs++
        return true

    }

    private fun restoreDefaultState() {

        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    fun winGame(): Boolean {

        return numberPairs == boardSize.getNumberPairs()
    }

    fun isFaceUp(position : Int): Boolean {

        return cards[position].isFaceUp
    }

    fun getNumberMoves(): Int {
return flippedCards/2
    }
}