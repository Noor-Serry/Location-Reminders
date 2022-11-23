package com.udacity.project4.ui.map

import android.location.Location
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.gms.tasks.OnSuccessListener
import com.udacity.project4.databinding.FragmentMapsBinding
import com.udacity.project4.R


class Maps() : Fragment() ,OnMapReadyCallback ,OnSuccessListener<Location> ,GoogleMap.OnPoiClickListener , MenuProvider {

    private lateinit var  googleMap: GoogleMap
    private lateinit var   fusedLocationProviderClient : FusedLocationProviderClient
    lateinit var binding : FragmentMapsBinding
    var directions : MapsDirections.ActionMapsToSaveReminderFragment? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
            binding = FragmentMapsBinding.inflate(layoutInflater)
        binding.searchView.setOnClickListener(this::goToFindLocationFragment)
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

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.setOnPoiClickListener(this)
         fusedLocationProviderClient.lastLocation.addOnSuccessListener(this)
        addPoiBySearching()

    }

    override fun onSuccess(location: Location?) {
        val latLng = if (location!=null) LatLng(location.latitude,location.longitude)
                     else LatLng(0.0,0.0)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15.0f))

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
        if(directions!=null){
        findNavController().navigate(directions!!)}
    }



}