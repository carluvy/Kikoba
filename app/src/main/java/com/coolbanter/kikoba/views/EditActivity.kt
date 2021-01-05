package com.coolbanter.kikoba.views


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.coolbanter.kikoba.utils.ContributionAdapter
import com.coolbanter.kikoba.R
import com.coolbanter.kikoba.utils.Utils
import com.coolbanter.kikoba.databinding.ActivityEditBinding
import com.coolbanter.kikoba.model.KikobaContribution
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.HashMap

class EditActivity : AppCompatActivity() {

//    companion object {
//        const val PICK_FILE_CODE = 24
//    }

    private lateinit var binding: ActivityEditBinding
    private lateinit var editName: TextInputEditText
    private lateinit var editAmount: TextInputEditText
    private lateinit var editDate: TextInputEditText
    private lateinit var mAdapter: ContributionAdapter

    private lateinit var ref: DatabaseReference
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit)

        editName = binding.memberName
        editAmount = binding.memberContribution
        editDate = binding.date


        ref = Utils.getDatabaseReference()!!
        val fileKey = intent.getStringExtra("ContributorKey")
        databaseReference = Utils.getDatabaseReference()!!.child("name").child(fileKey!!)
        databaseReference = Utils.getDatabaseReference()!!.child("amount").child(fileKey)
        databaseReference = Utils.getDatabaseReference()!!.child("date").child(fileKey)
        databaseReference = Utils.getDatabaseReference()!!.child("imageUrl").child(fileKey)

        ref.child(fileKey).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val memberName = snapshot.child("name").value.toString()
                    val contribution = snapshot.child("amount").value.toString()
                    val day = snapshot.child("date").value.toString()
                    val image = snapshot.child("imageUrl").value.toString()

                    editName.setText(memberName)
                    editAmount.setText(contribution)
                    editDate.setText(day)
                    binding.profilePic.setImageURI(Uri.parse(image))

                    binding.btnDelete.setOnClickListener {
                        ref.child(fileKey).removeValue().addOnCompleteListener {
                            startActivity(
                                Intent(
                                    this@EditActivity,
                                    ListActivity::class.java
                                )
                            )
//
//                        }

                        }


                    }


//
//                    binding.btnDelete.setOnClickListener {
//                        databaseReference.child("kikobacontributions").child("imageUrl") .removeValue()
//                            .addOnSuccessListener {
//
//                            startActivity(
//                                Intent(
//                                    this@EditActivity,
//                                    ListActivity::class.java
//                                )
//                            )
//                        }
//                    }

                    update()


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun update() {
        binding.btn.setOnClickListener {
            ref = Utils.getDatabaseReference()!!
            val name = editName.text.toString()
            val amount = editAmount.text.toString()
            val date = editDate.text.toString()
            val image = binding.profilePic.toString()
            val me = KikobaContribution(name, amount, date, image)
            val fileKey = intent.getStringExtra("ContributorKey")
//            val databaseReferenceName =
//                FirebaseDatabase.getInstance().reference.child("name").child(
//                    fileKey!!
//                )
//            val databaseReferenceAmount =
//                FirebaseDatabase.getInstance().reference.child("amount").child(
//                    fileKey
//                )
//            val databaseReferenceDate =
//                FirebaseDatabase.getInstance().reference.child("date").child(
//                    fileKey
//                )

            val nameMap: MutableMap<String, Any> = HashMap()
            nameMap["$fileKey"] = me
//                val amountMap: MutableMap<String, Any> = HashMap()
//                amountMap["amount"] = amount
            ref.updateChildren(nameMap).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Updated", Toast.LENGTH_LONG).show()
                } else {

                    Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()

                }

            }

            startActivity(
                Intent(
                    this@EditActivity,
                    ListActivity::class.java
                )
            )

        }



    }

//    override fun onStart() {
//        super.onStart()
//
//        ref = Utils.getDatabaseReference()!!
//        val name = editName.text.toString()
//        val amount = editAmount.text.toString()
//        val date = editDate.text.toString()
//        val me = KikobaContribution(name, amount, date)
//
//    }


//                ref.child(fileKey).addValueEventListener(object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        binding.btn.setOnClickListener {
//
//                            val name = snapshot.child("name").value.toString()
//                            val amount = snapshot.child("amount").value.toString()
//                            val date = snapshot.child("date").value.toString()
//
//                            val i = editName.setText(name)
//                            val j = editAmount.setText(amount)
//                            val k = editDate.setText(date)
//
//                            databaseReferenceName.updateChildren(i)
//                            databaseReferenceAmount.push().setValue(j)
//                            databaseReferenceDate.push().setValue(k)
//
//
////
//
//
//                            Toast.makeText(this@EditActivity, "updated", Toast.LENGTH_SHORT).show()
//                            startActivity(Intent(this@EditActivity, ListActivity::class.java))
//                        }
//
//
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        TODO("Not yet implemented")
//                    }
//
//
//                })
//
//
//            }



//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == PICK_FILE_CODE && resultCode == RESULT_OK) {
//            editName.text = data.data!!
//            isImageAdded = true
//            mImage.setImageURI(fileUri)
//        }
//    }
}