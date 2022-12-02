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
        getPermissions()

    }
    @SuppressLint("SuspiciousIndentation")
    private fun getPermissions() {
        var permissions = arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        if(Build.VERSION_CODES.Q<=Build.VERSION.SDK_INT)
        requestPermissions(permissions, 1)
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
            Toast.makeText(requireContext(), "please allow permission", Toast.LENGTH_SHORT).show()
        else {
            viewModel.save()
            findNavController().navigate(R.id.action_saveReminderFragment_to_reminderListPage)
        }
    }
}



