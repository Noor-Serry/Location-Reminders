package com.udacity.project4.ui.saveReminder

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
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
        showSaveMessage()
    }

    private fun showSaveMessage(){
        viewModel.showMessage.observe(viewLifecycleOwner) {
            if(it){
                Toast.makeText(requireContext(),"Save New Geofence",Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_saveReminderFragment_to_reminderListPage)
            }
            else Snackbar.make(requireView(),"Please Complete Data",Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun goToReminderList(view: View){
        getPermissions()
       viewModel.save()
    }


    @SuppressLint("SuspiciousIndentation")
    private fun getPermissions() {
        val permissions = arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        if(Build.VERSION_CODES.Q<= Build.VERSION.SDK_INT)
            ActivityCompat.requestPermissions(requireActivity(),permissions, 1)
    }

}



