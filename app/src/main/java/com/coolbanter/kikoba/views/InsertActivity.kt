package com.coolbanter.kikoba.views

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.coolbanter.kikoba.R
import com.coolbanter.kikoba.utils.Utils
import com.coolbanter.kikoba.databinding.ActivityInsertBinding
import com.coolbanter.kikoba.model.KikobaContribution
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class InsertActivity : AppCompatActivity() {
    private lateinit var binding : ActivityInsertBinding
    private lateinit var mFirebaseDatabase : FirebaseDatabase
    private lateinit var mDatabaseReference: DatabaseReference

    private lateinit var name : TextInputEditText
    private lateinit var amount : TextInputEditText
    private lateinit var date : TextInputEditText
    private lateinit var dateBtn : MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insert)

        mFirebaseDatabase = Utils.getDatabase()!!
        mDatabaseReference = Utils.getDatabaseReference()!!

        name = binding.memberName
        amount = binding.memberContribution
        date = binding.date
        dateBtn = binding.btn

        val c: Calendar = Calendar.getInstance()
        val years: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)

//        var dates = date.text.toString()

        dateBtn.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                    view, year, monthOfYear, dayOfMonth ->
                date.setText(dayOfMonth + monthOfYear + year)

            }, years, month, day)
            datePickerDialog.show()
        }

//        binding.btn.setOnClickListener {
//            startActivity(Intent(this, ListActivity::class.java))
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_menu -> {
                saveInfo()
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                clean()
                true
            } else -> {
                super.onOptionsItemSelected(item)
            }


        }



    }

    private fun clean() {
        name.setText("")
        amount.setText("")
        date.setText("")
        name.requestFocus()
    }

    private fun saveInfo() {
        val memberName = name.text.toString()
        val memberContribution = amount.text.toString()
        val contributionDate = date.text.toString()

        val contribution = KikobaContribution(memberName, memberContribution, contributionDate, "")
        mDatabaseReference.push().setValue(contribution)
        startActivity(Intent(this, ListActivity::class.java))

    }
}



