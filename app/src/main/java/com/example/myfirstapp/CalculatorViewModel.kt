package com.example.myfirstapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.DecimalFormat
import java.util.Stack

class CalculatorViewModel : ViewModel() {
    var display by mutableStateOf("0")
        private set

    private var pendingOperator: Char? = null
    private var operand: Double? = null
    private var justCalculated = false

    private val formatter = DecimalFormat("#.########")

    fun onNumberClick(digit: Char) {
        if (justCalculated) {
            display = ""
            justCalculated = false
        }
        if (display == "0" && digit == '0') return
        if (display == "0" && digit != '.') {
            display = digit.toString()
        } else {
            // Prevent multiple dots
            if (digit == '.' && display.contains('.')) return
            display += digit
        }
    }

    fun onOperatorClick(op: Char) {
        calculatePending()
        pendingOperator = op
        operand = display.toDoubleOrNull()
        justCalculated = false
    }

    fun onEqualsClick() {
        calculatePending()
        pendingOperator = null
        operand = null
        justCalculated = true
    }

    fun onClear() {
        display = "0"
        pendingOperator = null
        operand = null
        justCalculated = false
    }

    private fun calculatePending() {
        val current = display.toDoubleOrNull() ?: return
        if (operand == null) {
            operand = current
            return
        }
        val result = when (pendingOperator) {
            '+' -> operand!! + current
            '-' -> operand!! - current
            '×' -> operand!! * current
            '÷' -> if (current != 0.0) operand!! / current else Double.NaN
            else -> current
        }
        display = formatter.format(result)
        operand = result
    }
}
