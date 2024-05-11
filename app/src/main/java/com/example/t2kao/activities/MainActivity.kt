package com.example.t2kao.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.t2kao.R
import com.example.t2kao.adapters.ResultAdapter
import com.example.t2kao.models.Result

class MainActivity : AppCompatActivity() {

    lateinit var resultList: MutableList<Result>
    private var toast: Toast? = null
    private lateinit var timer: CountDownTimer
    private var score: Int = 0
    private var remainingTime: Long = 30000 // Initial time

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultList = mutableListOf()
        val spGuess: Spinner = findViewById(R.id.spinnerGuess)
        val btnPlay: Button = findViewById(R.id.btnPlay)
        val tvScore: TextView = findViewById(R.id.tvScore)
        val tvTimer: TextView = findViewById(R.id.tvTimer)

        // Setting spinner
        val availGuesses: Array<Int> = arrayOf(1, 2, 3, 4, 5)
        val spAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, availGuesses)
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spGuess.adapter = spAdapter

        // Setting list adapter
        val resultAdapter = ResultAdapter(this, resultList)
        val lvGameResults: ListView = findViewById(R.id.lvGameResults)
        lvGameResults.adapter = resultAdapter

        // Setting initial score
        updateScore()

        btnPlay.setOnClickListener {
            val computerGuess: Int = (1..5).random()
            val playerGuess: Int = spGuess.selectedItem.toString().toInt()

            // Check results
            if (check(playerGuess, computerGuess)) {
                score += 5 // Add 5 marks for correct answer
                updateScore()
                toast = Toast.makeText(this, "You won!", Toast.LENGTH_SHORT)
                // Increase time by 10 seconds
                timer.cancel() // Cancel the current timer
                startTimer(tvTimer, remainingTime + 10000) // Start new timer with increased time
            } else {
                score -= 1 // Deduct 1 marks for wrong answer
                updateScore()
                toast = Toast.makeText(this, "You lost.", Toast.LENGTH_SHORT)
            }

            toast!!.show()

            // Update list and reset spinner
            resultAdapter.notifyDataSetChanged()
            spGuess.setSelection(0)
        }

        // Starting timer with initial time
        startTimer(tvTimer, remainingTime)
    }

    private fun check(playerGuess: Int, pcGuess: Int): Boolean {
        val playerWins: Boolean = playerGuess == pcGuess
        val result = Result(playerGuess, pcGuess, playerWins)
        resultList.add(0, result)
        return playerWins
    }

    private fun updateScore() {
        val tvScore: TextView = findViewById(R.id.tvScore)
        tvScore.text = "Score: $score"
    }

    private fun startTimer(tvTimer: TextView, initialTimeInMillis: Long) {
        timer = object : CountDownTimer(initialTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished // Update remaining time
                val timeLeftSeconds = millisUntilFinished / 1000
                tvTimer.text = "Time left: $timeLeftSeconds s"
            }

            override fun onFinish() {
                tvTimer.text = "Time's up!"
                // Save highest score before finishing
                saveHighestScoreIfHigher(score)
                // Navigate to GameOverActivity
                val intent = Intent(this@MainActivity, GameOverActivity::class.java)
                intent.putExtra("finalScore", score)
                startActivity(intent)
            }
        }
        timer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    // Save highest score if the current score is higher
    private fun saveHighestScoreIfHigher(score: Int) {
        val highestScore = loadHighestScore()
        if (score > highestScore) {
            saveHighestScore(score)
        }
    }

    // Save highest score
    private fun saveHighestScore(score: Int) {
        val prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt("highestScore", score)
        editor.apply()
    }

    // Load highest score
    private fun loadHighestScore(): Int {
        val prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE)
        return prefs.getInt("highestScore", 0)
    }
}
