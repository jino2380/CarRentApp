package com.example.carrentapp

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar
import java.util.Locale

class HomeActivity : AppCompatActivity() {
    lateinit var idEdtPickup: EditText
    lateinit var idEdtDropoff: EditText
    lateinit var idEdtPickupDate: EditText
    lateinit var idEdtDropoffDate: EditText
    lateinit var idSearch: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        idEdtPickup = findViewById(R.id.idEdtPickup)
        idEdtDropoff= findViewById(R.id.idEdtDropoff)
        idEdtPickupDate = findViewById(R.id.idEdtPickupDate)
        idEdtDropoffDate = findViewById(R.id.idEdtDropoffDate)
        idSearch = findViewById(R.id.idBtn)

        idEdtPickupDate.setOnClickListener {
            showDatePickerDialog()
        }
        idEdtDropoffDate.setOnClickListener {
            showDatePickerDialogDropOff()
        }


        idSearch.setOnClickListener {
            if(validateInput()){
                if(idEdtPickupDate.text.isNotEmpty() ){
                    if( !isValidDate(idEdtPickupDate.text.toString())  ) {
                        Toast.makeText(this, "Input is valid!", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        openLinkInBrowser("https://www.kayak.com/in?a=awesomecars&url=/cars/${idEdtPickup.text}/${idEdtDropoff.text}/${idEdtPickupDate.text}/${idEdtDropoffDate.text}")
                    }
                }
                else{
                    Toast.makeText(this, "Select Pickup date", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun validateInput(): Boolean {
        val pickupLocation = idEdtPickup.text.toString().trim()

        if (TextUtils.isEmpty(pickupLocation)) {
            idEdtPickup.error = "Pickup location cannot be empty"
            return false
        }
        if (!isValidLocationFormat(pickupLocation)) {
            idEdtPickup.error = "Invalid location format"
            return false
        }

        return true
    }

    private fun isValidLocationFormat(location: String): Boolean {
        return location.all { it.isLetter() || it.isWhitespace() }
    }
    

    private fun isValidDate(dateString: String): Boolean {
        val dateFormat = java.text.SimpleDateFormat("YYYY-MM-DD", Locale.getDefault())
        val selectedDate = dateFormat.parse(dateString)

        return selectedDate != null
    }



    private fun openLinkInBrowser(baseUrl: String) {
        val urlBuilder = StringBuilder(baseUrl)

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlBuilder.toString()))
        startActivity(intent)
    }


    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
//            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            idEdtPickupDate.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showDatePickerDialogDropOff() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
            idEdtDropoffDate.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }
}