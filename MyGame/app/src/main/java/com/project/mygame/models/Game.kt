package com.project.mygame.models

import com.project.mygame.utility.DEFAULT_ICONS

class Game(private val boardSize: BoardSize) {

    val cards: List<MemoryCard>
    val numberPairs = 0

    init {
        val chosenImages: List<Int> = DEFAULT_ICONS.shuffled().take(boardSize.getNumberPairs())
        val randomizedImages: List<Int> = (chosenImages + chosenImages).shuffled()
        cards = randomizedImages.map { MemoryCard(it) }

    }
}