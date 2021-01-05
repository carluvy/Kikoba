package com.coolbanter.kikoba.utils

import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


object Utils {

    private var mDatabase: FirebaseDatabase? = null
    private var mDatabaseReference : DatabaseReference? = null
    private var mAuth : FirebaseAuth? = null
    private var mAuthUI : AuthUI? = null
    private var mStorageRef : StorageReference? = null

    fun getDatabase(): FirebaseDatabase? {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance()
            mDatabase!!.setPersistenceEnabled(true)
        }
        return mDatabase
    }

    fun getDatabaseReference() : DatabaseReference? {
        if (mDatabaseReference == null) {
            mDatabaseReference = mDatabase?.reference?.child("kikobacontributions")
        }
        return mDatabaseReference
    }

    fun getAuth() : FirebaseAuth? {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance()

        }
        return mAuth
    }

    fun getAuthUI() : AuthUI {
        if (mAuthUI == null) {
            mAuthUI = AuthUI.getInstance()

        }
        return mAuthUI as AuthUI
    }
    fun getStorageRef(): StorageReference? {
        if (mStorageRef == null) {
            mStorageRef = FirebaseStorage.getInstance().reference.child("Profile Pics")
        }
        return mStorageRef
    }
}