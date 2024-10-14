package com.example.mycalculator

//package com.example.mycaculator2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var textResult: TextView
    lateinit var textExpression: TextView // hien thi phep tinh

    var state: Int = 1
    var op: Int = 0
    var op1: String = "0"
    var op2: String = "0"
    var isDecimal: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textResult = findViewById(R.id.text_result)
        textExpression = findViewById(R.id.text_expression) //them text view de hien thi phep tinh

        findViewById<Button>(R.id.btn0).setOnClickListener(this)
        findViewById<Button>(R.id.btn1).setOnClickListener(this)
        findViewById<Button>(R.id.btn2).setOnClickListener(this)
        findViewById<Button>(R.id.btn3).setOnClickListener(this)
        findViewById<Button>(R.id.btn4).setOnClickListener(this)
        findViewById<Button>(R.id.btn5).setOnClickListener(this)
        findViewById<Button>(R.id.btn6).setOnClickListener(this)
        findViewById<Button>(R.id.btn7).setOnClickListener(this)
        findViewById<Button>(R.id.btn8).setOnClickListener(this)
        findViewById<Button>(R.id.btn9).setOnClickListener(this)
        findViewById<Button>(R.id.btnAdd).setOnClickListener(this)
        findViewById<Button>(R.id.btnSub).setOnClickListener(this)
        findViewById<Button>(R.id.btnMulti).setOnClickListener(this)
        findViewById<Button>(R.id.btnDiv).setOnClickListener(this)
        findViewById<Button>(R.id.btnDot).setOnClickListener(this)
        findViewById<Button>(R.id.btnAddorSub).setOnClickListener(this)
        findViewById<Button>(R.id.btnC).setOnClickListener(this)
        findViewById<Button>(R.id.btnCE).setOnClickListener(this)
        findViewById<Button>(R.id.btnBS).setOnClickListener(this)
        findViewById<Button>(R.id.btnEqual).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val id = p0?.id
        when (id) {
            R.id.btn0 -> addDigit("0")
            R.id.btn1 -> addDigit("1")
            R.id.btn2 -> addDigit("2")
            R.id.btn3 -> addDigit("3")
            R.id.btn4 -> addDigit("4")
            R.id.btn5 -> addDigit("5")
            R.id.btn6 -> addDigit("6")
            R.id.btn7 -> addDigit("7")
            R.id.btn8 -> addDigit("8")
            R.id.btn9 -> addDigit("9")
            R.id.btnDot -> addDot()
            R.id.btnAdd -> { op = 1; state = 2; isDecimal = false }
            R.id.btnSub -> { op = 2; state = 2; isDecimal = false }
            R.id.btnMulti -> { op = 3; state = 2; isDecimal = false }
            R.id.btnDiv -> { op = 4; state = 2; isDecimal = false }
            R.id.btnEqual -> calculate()
            R.id.btnAddorSub -> toggleSign()
            R.id.btnC -> clearAll()
            R.id.btnCE -> clearEntry()
            R.id.btnBS -> backspace()
        }
    }

    fun addDigit(c: String) {
        if (state == 1) {
            if (op1 == "0" && c != ".") op1 = c else op1 += c
            textResult.text = op1
        } else {
            if (op2 == "0" && c != ".") op2 = c else op2 += c
            textResult.text = op2
        }
        updateExpression() // Cao nhat phep tinh tren man hinh
    }

    fun addDot() {
        if (!isDecimal) {
            isDecimal = true
            if (state == 1) {
                op1 += "."
                textResult.text = op1
            } else {
                op2 += "."
                textResult.text = op2
            }
            updateExpression() // Cao nhat phep tinh tren man hinh
        }
    }

    fun setOperator(operator: Int, symbol: String) {
        op = operator
        state = 2
        isDecimal = false
        updateExpression(symbol) // Cập nhật biểu thức với dấu toán tử
    }

    fun updateExpression(symbol: String = "") {
        if (state == 1) {
            textExpression.text = op1
        } else {
            // Hiển thị dấu toán tử tương ứng với toán tử đã chọn
            val operatorSymbol = when (op) {
                1 -> "+"
                2 -> "-"
                3 -> "*"
                4 -> "/"
                else -> ""
            }
            textExpression.text = "$op1 $operatorSymbol $op2"
        }
    }

    fun toggleSign() {
        if (state == 1) {
            op1 = (-op1.toDouble()).toString()
            textResult.text = op1
        } else {
            op2 = (-op2.toDouble()).toString()
            textResult.text = op2
        }
        updateExpression() // Cao nhat phep tinh tren man hinh
    }

    fun backspace() {
        if (state == 1) {
            if (op1.isNotEmpty()) {
                op1 = if (op1.length > 1) op1.dropLast(1) else "0"  // Xóa ký tự cuối
                textResult.text = op1
            }
        } else {
            if (op2.isNotEmpty()) {
                op2 = if (op2.length > 1) op2.dropLast(1) else "0"  // Xóa ký tự cuối
                textResult.text = op2
            }
        }
        updateExpression() // Cập nhật phép tính sau khi xóa
    }
    fun clearAll() {
        state = 1
        op = 0
        op1 = "0"
        op2 = "0"
        isDecimal = false
        textResult.text = "0"
        textExpression.text = "" // Xóa toàn bộ biểu thức trên màn hình
    }

    fun clearEntry() {
        if (state == 1) {
            op1 = "0"
            textResult.text = "0"
        } else {
            op2 = "0"
            textResult.text = "0"
        }
        isDecimal = false
    }


    fun calculate() {
        var result = 0.0
        when (op) {
            1 -> result = op1.toDouble() + op2.toDouble()
            2 -> result = op1.toDouble() - op2.toDouble()
            3 -> result = op1.toDouble() * op2.toDouble()
            4 -> if (op2.toDouble() != 0.0) result = op1.toDouble() / op2.toDouble() else {
                textResult.text = "Error"
                return
            }
        }

        // Hiển thị kết quả
        if (result == result.toInt().toDouble()) {
            textResult.text = result.toInt().toString()
        } else {
            textResult.text = result.toString()
        }

        // Cập nhật phép tính với kết quả
        val operatorSymbol = when (op) {
            1 -> "+"
            2 -> "-"
            3 -> "*"
            4 -> "/"
            else -> ""
        }
        textExpression.text = "$op1 $operatorSymbol $op2 = $result"

        resetCalculator() // Đặt lại trạng thái sau khi tính
    }

    fun resetCalculator() {
        state = 1
        op1 = "0"
        op2 = "0"
        op = 0
        isDecimal = false
    }
}