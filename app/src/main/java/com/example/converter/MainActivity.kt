package com.example.converter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import com.example.converter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculate() }

        binding.converterEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }

    private fun calculate() {
        val stringInField = binding.converterEditText.text.toString()
        val value = stringInField.toDoubleOrNull()

        if (value == null || value == 0.0) {
            displayValue(0.0)
            return
        }

        var answer = when (binding.selectConversionOption.checkedRadioButtonId) {
            R.id.to_fluid_ounces -> value * 0.03381
            else -> value * 29.57355
        }

        if (binding.roundUpSwitch.isChecked) {
            answer = kotlin.math.ceil(answer)
        }

        displayValue(answer)
    }

    private fun displayValue(answer: Double) {
        binding.answer.text = answer.toString()
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            //Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}