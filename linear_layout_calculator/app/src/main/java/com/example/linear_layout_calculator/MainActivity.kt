package com.example.linear_layout_calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var textResult: TextView

    var state: Int = 1 //1 - first number 2 - second number
    var op: Int = 0 //1 - add 2 - minus 3 - mul 4 - div
    var op1: Int = 0 // first number
    var op2: Int = 0 // second number
    var isNewCalculation: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Listening button and TextView
        textResult = findViewById(R.id.text_result)

        // Listening to buttons
        findViewById<Button>(R.id.btn0).setOnClickListener(this)
        findViewById<Button>(R.id.btn1).setOnClickListener(this)
        findViewById<Button>(R.id.btn2).setOnClickListener(this)
        findViewById<Button>(R.id.btn3).setOnClickListener(this)
        findViewById<Button>(R.id.btnAdd).setOnClickListener(this)
        findViewById<Button>(R.id.btnSub).setOnClickListener(this)
        findViewById<Button>(R.id.btnMul).setOnClickListener(this)
        findViewById<Button>(R.id.btnDiv).setOnClickListener(this)
        findViewById<Button>(R.id.btnEqual).setOnClickListener(this)
        findViewById<Button>(R.id.btnClear).setOnClickListener(this)
        findViewById<Button>(R.id.btnBackspace).setOnClickListener(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Handle button clicks
    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.btn0 -> addDigit(0)
                R.id.btn1 -> addDigit(1)
                R.id.btn2 -> addDigit(2)
                R.id.btn3 -> addDigit(3)
                R.id.btnAdd -> setOperation(1) // Phép cộng
                R.id.btnSub -> setOperation(2) // Phép trừ
                R.id.btnMul -> setOperation(3) // Phép nhân
                R.id.btnDiv -> setOperation(4) // Phép chia
                R.id.btnEqual -> calculateResult() // Tính kết quả
                R.id.btnClear -> clearAll() // Xóa tất cả
                R.id.btnBackspace -> backspace() // Xóa từng chữ số
            }
        }
    }

    // Add digit to the current number
    fun addDigit(c: Int) {
        if (isNewCalculation) {
            clearAll()
            isNewCalculation = false
        }

        if (state == 1) {
            op1 = op1 * 10 + c
            textResult.text = op1.toString()
        } else {
            op2 = op2 * 10 + c
            textResult.text = op2.toString()
        }
    }

    // Set operation and move to second number
    fun setOperation(operation: Int) {
        if (isNewCalculation) {
            op1 = textResult.text.toString().toInt()
            op2 = 0
            isNewCalculation = false
        }
        op = operation
        state = 2
        textResult.text = "0"
    }

    // Calculate the result
    fun calculateResult() {
        var result: String = ""
        var expression: String = ""
        when (op) {
            1 -> {
                result = (op1 + op2).toString()
                expression = "$op1 + $op2"
            }

            2 -> {
                result = (op1 - op2).toString()
                expression = "$op1 - $op2"
            }

            3 -> {
                result = (op1 * op2).toString()
                expression = "$op1 * $op2"
            }

            4 -> {
                if (op2 != 0) {
                    result = (op1 / op2).toString()
                    expression = "$op1 / $op2"
                } else {
                    result = "Error"
                    expression = "$op1 / $op2"
                }
            }
        }
        textResult.text = "$expression = $result"
        isNewCalculation = true
    }

    // Clear all data
    fun clearAll() {
        reset()
        textResult.text = "0"
    }

    // Remove the last digit
    fun backspace() {
        if (state == 1) {
            op1 /= 10
            textResult.text = "$op1"
        } else {
            op2 /= 10
            textResult.text = "$op2"
        }
    }

    // Reset all variables after calculation
    fun reset() {
        op1 = 0
        op2 = 0
        op = 0
        state = 1
        isNewCalculation = false
        textResult.text = "0"
    }
}
