package com.example.t2kao.models

data class Result(val playerGuess: Int, val computerGuess: Int, val playerWins: Boolean) {
    override fun toString(): String {
        val resultString = if (playerWins) "You won!" else "You lost."
        return "Computer's Guess: $computerGuess, Your Guess: $playerGuess, Result: $resultString"
    }
}
