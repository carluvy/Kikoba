package com.coolbanter.kikoba

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coolbanter.kikoba.databinding.ContributionListBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions


class ContributionAdapter(context: Context, options: FirebaseRecyclerOptions<KikobaContribution>) : FirebaseRecyclerAdapter<KikobaContribution, ContributionAdapter.ContributionViewHolder?>(
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
    }

//    override fun getItemCount(): Int {
////        notifyItemInserted(contribution.size-1)
//        return contribution.size
//
//    }

    inner class ContributionViewHolder(private var itemBinding: ContributionListBinding) : RecyclerView.ViewHolder(
        itemBinding.root
    ) {
            var name = itemBinding.nameTxt
            var amount = itemBinding.amountTxt
            var date = itemBinding.dateTxt




//        mDatabaseReference.addChildEventListener(mChildListener)
    }

//    override fun onBindViewHolder(holder: ContributionViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }
}