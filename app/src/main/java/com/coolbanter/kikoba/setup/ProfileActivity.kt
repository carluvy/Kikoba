package com.coolbanter.kikoba.setup

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.coolbanter.kikoba.views.EditProfileActivity
import com.coolbanter.kikoba.R
import com.coolbanter.kikoba.utils.Utils
import com.coolbanter.kikoba.databinding.ActivityProfileBinding
import com.coolbanter.kikoba.model.KikobaContribution
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding
    private lateinit var name : MaterialTextView
    private lateinit var amount : MaterialTextView
    private lateinit var editProfile : MaterialButton
    private lateinit var profileImage : ImageView
    private lateinit var mStorageRef : StorageReference
    private lateinit var mDatabaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        mDatabaseReference = Utils.getDatabaseReference()!!
        mStorageRef = Utils.getStorageRef()!!

        name = binding.nameTxt
        amount = binding.amountTxt
        editProfile = binding.editProfile
        profileImage = binding.profilePic

        editProfile.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
        showInfo()
    }

    private fun showInfo() {
//        val w = intent.getStringExtra("Profile Pic")

        val upload = intent.getParcelableExtra<Uri>("Profile Pic")
        if (upload != null) {
            val nameIntent = intent.extras
            if (nameIntent != null) {
                val text = nameIntent.getString("Name")
                name.text = text
            }
//            val pic = upload.getString("Profile Pic")

//            val uri = Uri.parse(upload.getString("Profile Pic"))
            profileImage.setImageURI(Uri.parse(upload.toString()))

            val profInfo = KikobaContribution(name.text.toString(), "", "", upload.toString())
            mDatabaseReference.push().setValue(profInfo)
        }



//

    }

}










//            val nameIntent = intent.extras
//            if (nameIntent != null) {
//                val text = nameIntent.getString("Name")
//                name.text = text
//            }




//        name.text = profileInfo.getStringExtra("name")
//        profileImage = profileInfo.getByteExtra("profile")

