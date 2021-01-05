package com.coolbanter.kikoba.utils

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.coolbanter.kikoba.R
import com.coolbanter.kikoba.databinding.ContributionListBinding
import com.coolbanter.kikoba.model.KikobaContribution
import com.coolbanter.kikoba.views.EditActivity
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions


class ContributionAdapter(private var context: Context, options: FirebaseRecyclerOptions<KikobaContribution>) :
    FirebaseRecyclerAdapter<KikobaContribution, ContributionAdapter.ContributionViewHolder?>(
    options
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributionViewHolder {
        val itemBinding = ContributionListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContributionViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        holder: ContributionViewHolder,
        position: Int,
        mem: KikobaContribution
    ) {
        holder.name.text = mem.name
        holder.amount.text = mem.amount
        holder.date.text= mem.date



//        val w = mDatabaseReference.orderByChild("Profile Info")

        Glide.with(context)
            .load(mem.imageUrl)
            .centerCrop()
            .circleCrop()
            .apply(RequestOptions().override(600, 200))
            .placeholder(R.drawable.common_full_open_on_phone)
            .into(holder.profileImage)





        holder.itemView.setOnClickListener {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra("ContributorKey", getRef(position).key)
            context.startActivity(intent)


        }


    }
//    inner class SwipeToDeleteCallback(adapter : ContributionAdapter) :
//        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//        private var adapter : ContributionAdapter
//            get() {
//                adapter.SwipeToDeleteCallback()
//                return adapter
//            }
//
//        init {
//            adapter = adapter
//        }
//        override fun onMove(
//            recyclerView: RecyclerView,
//            viewHolder: RecyclerView.ViewHolder,
//            target: RecyclerView.ViewHolder
//        ): Boolean {
//            TODO("Not yet implemented")
//        }
//
//        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            val pos = viewHolder.adapterPosition
//            adapter.deleteItem(pos)
//        }

//    }
//
//    private fun SwipeToDeleteCallback() {
//        TODO("Not yet implemented")
//    }


    inner class ContributionViewHolder(private var itemBinding: ContributionListBinding) : RecyclerView.ViewHolder(
        itemBinding.root
    ) {
            var name = itemBinding.nameTxt
            var amount = itemBinding.amountTxt
            var date = itemBinding.dateTxt
        var profileImage = itemBinding.profilePic





//        mDatabaseReference.addChildEventListener(mChildListener)
    }

//    override fun onBindViewHolder(holder: ContributionViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }
}