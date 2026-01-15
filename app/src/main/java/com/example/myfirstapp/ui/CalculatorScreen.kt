package com.example.myfirstapp.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapp.viewmodel.CalculatorViewModel

private data class CalcButton(val label: String, val weight: Float = 1f, val isOperator: Boolean = false)

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel) {
    var display by remember { mutableStateOf(viewModel.display) }

    // Observe ViewModel changes
    viewModel.displayState.observeForever { display = it }

    val buttons = listOf(
        listOf(CalcButton("C", isOperator = true), CalcButton("±", isOperator = true), CalcButton("%", isOperator = true), CalcButton("÷", isOperator = true, weight = 1f)),
        listOf(CalcButton("7"), CalcButton("8"), CalcButton("9"), CalcButton("×", isOperator = true)),
        listOf(CalcButton("4"), CalcButton("5"), CalcButton("6"), CalcButton("-", isOperator = true)),
        listOf(CalcButton("1"), CalcButton("2"), CalcButton("3"), CalcButton("+", isOperator = true)),
        listOf(CalcButton("0", weight = 2f), CalcButton(".", weight = 1f), CalcButton("=", isOperator = true))
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Display
            Text(
                text = display,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                    .padding(24.dp)
                    .animateContentSize(),
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                maxLines = 1
            )

            // Buttons Grid
            buttons.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { button ->
                        val backgroundColor = if (button.isOperator) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                        val contentColor = if (button.isOperator) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                        ElevatedButton(
                            onClick = { viewModel.onButtonClick(button.label) },
                            modifier = Modifier
                                .weight(button.weight)
                                .height(64.dp),
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = backgroundColor,
                                contentColor = contentColor
                            ),
                            shape = RoundedCornerShape(12.dp),
                            elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp)
                        ) {
                            Text(
                                text = button.label,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}
