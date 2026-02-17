package com.apptest.calculator

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var displayText: TextView

    private val expression = StringBuilder()
    private var lastResult = "0"
    private var lastInputWasEquals = false
    private var isErrorState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayText = findViewById(R.id.display_text)

        bindNumberButtons()
        bindOperatorButtons()
        bindActionButtons()
        updateDisplay(getString(R.string.display_default))
    }

    private fun bindNumberButtons() {
        bindClick(R.id.button_0) { appendDigit("0") }
        bindClick(R.id.button_1) { appendDigit("1") }
        bindClick(R.id.button_2) { appendDigit("2") }
        bindClick(R.id.button_3) { appendDigit("3") }
        bindClick(R.id.button_4) { appendDigit("4") }
        bindClick(R.id.button_5) { appendDigit("5") }
        bindClick(R.id.button_6) { appendDigit("6") }
        bindClick(R.id.button_7) { appendDigit("7") }
        bindClick(R.id.button_8) { appendDigit("8") }
        bindClick(R.id.button_9) { appendDigit("9") }
        bindClick(R.id.button_dot) { appendDecimalPoint() }
    }

    private fun bindOperatorButtons() {
        bindClick(R.id.button_add) { appendOperator("+") }
        bindClick(R.id.button_subtract) { appendOperator("-") }
        bindClick(R.id.button_multiply) { appendOperator("×") }
        bindClick(R.id.button_divide) { appendOperator("÷") }
    }

    private fun bindActionButtons() {
        bindClick(R.id.button_clear) { clearAll() }
        bindClick(R.id.button_backspace) { backspace() }
        bindClick(R.id.button_equals) { evaluateExpression() }
    }

    private fun appendDigit(digit: String) {
        if (isErrorState) {
            clearAll()
        }

        if (lastInputWasEquals) {
            expression.clear()
            lastInputWasEquals = false
        }

        if (expression.toString() == "0") {
            expression.clear()
        }

        expression.append(digit)
        updateDisplay(currentDisplayText())
    }

    private fun appendDecimalPoint() {
        if (isErrorState) {
            clearAll()
        }

        if (lastInputWasEquals) {
            expression.clear()
            lastInputWasEquals = false
        }

        if (currentNumberHasDecimalPoint()) {
            return
        }

        if (expression.isEmpty() || expression.last().isOperator()) {
            expression.append("0")
        }

        expression.append(".")
        updateDisplay(currentDisplayText())
    }

    private fun appendOperator(operator: String) {
        if (isErrorState) {
            return
        }

        if (lastInputWasEquals) {
            expression.clear()
            expression.append(lastResult)
            lastInputWasEquals = false
        }

        if (expression.isEmpty()) {
            if (operator == "-") {
                expression.append(operator)
                updateDisplay(currentDisplayText())
            }
            return
        }

        val lastChar = expression.last()
        if (lastChar.isOperator()) {
            expression.setCharAt(expression.length - 1, operator.first())
            updateDisplay(currentDisplayText())
            return
        }

        if (lastChar == '.') {
            return
        }

        expression.append(operator)
        updateDisplay(currentDisplayText())
    }

    private fun evaluateExpression() {
        if (isErrorState || expression.isEmpty()) {
            return
        }

        val candidateExpression = expression.toString()
        if (candidateExpression.last().isOperator() || candidateExpression.last() == '.') {
            return
        }

        try {
            val result = CalculatorEngine.evaluate(candidateExpression)
            lastResult = result
            lastInputWasEquals = true
            isErrorState = false
            updateDisplay(result)
        } catch (_: IllegalArgumentException) {
            showError()
        } catch (_: ArithmeticException) {
            showError()
        }
    }

    private fun clearAll() {
        expression.clear()
        lastResult = "0"
        lastInputWasEquals = false
        isErrorState = false
        updateDisplay(getString(R.string.display_default))
    }

    private fun backspace() {
        if (isErrorState) {
            clearAll()
            return
        }

        if (lastInputWasEquals) {
            expression.clear()
            expression.append(lastResult)
            lastInputWasEquals = false
        }

        if (expression.isNotEmpty()) {
            expression.deleteCharAt(expression.length - 1)
        }

        updateDisplay(currentDisplayText())
    }

    private fun currentNumberHasDecimalPoint(): Boolean {
        for (i in expression.length - 1 downTo 0) {
            val char = expression[i]
            if (char.isOperator()) {
                break
            }
            if (char == '.') {
                return true
            }
        }
        return false
    }

    private fun currentDisplayText(): String {
        if (expression.isEmpty()) {
            return getString(R.string.display_default)
        }

        return expression.toString()
    }

    private fun updateDisplay(value: String) {
        displayText.text = value
    }

    private fun showError() {
        isErrorState = true
        lastInputWasEquals = false
        expression.clear()
        updateDisplay(getString(R.string.error_text))
    }

    private fun bindClick(buttonId: Int, listener: () -> Unit) {
        findViewById<MaterialButton>(buttonId).setOnClickListener { listener() }
    }

    private fun Char.isOperator(): Boolean {
        return this == '+' || this == '-' || this == '×' || this == '÷'
    }
}
