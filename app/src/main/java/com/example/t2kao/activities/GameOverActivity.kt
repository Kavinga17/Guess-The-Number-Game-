package com.example.t2kao.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.t2kao.R

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_over)

        // Retrieve final score from intent extra
        val finalScore = intent.getIntExtra("finalScore", 0)

        // Display the final score
        val tvScoreValue: TextView = findViewById(R.id.tvScoreValue)
        tvScoreValue.text = "Your Final Score: $finalScore"

        // Load highest score from SharedPreferences
        val highestScore = loadHighestScore()

        // Display the highest score
        val tvHighestScore: TextView = findViewById(R.id.tvHighestScore)
        tvHighestScore.text = "Highest Score: $highestScore"

        // Set onClickListener for the restart button
        val btnRestart: Button = findViewById(R.id.btnRestart)
        btnRestart.setOnClickListener {
            val intent = Intent(this@GameOverActivity, MainActivity::class.java)
            startActivity(intent)
            // Finish the current activity (GameOverActivity)
            finish()
        }

        // Set onClickListener for the exit button
        val btnExit: Button = findViewById(R.id.btnExit)
        btnExit.setOnClickListener {
            // Exit the game (close the app)
            finishAffinity()
        }
    }

    // Load highest score from SharedPreferences
    private fun loadHighestScore(): Int {
        val prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE)
        return prefs.getInt("highestScore", 0)
    }
}
