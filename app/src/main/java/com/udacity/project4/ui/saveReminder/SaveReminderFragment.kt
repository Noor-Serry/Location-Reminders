package com.udacity.project4.ui.saveReminder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentSaveReminderBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SaveReminderFragment : Fragment() {
    lateinit var binding: FragmentSaveReminderBinding
     private val viewModel: SaveReminderViewModel by sharedViewModel ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaveReminderBinding.inflate(inflater)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: SaveReminderFragmentArgs by navArgs()
        viewModel.name = args.name
        viewModel.longitude = args.longitude.toDouble()
        viewModel.latitude = args.latitude.toDouble()
        binding.saveGeofence.setOnClickListener(this::goToReminderList)
    }

    private fun goToReminderList(view: View){
        viewModel.save()
        findNavController().navigate(R.id.action_saveReminderFragment_to_reminderListPage)
    }

}



