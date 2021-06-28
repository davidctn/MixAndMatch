package com.project.mygame.models

enum class BoardSize(val numberCards : Int) {
    EASY(8),
    MEDIUM(18),
    HARD(24);

    fun getWitdh() : Int {
        return when(this) {
            EASY->2
            MEDIUM->3
            HARD->4
        }
    }

    fun getHeight() : Int {
        return numberCards/getWitdh()
    }

    fun getNumberPairs() : Int {
        return numberCards/2
    }
}