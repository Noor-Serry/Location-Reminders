package com.udacity.project4.ui.reminders


import com.example.domain.entity.Reminder
import com.udacity.project4.R
import com.udacity.project4.databinding.ReminderItemBinding
import com.udacity.project4.utils.BaseAdapter


class ReminderAdapter : BaseAdapter<Reminder, ReminderItemBinding>(R.layout.reminder_item) {

    override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean =
        oldItem.id == newItem.id


    override fun onBindViewHolder(holder: Holder<ReminderItemBinding>, position: Int) {
        holder.binding.item = getDifferData().currentList[position]
    }

}



