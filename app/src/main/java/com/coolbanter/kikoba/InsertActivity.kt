package com.coolbanter.kikoba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.coolbanter.kikoba.databinding.ActivityInsertBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertActivity : AppCompatActivity() {
    private lateinit var binding : ActivityInsertBinding
    private lateinit var mFirebaseDatabase : FirebaseDatabase
    private lateinit var mDatabaseReference: DatabaseReference

    private lateinit var name : TextInputEditText
    private lateinit var amount : TextInputEditText
    private lateinit var date : TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insert)

//        FirebaseUtil.openReference("kikobacontributions")

//        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mFirebaseDatabase.reference.child("kikobacontributions")

        name = binding.memberName
        amount = binding.memberContribution
        date = binding.date

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
                saveDeal()
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

    private fun saveDeal() {
        val memberName = name.text.toString()
        val memberContribution = amount.text.toString()
        val contributionDate = date.text.toString()

        val contribution = KikobaContribution(memberName, memberContribution, contributionDate, "")
        mDatabaseReference.push().setValue(contribution)
        startActivity(Intent(this, ListActivity::class.java))

    }
}



