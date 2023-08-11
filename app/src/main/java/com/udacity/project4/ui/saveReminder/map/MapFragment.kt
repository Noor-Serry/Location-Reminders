package com.udacity.project4.ui.saveReminder.map

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.domain.entity.Address
import com.example.domain.entity.Coordinates
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentMapBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    val viewModel: MapViewModel by viewModels()
    private lateinit var binding: FragmentMapBinding
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var addressAdapter: AddressAdapter
    private lateinit var googleMap: GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater)
        setUpAdapter()
        mapFragment = (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
        return binding.root

    }

    @SuppressLint("MissingPermission")
    private fun setUpAdapter() {
        addressAdapter = AddressAdapter(::onRecyclerViewClickListener)
        binding.recyclerView.adapter = addressAdapter

    }

    private fun onRecyclerViewClickListener(address: Address) {
        val latLng = LatLng(address.coordinates.latitude, address.coordinates.longitude)
        onMapClickListener(latLng)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment.getMapAsync(this)
        binding.locationSearchView.addTextChangedListener { viewModel.onSearch(it.toString()) }

        lifecycleScope.launch {
            viewModel.addressesState.collect {
                addressAdapter.setNewDataSet(it)
            }
        }
        lifecycleScope.launch {
            viewModel.addressState.collect {
                it?.let {
                    updateLocationTextView(it.addressDetails)
                }

            }
        }

        lifecycleScope.launch {
            viewModel.errorMessage.collect {
                it?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }

             binding.setLocationButton.setOnClickListener{
                 if(viewModel.currentCoordinates.value != null){
                 val bundle = Bundle()
                 bundle.putSerializable("coordinates",viewModel.currentCoordinates.value)
                   findNavController().navigate(R.id.action_mapFragment_to_saveReminderFragment,bundle)
             }}
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        val latLng = LatLng(31.3534079, 27.2371931)
        setMapStyle()
        addMarker(latLng)
        moveCamera(latLng)
        googleMap.setOnMapClickListener (::onMapClickListener)
        }


    private fun onMapClickListener(latLng: LatLng){
        googleMap.clear()
        addMarker(latLng)
        moveCamera(latLng)
        viewModel.onMapClick(Coordinates(latLng.latitude, latLng.longitude))
    }

    private fun setMapStyle() {
        googleMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style)
        )
    }

    private fun addMarker(latLng: LatLng) {
        googleMap.addMarker(
            MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.my_mark_map))
        )
        addGeofenceBound(latLng)
    }

    private fun addGeofenceBound(latLng: LatLng) {
        googleMap.addCircle(
            CircleOptions().center(latLng).radius(4.0).fillColor(Color.argb(100, 21, 170, 119))
                .strokeWidth(0F)
        )
        googleMap.addCircle(
            CircleOptions().center(latLng).radius(8.0).fillColor(Color.argb(50, 21, 170, 119))
                .strokeWidth(0F)
        )
    }

    private fun moveCamera(latLng: LatLng) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19.0f))
    }

    private fun updateLocationTextView(addressDetails: String) {
        binding.locationName.text = addressDetails
    }


}
