package com.udacity.project4.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnSuccessListener
import com.udacity.project4.databinding.FragmentMapsBinding
import com.udacity.project4.R


class Maps() : Fragment() ,OnMapReadyCallback ,OnSuccessListener<Location> ,GoogleMap.OnPoiClickListener , MenuProvider,
    GoogleMap.OnMyLocationButtonClickListener {

    private lateinit var  googleMap: GoogleMap
    private lateinit var   fusedLocationProviderClient : FusedLocationProviderClient
    lateinit var binding : FragmentMapsBinding
    var directions : MapsDirections.ActionMapsToSaveReminderFragment? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
            binding = FragmentMapsBinding.inflate(layoutInflater)
        binding.locationBySearch.setOnClickListener(this::goToFindLocationFragment)
        binding.save.setOnClickListener(this::goToSaveReminderFragment)
           return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        mapFragment?.getMapAsync(this)

    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        getPermissions()
        this.googleMap = googleMap
        setMapStyle()
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.setOnPoiClickListener(this)
         fusedLocationProviderClient.lastLocation.addOnSuccessListener(this)
         googleMap.setOnMapLongClickListener(this::onMapLongClickListener)
        addPoiBySearching()

    }

    fun setMapStyle(){
        googleMap.setMapStyle(
            MapStyleOptions.
            loadRawResourceStyle(requireContext(), R.raw.map_style))
    }

    override fun onSuccess(location: Location?) {
        if(location!=null){
            var latLng =LatLng(location.latitude,location.longitude)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15.0f))
        googleMap.addMarker(MarkerOptions().position(latLng))
        }

    }

    override fun onPoiClick(poi: PointOfInterest) {
        with(poi){
      addGeofencing(latLng.latitude,latLng.longitude,name)}

    }

    private fun addPoiBySearching(){
        val args : MapsArgs by navArgs()
        if(!args.name.isNullOrEmpty())
            with(args){
            addGeofencing(latitude.toDouble(),longitude.toDouble(),name!!)}
    }

    private fun addGeofencing(latitude: Double , longitude: Double, name : String){
        googleMap.clear()
        val latLng = LatLng(latitude , longitude)
        googleMap.addMarker(MarkerOptions().position(latLng).title(name))
        googleMap.addCircle(CircleOptions().center(latLng).
        radius(100.0).fillColor(R.color.light_blue).strokeColor(R.color.light2_blue))
        directions = MapsDirections.actionMapsToSaveReminderFragment(
                latitude.toFloat(),
                longitude.toFloat(),
                name
            )

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.map_type_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        googleMap.mapType =
            when(menuItem.itemId){
              R.id.normal_map ->  GoogleMap.MAP_TYPE_NORMAL
              R.id.terrain_map -> GoogleMap.MAP_TYPE_TERRAIN
              R.id.satellite_map -> GoogleMap.MAP_TYPE_SATELLITE
            else -> GoogleMap.MAP_TYPE_HYBRID
          }
        return true
    }



    private fun goToFindLocationFragment(view : View){
        findNavController().navigate(R.id.action_maps_to_findLocation)
    }

    private fun goToSaveReminderFragment(view : View){
        if(directions!=null)
            findNavController().navigate(directions!!)

        else   Toast.makeText(requireContext(),"Please Select Poi First",Toast.LENGTH_SHORT).show()

    }

    @SuppressLint("MissingPermission")
    override fun onMyLocationButtonClick(): Boolean {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener(this)
        return true
    }
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
            Toast.makeText(requireContext(), "please allow permission", Toast.LENGTH_SHORT).show()
        else {
            googleMap.isMyLocationEnabled = true
            googleMap.setOnMyLocationButtonClickListener(this)
            openGps()
        }
    }

    private fun getPermissions() {
        var permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION
            , Manifest.permission.ACCESS_COARSE_LOCATION)
        requestPermissions(permissions, 1)
    }

    private fun openGps() {
        var locationRequest = LocationRequest.create().setInterval(1000).setFastestInterval(500)
        var locationSetting = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            .setAlwaysShow(true).build()
        var task = LocationServices.getSettingsClient(requireContext()).checkLocationSettings(locationSetting)
        task.addOnFailureListener(this::onGpsFailureListener)

    }
    @SuppressLint("RestrictedApi")
    private  fun onGpsFailureListener(exception: Exception){
        if (exception is ResolvableApiException)
            exception.startResolutionForResult(requireActivity(),1)
    }

    private fun onMapLongClickListener(latLng: LatLng){
       addGeofencing(latLng.latitude,latLng.longitude,"placeSelected")
    }


}