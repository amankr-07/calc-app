package com.myapp.calculator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.myapp.calculator.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding : ActivityMainBinding
    var number : String? = null

    var firstNumber : Double = 0.0
    var lastNumber : Double = 0.0

    var status : String? = null
    var operator : Boolean = false

    val myFormatter = DecimalFormat("######.######")

    var history : String = ""
    var currentResult : String = ""

    var dotControl : Boolean = true
    var buttonEqualsControl : Boolean = false

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.textViewResult.text = "0"


        mainBinding.btn0.setOnClickListener {
            onNumberClicked("0")
        }

        mainBinding.btn1.setOnClickListener {
            onNumberClicked("1")
        }

        mainBinding.btn2.setOnClickListener {
            onNumberClicked("2")
        }

        mainBinding.btn3.setOnClickListener {
            onNumberClicked("3")
        }

        mainBinding.btn4.setOnClickListener {
            onNumberClicked("4")
        }

        mainBinding.btn5.setOnClickListener {
            onNumberClicked("5")
        }

        mainBinding.btn6.setOnClickListener {
            onNumberClicked("6")
        }

        mainBinding.btn7.setOnClickListener {
            onNumberClicked("7")
        }

        mainBinding.btn8.setOnClickListener {
            onNumberClicked("8")
        }

        mainBinding.btn9.setOnClickListener {
            onNumberClicked("9")
        }



        mainBinding.btnAC.setOnClickListener {
            onButtonACClicked()
        }

        mainBinding.btnDELETE.setOnClickListener {
            number?.let {
                if (it.length == 1)
                {
                    onButtonACClicked()
                }
                else
                {
                    number = it.substring(0, it.length-1)
                    mainBinding.textViewResult.text = number
                    dotControl = !number!!.contains(".")
                }
            }
        }

        mainBinding.btnDIVIDE.setOnClickListener {

            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentResult).plus("/")

            if (operator == true)
            {
                when (status)
                {
                    "addition" -> add()
                    "subtraction" -> subtract()
                    "multiplication" -> multiply()
                    "division" -> divide()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }

                status = "division"
                operator = false
                number = null
                dotControl = true
            }
        }

        mainBinding.btnMULTIPLY.setOnClickListener {

            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentResult).plus("*")

            if (operator == true)
            {
                when (status)
                {
                    "addition" -> add()
                    "subtraction" -> subtract()
                    "multiplication" -> multiply()
                    "division" -> divide()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }

                status = "multiplication"
                operator = false
                number = null
                dotControl = true
            }
        }

        mainBinding.btnADD.setOnClickListener {

            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentResult).plus("+")

            if (operator == true)
            {
                when (status)
                {
                    "addition" -> add()
                    "subtraction" -> subtract()
                    "multiplication" -> multiply()
                    "division" -> divide()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }

                status = "addition"
                operator = false
                number = null
                dotControl = true
            }
        }

        mainBinding.btnSUBTRACT.setOnClickListener {

            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentResult).plus("-")

            if (operator == true)
            {
                when (status)
                {
                    "addition" -> add()
                    "subtraction" -> subtract()
                    "multiplication" -> multiply()
                    "division" -> divide()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }

                status = "subtraction"
                operator = false
                number = null
                dotControl = true
            }
        }

        mainBinding.btnDOT.setOnClickListener {
            if (dotControl)
            {
                number = if (number == null)
                {
                    "0."
                }
                else if (buttonEqualsControl)
                {
                    if (mainBinding.textViewResult.text.toString().contains("."))
                    {
                        mainBinding.textViewResult.text.toString()
                    }
                    else
                    {
                        mainBinding.textViewResult.text.toString().plus(".")
                    }
                }
                else
                {
                    "$number."
                }
                mainBinding.textViewResult.text = number
            }

            dotControl = false
        }

        mainBinding.btnEQUAL.setOnClickListener {

            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()

            if (operator == true)
            {
                when (status)
                {
                    "addition" -> add()
                    "subtraction" -> subtract()
                    "multiplication" -> multiply()
                    "division" -> divide()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }
                mainBinding.textViewHistory.text = history.plus(currentResult).plus("=").plus(mainBinding.textViewResult.text.toString())

            }
            operator = false
            dotControl = true
            buttonEqualsControl = true
        }

        mainBinding.toolbar.setOnMenuItemClickListener { item ->

            when (item.itemId)
            {
                R.id.settings_item -> {
                    val intent = Intent(this@MainActivity, ChangeThemeActivity::class.java)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }

                else -> return@setOnMenuItemClickListener false
            }
        }

    }


    fun onButtonACClicked(){
        number = null
        status = null
        mainBinding.textViewResult.text = "0"
        mainBinding.textViewHistory.text = ""
        firstNumber = 0.0
        lastNumber = 0.0
        dotControl = true
        buttonEqualsControl = false
    }

    fun onNumberClicked(clickedNumber : String){
        if (number == null)
        {
            number = clickedNumber
        }
        else if (buttonEqualsControl)
        {
            number = if (dotControl)
            {
                clickedNumber
            }
            else{
                mainBinding.textViewResult.text.toString().plus(clickedNumber)
            }

            firstNumber = number!!.toDouble()
            lastNumber = 0.0
            status = null
            mainBinding.textViewHistory.text = ""
        }
        else
        {
            number += clickedNumber
        }

        mainBinding.textViewResult.text = number

        operator = true
        buttonEqualsControl = false
    }

    fun add(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber += lastNumber
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)
    }

    fun subtract(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber -= lastNumber
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)
    }

    fun multiply(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber *= lastNumber
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)
    }

//    fun divide(){
//        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
//        if (lastNumber == 0.0)
//        {
//            Toast.makeText(applicationContext, "Cannot divide by zero", Toast.LENGTH_LONG).show()
//        }
//        else{
//            firstNumber = firstNumber / lastNumber
//            mainBinding.textViewResult.text = myFormatter.format(firstNumber)
//        }
//
//    }

//    fun divide() {
//        val lastNumber = mainBinding.textViewResult.text.toString().toDoubleOrNull()
//
//        if (lastNumber == null) {
//            Toast.makeText(applicationContext, "Invalid input", Toast.LENGTH_LONG).show()
//              return
//        } else if (lastNumber == 0.0) {
//            Toast.makeText(applicationContext, "Cannot divide by zero", Toast.LENGTH_LONG).show()
//              return
//        }
//
//        firstNumber /= lastNumber!!
//        mainBinding.textViewResult.text = firstNumber.toString()
//    }

    fun divide(){
        try {
            lastNumber = mainBinding.textViewResult.text.toString().toDouble()
            if (lastNumber == 0.0) {
                Toast.makeText(applicationContext, "Cannot divide by zero", Toast.LENGTH_LONG).show()
                return
            }
            firstNumber = firstNumber / lastNumber
            mainBinding.textViewResult.text = myFormatter.format(firstNumber)
        } catch (e: NumberFormatException) {
            Toast.makeText(applicationContext, "Invalid number format", Toast.LENGTH_LONG).show()
        }
    }


    override fun onResume() {
        super.onResume()

        sharedPreferences = this.getSharedPreferences("Dark Theme", Context.MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("switch", false)
        if (isDarkMode)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onPause() {
        super.onPause()

        sharedPreferences = this.getSharedPreferences("calculations", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val resultToSave = mainBinding.textViewResult.text.toString()
        val historyToSave = mainBinding.textViewHistory.text.toString()
        val numberToSave = number
        val statusToSave = status
        val operatorToSave = operator
        val dotToSave = dotControl
        val equalToSave = buttonEqualsControl
        val firstNumberToSave = firstNumber.toString()
        val lastNumberToSave = lastNumber.toString()

        editor.putString("result", resultToSave)
        editor.putString("history", historyToSave)
        editor.putString("number", numberToSave)
        editor.putString("status", statusToSave)
        editor.putBoolean("operator", operatorToSave)
        editor.putBoolean("dot", dotToSave)
        editor.putBoolean("equal", equalToSave)
        editor.putString("first", firstNumberToSave)
        editor.putString("last", lastNumberToSave)

        editor.apply()

    }

    override fun onStart() {
        super.onStart()

        sharedPreferences = this.getSharedPreferences("calculations", Context.MODE_PRIVATE)
        mainBinding.textViewResult.text = sharedPreferences.getString("result", "0")
        mainBinding.textViewHistory.text = sharedPreferences.getString("history", "")

        number = sharedPreferences.getString("number", null)
        status = sharedPreferences.getString("status", null)
        operator = sharedPreferences.getBoolean("operator", false)
        dotControl = sharedPreferences.getBoolean("dot", true)
        buttonEqualsControl = sharedPreferences.getBoolean("equal", false)
        firstNumber = sharedPreferences.getString("first", "0.0")!!.toDouble()
        lastNumber = sharedPreferences.getString("last", "0.0")!!.toDouble()
    }

}