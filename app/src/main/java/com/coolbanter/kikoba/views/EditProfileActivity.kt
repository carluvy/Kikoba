package com.coolbanter.kikoba.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.coolbanter.kikoba.R
import com.coolbanter.kikoba.utils.Utils
import com.coolbanter.kikoba.databinding.ActivityEditProfileBinding
import com.coolbanter.kikoba.setup.ProfileActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference



class EditProfileActivity : AppCompatActivity() {

    companion object {
        const val PICK_FILE_CODE = 1000
    }
    private lateinit var name : TextInputEditText
    private lateinit var uploadPic : MaterialButton
    private lateinit var profileImage : ImageView
    private lateinit var binding : ActivityEditProfileBinding
    private lateinit var imageUri : Uri
    private lateinit var mStorageRef : StorageReference
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mDatabaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)

        mDatabase = Utils.getDatabase()!!
        mDatabaseReference = Utils.getDatabaseReference()!!
        mStorageRef = Utils.getStorageRef()!!

        profileImage = binding.profilePic
        imageUri = Uri.parse(profileImage.toString())

        name = binding.nameTxt
        uploadPic = binding.uploadPic

        profileImage.setOnClickListener {
            val intent = Intent()
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.action = Intent.ACTION_OPEN_DOCUMENT
//            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            startActivityForResult(intent, PICK_FILE_CODE)

        }
        uploadPic.setOnClickListener {
//            addName()
//

            clean()
            startActivity(Intent(this, ListActivity::class.java))
        }
    }

    private fun clean() {
        imageUri = Uri.parse(profileImage.toString())
        name.setText("")
    }

//    private fun addName() {
//        val nameIntent = Intent(this, ProfileActivity::class.java)
//        val name = name.text.toString()
//        nameIntent.putExtra("Name", name)
//        startActivity(nameIntent)
//    }



//        val image = Intent()
//        image.action = Intent.ACTION_SEND
//        image.putExtra(Intent.EXTRA_STREAM, Uri.parse(profileImage.toString()))
//        image.type = "image/*"
//        startActivity(Intent.createChooser(image, "Profile Pic"))

//        val imageUri = imageUri.toString()
//        val profileInfo = KikobaContribution("", "", "memberName",  imageUri)
//        mDatabaseReference.push().setValue(profileInfo)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_CODE && data != null) {
            imageUri = data.data!!
            profileImage.setImageURI(imageUri)


//            val profInfo = KikobaContribution(name.text.toString(), "", "", imageUri.toString())
//            mDatabaseReference.push().setValue(profInfo)




            val upload = Intent(this, ProfileActivity::class.java)
            val image = Bundle()
            image.putParcelable("Profile Pic", imageUri)
            upload.putExtras(image)
//            upload.putExtra("Profile Pic", imageUri)
            val name = name.text.toString()
            upload.putExtra("Name", name)
            startActivity(upload)



        }
    }
}