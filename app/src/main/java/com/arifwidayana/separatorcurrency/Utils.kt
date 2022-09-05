package com.arifwidayana.separatorcurrency

import java.text.DecimalFormat
import java.util.*

fun currency(value: Int): String {
    val formatCurrency = DecimalFormat.getCurrencyInstance(Locale("id","ID"))
    return formatCurrency.format(value)
}