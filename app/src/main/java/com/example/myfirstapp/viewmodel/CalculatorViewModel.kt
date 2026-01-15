package com.example.myfirstapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DecimalFormat

class CalculatorViewModel : ViewModel() {
    private val _display = MutableLiveData("0")
    val displayState: LiveData<String> = _display
    val display: String
        get() = _display.value ?: "0"

    private var pendingOperation: Char? = null
    private var firstOperand: Double? = null
    private var isNewInput = true

    private val formatter = DecimalFormat("#.##########")

    fun onButtonClick(label: String) {
        when (label) {
            "C" -> clear()
            "±" -> toggleSign()
            "%" -> percent()
            "=" -> evaluate()
            "+", "-", "×", "÷" -> setOperation(label.first())
            else -> inputDigitOrDot(label)
        }
    }

    private fun clear() {
        _display.value = "0"
        firstOperand = null
        pendingOperation = null
        isNewInput = true
    }

    private fun toggleSign() {
        val current = _display.value?.toDoubleOrNull() ?: return
        _display.value = formatter.format(-current)
    }

    private fun percent() {
        val current = _display.value?.toDoubleOrNull() ?: return
        _display.value = formatter.format(current / 100)
    }

    private fun setOperation(op: Char) {
        firstOperand = _display.value?.toDoubleOrNull()
        pendingOperation = op
        isNewInput = true
    }

    private fun evaluate() {
        val second = _display.value?.toDoubleOrNull() ?: return
        val result = when (pendingOperation) {
            '+' -> (firstOperand ?: 0.0) + second
            '-' -> (firstOperand ?: 0.0) - second
            '×' -> (firstOperand ?: 0.0) * second
            '÷' -> if (second != 0.0) (firstOperand ?: 0.0) / second else Double.NaN
            else -> second
        }
        _display.value = formatter.format(result)
        // Reset state
        firstOperand = null
        pendingOperation = null
        isNewInput = true
    }

    private fun inputDigitOrDot(label: String) {
        if (isNewInput) {
            _display.value = if (label == ".") "0." else label
            isNewInput = false
        } else {
            val current = _display.value ?: ""
            // Prevent multiple dots
            if (label == "." && current.contains('.')) return
            _display.value = current + label
        }
    }
}
