package com.example.maaltafels

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.time.ExperimentalTime

class ResultActivity : AppCompatActivity() {

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val correct:Int = intent.getIntExtra("correct", 0)
        val max:Int = intent.getIntExtra("max", 10)
        val score:TextView = findViewById<TextView>(R.id.result)
        score.text = "Je behaalde " + correct.toString() + "/" + max.toString()

        val restart:Button = findViewById<Button>(R.id.restart)
        restart.setOnClickListener {
            val intent = Intent(this@ResultActivity, MainActivity::class.java);
            startActivity(intent)
        }
    }
}