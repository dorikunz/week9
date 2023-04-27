package edu.skku.map.week9

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)
        button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch(){
                textView.text = longTask().await()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun longTask() = CoroutineScope(Dispatchers.Default).async{
        var totalCount = 0
        var innerCount = 0
        for(bigLoop in 1..100){
            for(smallLoop in 1..1_000_000){
                val x = Math.random()
                val y = Math.random()
                if(sqrt(x.pow(2)+y.pow(2)) <= 1) innerCount += 1
                totalCount += 1
            }
            val currentValue = innerCount.toDouble() / totalCount.toDouble() * 4.0f
            CoroutineScope(Dispatchers.Main).launch(){
                val textView = findViewById<TextView>(R.id.textView)
                textView.text = "Done ${bigLoop}%...\n" + "current estimation: ${String.format("%.6f", currentValue)}"
            }
        }
        val lastValue = innerCount.toDouble() / totalCount.toDouble() * 4.0f
        "Done!\nEstimation: ${String.format("%.6f", lastValue)}"
    }
}