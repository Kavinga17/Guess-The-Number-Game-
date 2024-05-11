package com.example.t2kao.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.t2kao.R

class InitialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)

        val btnStart: Button = findViewById(R.id.btnStart)
        btnStart.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Finish this activity to prevent going back to it when back pressed
        }
    }
}
