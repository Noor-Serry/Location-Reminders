package noor.serry.locationreminders.ui.reminderList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import noor.serry.locationreminders.R
import noor.serry.locationreminders.data.ReminderDataItem
import noor.serry.locationreminders.databinding.ItReminderBinding


class RecyclerAdapter (): RecyclerView.Adapter<RecyclerAdapter.Holder> (){

  private lateinit var dataItem : List<ReminderDTO>

       @JvmName("setDataItem1")
       fun setDataItem(dataItem : List<ReminderDTO>){
         this.dataItem = dataItem
       }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = DataBindingUtil.inflate<ItReminderBinding>(LayoutInflater.from(parent.context),
            R.layout.it_reminder,parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.item = dataItem.get(position)
    }

    override fun getItemCount(): Int {
       return dataItem.size
    }

    class Holder(var binding : ItReminderBinding) :RecyclerView.ViewHolder(binding.root)

}