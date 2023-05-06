package com.example.maaltafels

import android.content.Intent
import android.graphics.Color
import android.os.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.math.max
import kotlin.random.Random
import kotlin.time.ExperimentalTime


@ExperimentalTime
class MainActivity : AppCompatActivity() {

    var handler: Handler? = null
    private val maxCountDown:Int = 90
    private var countDown:Int = maxCountDown
    private var maxExercises:Int = 0
    private var correctExercises:Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

        countDown = maxCountDown
        maxExercises = 0
        correctExercises = 0

        newExercise()

        // Progressbar + countdown
        val p:ProgressBar = findViewById<ProgressBar>(R.id.progress)
        p.max = countDown
        handler = Handler(Handler.Callback {
            countDown--
            p.setProgress(countDown, true)

            if (countDown == 0) {
                val intent = Intent(this@MainActivity, ResultActivity::class.java);
                intent.putExtra("correct", correctExercises)
                intent.putExtra("max", maxExercises)
                startActivity(intent);
            }
            handler?.sendEmptyMessageDelayed(0, 1000)

            true
        })
        handler?.sendEmptyMessageDelayed(0, 1000)
    }

    override fun onRestart() {
        super.onRestart()
    }

    private fun getRandomValue(not:Int): Int {
        var result:Int = not
        while (result == not) {
            result = Random.nextInt(1, 100)
        }
        return result
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun newExercise() {
        val factor1 = Random.nextInt(1, 10)
        val factor2 = Random.nextInt(1, 10)

        val buttons = arrayOf(
            findViewById<Button>(R.id.answer1),
            findViewById<Button>(R.id.answer2),
            findViewById<Button>(
                R.id.answer3
            )
        )

        // Set neutral background colors
        for (button in buttons) {
            button.setBackgroundColor(Color.BLUE)
        }

        val correctButtonIndex = Random.nextInt(0, buttons.size)
        for (button in buttons) {
            if (button != buttons[correctButtonIndex]) {
                button.text = getRandomValue((factor1 * factor2)).toString()
                button.setOnClickListener {
                    button.setBackgroundColor(Color.RED)
                    maxExercises++
                }
            }
        }

        val correctButton = buttons[correctButtonIndex]
        correctButton.text = (factor1 * factor2).toString()
        correctButton.setOnClickListener {
            correctExercises++
            maxExercises++

            newExercise()
        }

        findViewById<TextView>(R.id.exercise).text = factor1.toString() + " x " + factor2.toString()
    }
}