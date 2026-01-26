package com.example.myfirstapp.ui
import android.widget.Button
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapp.CalculatorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Calculator", fontWeight = FontWeight.Bold)
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DisplaySection(value = viewModel.display)
            Spacer(modifier = Modifier.height(8.dp))
            ButtonGrid(viewModel = viewModel)
        }
    }
}

@Composable
private fun DisplaySection(value: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.displayMedium.copy(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Light
                ),
                textAlign = TextAlign.End,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun ButtonGrid(viewModel: CalculatorViewModel) {
    val buttonModifier = Modifier
        .size(80.dp)
        .padding(4.dp)

    val buttonShape = RoundedCornerShape(12.dp)
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )

    val operatorColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Row 1
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            CalculatorButton(text = "C", onClick = { viewModel.onClear() }, modifier = buttonModifier, colors = buttonColors)
            CalculatorButton(text = "÷", onClick = { viewModel.onOperatorClick('÷') }, modifier = buttonModifier, colors = operatorColors)
            CalculatorButton(text = "×", onClick = { viewModel.onOperatorClick('×') }, modifier = buttonModifier, colors = operatorColors)
            CalculatorButton(text = "←", onClick = { /* optional backspace */ }, modifier = buttonModifier, colors = buttonColors)
        }
        // Row 2
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf('7', '8', '9').forEach { digit ->
                CalculatorButton(
                    text = digit.toString(),
                    onClick = { viewModel.onNumberClick(digit) },
                    modifier = buttonModifier,
                    colors = buttonColors
                )
            }
            CalculatorButton(text = "-", onClick = { viewModel.onOperatorClick('-') }, modifier = buttonModifier, colors = operatorColors)
        }
        // Row 3
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf('4', '5', '6').forEach { digit ->
                CalculatorButton(
                    text = digit.toString(),
                    onClick = { viewModel.onNumberClick(digit) },
                    modifier = buttonModifier,
                    colors = buttonColors
                )
            }
            CalculatorButton(text = "+", onClick = { viewModel.onOperatorClick('+') }, modifier = buttonModifier, colors = operatorColors)
        }
        // Row 4
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf('1', '2', '3').forEach { digit ->
                CalculatorButton(
                    text = digit.toString(),
                    onClick = { viewModel.onNumberClick(digit) },
                    modifier = buttonModifier,
                    colors = buttonColors
                )
            }
            CalculatorButton(text = "=", onClick = { viewModel.onEqualsClick() }, modifier = buttonModifier, colors = operatorColors)
        }
        // Row 5
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            CalculatorButton(text = "0", onClick = { viewModel.onNumberClick('0') }, modifier = Modifier
                .weight(1f)
                .height(80.dp)
                .padding(4.dp), colors = buttonColors)
            CalculatorButton(text = ".", onClick = { viewModel.onNumberClick('.') }, modifier = buttonModifier, colors = buttonColors)
        }
    }
}

@Composable
private fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors()
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = colors,
        modifier = modifier,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp, pressedElevation = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}
