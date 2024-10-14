package com.example.calculator

import android.annotation.SuppressLint
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
    @SuppressLint("SetTextI18n")
    private fun clearEntry() {
        calculateView.text = ""
    }

    @SuppressLint("SetTextI18n")
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
        val expression = calculateView.text.toString()

        if (expression.isEmpty()) {
            resultView.text = "0"
            return
        }

        try {
            val result = evaluateExpression(expression)
            resultView.text = result.toString()
        } catch (e : Exception) {
            resultView.text = ""
        }
    }

    private fun evaluateExpression(expression : String) : Double {
        val formattedExpression = expression.replace("x", "*")

        val tokens = tokenizeExpression(formattedExpression)

        return evaluateTokens(tokens)
    }

    private fun tokenizeExpression(expression: String): List<String> {
        val tokens = mutableListOf<String>()
        var numberBuffer = StringBuilder()

        for (char in expression) {
            if (char.isDigit() || char == '.')
                numberBuffer.append(char)
            else {
                // If it is an operator, add the number first and then the operator
                if (numberBuffer.isNotEmpty()) {
                    tokens.add(numberBuffer.toString())
                    numberBuffer = StringBuilder()
                }
                tokens.add(char.toString()) // Add operator
            }
        }

        // Add any remaining number to the token list
        if (numberBuffer.isNotEmpty())
            tokens.add(numberBuffer.toString())

        return tokens
    }

    private fun evaluateTokens(tokens: List<String>) : Double {
        val stack = mutableListOf<String>()

        var i = 0
        while (i < tokens.size) {
            val token = tokens[i]

            if (token == "*" || token == "/") {
                val previous = stack.removeAt(stack.size - 1).toDouble()
                val next = tokens[i + 1].toDouble()

                val result = if (token == "*") previous * next else previous / next
                stack.add(result.toString())

                i += 1 // Skip the next token as it is already processed
            } else {
                stack.add(token)
            }
            i += 1
        }

        var result = stack[0].toDouble()
        i = 1
        while (i < stack.size) {
            val operator = stack[i]
            val nextValue = stack[i + 1].toDouble()

            result = if (operator == "+") result + nextValue else result - nextValue
            i += 2
        }
        return result
    }
}