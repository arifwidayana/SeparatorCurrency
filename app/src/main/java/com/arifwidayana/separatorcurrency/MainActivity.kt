package com.arifwidayana.separatorcurrency

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.arifwidayana.separatorcurrency.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var bind: ActivityMainBinding? = null
    private val binding get() = bind!!
    private val formatCurrency = DecimalFormat.getCurrencyInstance(Locale("id", "ID"))

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            with(formatCurrency) {
                maximumFractionDigits = 0
                roundingMode = RoundingMode.FLOOR
            }
            etValue1.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    if (etValue1.text.toString() == "") {
                        return
                    }
                    etValue1.removeTextChangedListener(this)
                    val parsed = parseCurrencyValue(etValue1.text.toString())
                    val formatted = formatCurrency.format(parsed)
                    etValue1.setText(formatted)
                    etValue1.setSelection(formatted.length)
                    etValue1.addTextChangedListener(this)
                }
            })

            btnSeparatorTextWatcher.setOnClickListener {
                tvCur1.text = "Currency Value: ${etValue1.text}"
                val addCleanCurrency = parseCurrencyValue(etValue1.text.toString())
                tvCur2.text = "Original Value: $addCleanCurrency"
            }
            btnSeparatorNone.setOnClickListener {
                val addWithCurrency = currency(etValue2.text.toString().toInt())
                tvCur1.text = "Currency Value: $addWithCurrency"
                tvCur2.text = "Original Value: ${etValue2.text}"
            }
        }
    }

    private fun parseCurrencyValue(value: String): BigDecimal {
        try {
            val replaceRegex = String.format(
                "[%s,.]",
                Objects.requireNonNull(formatCurrency.currency?.displayName)
            )
            val newCurrency = value.replace(replaceRegex.toRegex(), "")
            return BigDecimal(newCurrency)
        } catch (e: Exception) {
            Log.e("Currency", e.message, e)
        }
        return BigDecimal.ZERO
    }

    override fun onStart() {
        super.onStart()
        binding.etValue1.setText("0")
    }
}