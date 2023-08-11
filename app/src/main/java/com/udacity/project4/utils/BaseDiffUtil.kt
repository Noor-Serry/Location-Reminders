package com.udacity.project4.utils

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class BaseDiffUtil<T >(
    private val customAreItemsSame: (oldItem: T, newItem: T) -> Boolean) : DiffUtil.ItemCallback<T>(){

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return customAreItemsSame(oldItem, newItem)
    }


    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =  oldItem == newItem



}