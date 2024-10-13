package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var calculateView : TextView
    private lateinit var resultView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.linear_layout)

        calculateView = findViewById(R.id.calculationTextView)
        resultView = findViewById(R.id.resultTextView)

        val numberButtons = arrayOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9
        )

        // Operator buttons
        findViewById<Button>(R.id.button_add).setOnClickListener { appendToCalculate("+") }
        findViewById<Button>(R.id.button_sub).setOnClickListener { appendToCalculate("-") }
        findViewById<Button>(R.id.button_mul).setOnClickListener { appendToCalculate("x") }
        findViewById<Button>(R.id.button_div).setOnClickListener { appendToCalculate("/") }
        findViewById<Button>(R.id.button_dot).setOnClickListener { appendToCalculate(".") }

        // Function buttons
        findViewById<Button>(R.id.buttonCE).setOnClickListener { clearEntry() }
        findViewById<Button>(R.id.buttonC).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.buttonBS).setOnClickListener { backspace() }
        findViewById<Button>(R.id.button_equal).setOnClickListener { calculate() }

        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener { appendToCalculate((it as Button).text.toString())}
        }
    }

    private fun appendToCalculate(str : String) {
        calculateView.append(str)
    }

    private fun clearEntry() {
        calculateView.text = ""
    }

    private fun clearAll() {
        calculateView.text = ""
        resultView.text = ""
    }

    private fun backspace() {
        val currentDigit = calculateView.text.toString()
        if (currentDigit.isNotEmpty())
            calculateView.text = currentDigit.substring(0, currentDigit.length - 1)
    }

    private fun calculate() {

    }
}