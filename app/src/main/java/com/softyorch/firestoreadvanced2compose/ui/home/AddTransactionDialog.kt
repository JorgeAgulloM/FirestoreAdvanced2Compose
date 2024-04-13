package com.softyorch.firestoreadvanced2compose.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun AddTransactionDialog(onAddTransaction: () -> Unit, onDismiss: () -> Unit) {

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Add Transaction",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(24.dp)
                )
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text(text = "Concept") }
                )
                Spacer(modifier = Modifier.height(24.dp))
                TextField(
                    value = amount,
                    onValueChange = { amount = it },
                    placeholder = { Text(text = "Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Button(onClick = { onAddTransaction() }) {
                    Text(text = "Add Transaction")
                }
            }
        }
    }
}
