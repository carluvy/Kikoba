package com.coolbanter.kikoba

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.coolbanter.kikoba.databinding.ActivityDatePickerBinding
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class DatePickerActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDatePickerBinding
    private lateinit var editName: TextInputEditText
    private lateinit var editAmount: TextInputEditText
    private lateinit var editDate: TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_date_picker)

        editName = binding.memberName
        editAmount = binding.memberContribution
        editDate = binding.date

        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)

        var date = editDate.text.toString()

        editDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                    view, year, monthOfYear, dayOfMonth ->
               date = " $dayOfMonth $monthOfYear, $year"
            }, year, month, day)
            datePickerDialog.show()
        }


    }
}