package com.ggendrick.epamtesttask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import android.widget.Toast
import com.ggendrick.epamtesttask.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity()  {
    private lateinit var binding:ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences
    val SHAREDPREFSTAG="mysharedpref"
    val SHAREDPREFSTAG_GENERATION="generation"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = getSharedPreferences(SHAREDPREFSTAG, MODE_PRIVATE)
        val datePickerDialog = DatePickerDialog(this)
        updateGenerationTextView()
        datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
            updateGeneration(year)
        }

        binding.setDateButton.setOnClickListener{
            datePickerDialog.show()
        }

    }

    fun updateGeneration(year:Int){
        val generation=when(year){
           in 1946..1964->getString(R.string.babyBoomer)
           in 1965..1980->getString(R.string.GenerationX)
           in 1981..1996->getString(R.string.generationY)
           in 1997..2012->getString(R.string.generationZ)
           else->getString(R.string.genarationCalculatingError)
        }
        if (generation.equals(getString(R.string.genarationCalculatingError)))
            Toast.makeText(applicationContext,generation,Toast.LENGTH_SHORT).show()
        else  {

            binding.calculateGenerationButton.setOnClickListener{
                val spEditor=sharedPref.edit()
                spEditor.putString(SHAREDPREFSTAG_GENERATION,generation)
                spEditor.apply()
                updateGenerationTextView()
            }
        }

    }
    fun updateGenerationTextView(){
        binding.generationTextView.text=getString(R.string.yourGeneration)+sharedPref.getString(SHAREDPREFSTAG_GENERATION,getString(R.string.pleaseEnterDateOfBirth))
    }
}