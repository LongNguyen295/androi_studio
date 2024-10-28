package com.example.currency

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var edt1: EditText
    private lateinit var edt2: EditText
    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private var lastFocusedEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edt1 = findViewById(R.id.edt1)
        edt2 = findViewById(R.id.edt2)
        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)

        // Cập nhật danh sách tiền tệ
        val arrayList = arrayListOf("VietNam - Dong", "China - Yuan", "Europe - Euro", "Japan - Yen", "USA - Dollar")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter
        spinner2.adapter = adapter

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                updateConversion()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                updateConversion()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        edt1.addTextChangedListener(edt1Watcher)
        edt2.addTextChangedListener(edt2Watcher)

        edt1.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) lastFocusedEditText = edt1
        }

        edt2.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) lastFocusedEditText = edt2
        }
    }

    private val edt1Watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            updateConversionFromEdt1()
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    private val edt2Watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            updateConversionFromEdt2()
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    private fun updateConversion() {
        if (lastFocusedEditText == edt1) {
            updateConversionFromEdt1()
        } else if (lastFocusedEditText == edt2) {
            updateConversionFromEdt2()
        }
    }

    private fun updateConversionFromEdt1() {
        edt2.removeTextChangedListener(edt2Watcher)
        val inputValue = edt1.text.toString().toDoubleOrNull()
        if (inputValue != null) {
            val coinFrom = spinner1.selectedItem.toString()
            val coinTo = spinner2.selectedItem.toString()
            val convertedValue = convertCurrency(inputValue, coinFrom, coinTo)
            edt2.setText(String.format(Locale.getDefault(), "%.2f", convertedValue))
        }
        edt2.addTextChangedListener(edt2Watcher)
    }

    private fun updateConversionFromEdt2() {
        edt1.removeTextChangedListener(edt1Watcher)
        val inputValue = edt2.text.toString().toDoubleOrNull()
        if (inputValue != null) {
            val coinFrom = spinner2.selectedItem.toString()
            val coinTo = spinner1.selectedItem.toString()
            val convertedValue = convertCurrency(inputValue, coinFrom, coinTo)
            edt1.setText(String.format(Locale.getDefault(), "%.2f", convertedValue))
        }
        edt1.addTextChangedListener(edt1Watcher)
    }

    // Cập nhật hàm chuyển đổi tiền tệ
    private fun convertCurrency(value: Double, from: String, to: String): Double {
        return when {
            from == "VietNam - Dong" && to == "VietNam - Dong" -> value
            from == "VietNam - Dong" && to == "China - Yuan" -> value * 0.0002805
            from == "VietNam - Dong" && to == "Europe - Euro" -> value * 0.00003652
            from == "VietNam - Dong" && to == "Japan - Yen" -> value * 0.006011
            from == "VietNam - Dong" && to == "USA - Dollar" -> value * 0.000041

            from == "China - Yuan" && to == "VietNam - Dong" -> value / 0.0002805
            from == "China - Yuan" && to == "China - Yuan" -> value
            from == "China - Yuan" && to == "Europe - Euro" -> value * 0.1302
            from == "China - Yuan" && to == "Japan - Yen" -> value * 21.4282
            from == "China - Yuan" && to == "USA - Dollar" -> value * 0.142

            from == "Europe - Euro" && to == "VietNam - Dong" -> value * 27382.4925
            from == "Europe - Euro" && to == "China - Yuan" -> value * 7.6813
            from == "Europe - Euro" && to == "Europe - Euro" -> value
            from == "Europe - Euro" && to == "Japan - Yen" -> value * 164.5968
            from == "Europe - Euro" && to == "USA - Dollar" -> value * 1.1

            from == "Japan - Yen" && to == "VietNam - Dong" -> value / 0.006011
            from == "Japan - Yen" && to == "China - Yuan" -> value / 21.4282
            from == "Japan - Yen" && to == "Europe - Euro" -> value / 164.5968
            from == "Japan - Yen" && to == "Japan - Yen" -> value
            from == "Japan - Yen" && to == "USA - Dollar" -> value * 0.0085

            from == "USA - Dollar" && to == "VietNam - Dong" -> value / 0.000041
            from == "USA - Dollar" && to == "China - Yuan" -> value / 0.142
            from == "USA - Dollar" && to == "Europe - Euro" -> value / 1.1
            from == "USA - Dollar" && to == "Japan - Yen" -> value / 0.0085
            from == "USA - Dollar" && to == "USA - Dollar" -> value

            else -> 0.0
        }
    }
}
