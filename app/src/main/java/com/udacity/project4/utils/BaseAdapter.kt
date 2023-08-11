package com.udacity.project4.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


abstract class BaseAdapter<T,VB : ViewDataBinding>(@LayoutRes private val layoutID: Int) : RecyclerView.Adapter<BaseAdapter.Holder<VB>>() {

    private val differCallback = BaseDiffUtil(::areItemsTheSame)
    private val differData = AsyncListDiffer(this,differCallback)

    fun getDifferData() = differData

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder<VB> {
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutID, parent, false)
        val binding = DataBindingUtil.bind<VB>(view)
        return Holder(binding!!)
     }

     abstract fun areItemsTheSame(oldItem: T, newItem: T):Boolean

     fun setNewDataSet(newDataSet:List<T>?){
            differData.submitList(newDataSet)
     }

    override fun getItemCount(): Int = differData.currentList.size

    class Holder<VB : ViewBinding> (val binding : VB) : RecyclerView.ViewHolder(binding.root)

}