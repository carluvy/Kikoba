package com.coolbanter.kikoba


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolbanter.kikoba.databinding.ActivityListBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*


class ListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityListBinding
    private lateinit var mRecyclerview: RecyclerView
    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mChildListener : ChildEventListener
    private lateinit var mAdapter: ContributionAdapter
    private lateinit var mContributors : ArrayList<KikobaContribution>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)



//        mFirebaseDatabase = FirebaseDatabase.getInstance()
//        mDatabaseReference = mFirebaseDatabase.reference.child("kikobacontributions")

        mRecyclerview = binding.recyclerview
        mRecyclerview.layoutManager = LinearLayoutManager(this@ListActivity)




        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mFirebaseDatabase.reference.child("kikobacontributions")

        val query = mDatabaseReference.orderByChild("kikobacontributions")

        val options = FirebaseRecyclerOptions.Builder<KikobaContribution>()
            .setQuery(query, KikobaContribution::class.java)
            .build()


        mAdapter = ContributionAdapter(applicationContext, options)
        mRecyclerview.adapter = mAdapter


    }

    override fun onStart() {
        super.onStart()
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }



//        mChildListener = object : ChildEventListener {
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                val d = "Boring"
//                Log.d(d, "onChildAdded" + snapshot.key)
//                val contribution: KikobaContribution? =
//                    snapshot.getValue(KikobaContribution::class.java)
//
//                if (contribution != null) {
//                    mContributors = ArrayList<KikobaContribution>()
//                    contribution.name?.let { Log.d(d, "member: " + it) }
//                    mContributors.add(contribution)
//
//                }
//                mRecyclerview.apply {
//                    layoutManager = LinearLayoutManager(this@ListActivity)
////                    mContributors = ArrayList()
//                    mAdapter = ContributionAdapter(applicationContext, mContributors)
//                    adapter = mAdapter
//                }
//
//
//            }
//
//
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//
//
//            }
//
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//
//            }
//
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//        }
//        mDatabaseReference.addChildEventListener(mChildListener)


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.insert_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.insert_menu -> {
                val intent = Intent(this, InsertActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}