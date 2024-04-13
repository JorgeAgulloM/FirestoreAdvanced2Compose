package com.softyorch.firestoreadvanced2compose.ui.home.ex

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long?.millisToDate(): String = if (this == null) "" else try {
    val date = Date(this)
    val sdf = SimpleDateFormat("EEEE dd MMMM", Locale.getDefault())
    sdf.format(date)
} catch (ex: Exception) {
    ""
}
