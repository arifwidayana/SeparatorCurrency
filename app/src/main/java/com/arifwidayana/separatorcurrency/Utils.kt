package com.arifwidayana.separatorcurrency

import android.util.Log
import java.text.DecimalFormat
import java.util.*

const val ZERO_VALUE = 0

private val formatCurrency = DecimalFormat.getCurrencyInstance(Locale("id","ID"))

fun currency(value: Int): String {
    return formatCurrency.format(value)
}

fun parseCurrencyValue(value: String): String {
    try {
        val replaceRegex = String.format(
            "[%s,.]",
            Objects.requireNonNull(formatCurrency.currency?.displayName)
        )
        val newCurrency = value.replace(replaceRegex.toRegex(), "").toBigDecimal()
        return formatCurrency.format(newCurrency)
    } catch (e: Exception) {
        Log.e("Currency", e.message, e)
    }
    return ZERO_VALUE.toString()
}