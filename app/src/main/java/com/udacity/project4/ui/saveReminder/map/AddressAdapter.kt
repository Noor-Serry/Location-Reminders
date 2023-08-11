package com.udacity.project4.ui.saveReminder.map


import android.view.ViewGroup
import com.example.domain.entity.Address
import com.udacity.project4.R
import com.udacity.project4.databinding.AddressHolderBinding
import com.udacity.project4.utils.BaseAdapter


class AddressAdapter(val onItemClick : ((Address)->Unit)):
    BaseAdapter<Address, AddressHolderBinding>(R.layout.address_holder) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder<AddressHolderBinding> {
       val holder = super.onCreateViewHolder(parent, viewType)
       val binding = holder.binding
       binding.root.setOnClickListener { onItemClick(binding.address!!) }
       return holder
    }
    override fun onBindViewHolder(holder: Holder<AddressHolderBinding>, position: Int) {
        holder.binding.address = getDifferData().currentList[position]
    }

    override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean
            =  oldItem.addressDetails == newItem.addressDetails


}




