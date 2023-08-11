package com.udacity.project4.ui.saveReminder


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.entity.Reminder
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentSaveReminderBinding
import com.udacity.project4.ui.service.GeofenceTransitionsService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SaveReminderFragment : Fragment() {

    lateinit var binding: FragmentSaveReminderBinding
      val viewModel: SaveReminderViewModel by viewModels ()
    private  var registerPermission =registerForActivityResult(RequestMultiplePermissions(),::onRequestPermissionsResult)
    private  var registerGps    = registerForActivityResult(StartIntentSenderForResult(),::onRequestGPSResult)

    private val userCoordinates by lazy {
        val args: SaveReminderFragmentArgs by navArgs()
        args.coordinates
    }


    private val geofencingID by lazy {
        System.currentTimeMillis()
    }


    private var permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ).apply {
        if(Build.VERSION_CODES.Q<= Build.VERSION.SDK_INT)
            plus(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        if(Build.VERSION_CODES.TIRAMISU<= Build.VERSION.SDK_INT)
            plus(Manifest.permission.POST_NOTIFICATIONS)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaveReminderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAddressInfo(userCoordinates)

        lifecycleScope.launch {
            viewModel.addressState.collect{
                binding.locationDetailsTextView.text = it?.addressDetails
            }
        }

        lifecycleScope.launch {
            viewModel.errorMessage.collect{it?.let {
                showToast(it)
            }

            }
        }

        lifecycleScope.launch {
            viewModel.geofencingAddedSuccessfully.collect{
                if(it){
                    val intent = Intent(requireContext(), GeofenceTransitionsService::class.java)
                    ContextCompat.startForegroundService(requireContext().applicationContext, intent)
                findNavController().popBackStack(R.id.remindersFragment , false)}
            }
        }

        binding.saveGeofence.setOnClickListener {
            registerPermission.launch(permissions)
        }}


        private fun onRequestPermissionsResult(result : Map<String,Boolean>) {
            if(result.containsValue(false))
                showToast(getString(R.string.please_allow_permission))
            else
                openGps()
        }

    private fun openGps() {
        val locationRequest = LocationRequest.Builder(1000).build()
        val locationSetting = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            .setAlwaysShow(true).build()
        LocationServices.getSettingsClient(requireContext())
            .checkLocationSettings(locationSetting)
            .addOnFailureListener(::onGPSFailureListener)
            .addOnSuccessListener { viewModel.saveReminder(getReminder()) }
    }

    private fun onGPSFailureListener( exception: Exception){
        val intentSender = (exception as ResolvableApiException).resolution.intentSender
        registerGps.launch(IntentSenderRequest.Builder(intentSender).build())
    }

    private fun onRequestGPSResult(activityResult :ActivityResult){
        if(activityResult.resultCode == Activity.RESULT_OK)
            viewModel.saveReminder(getReminder())
            else showToast(getString(R.string.please_open_the_gps))
    }


    private fun getReminder() = Reminder(
        title = binding.title.text.toString(),
        description = binding.description.text.toString(),
        locationName = binding.locationDetailsTextView.text.toString(),
        coordinates = userCoordinates,
        id = geofencingID
    )

    private fun showToast(text : String ) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

}



