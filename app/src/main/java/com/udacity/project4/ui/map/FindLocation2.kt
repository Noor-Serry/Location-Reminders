package com.udacity.project4.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentFindLocation2Binding

import java.util.*


// i cant use autocomplete Because it needs a credit card but this class work
class FindLocation2 : Fragment() , PlaceSelectionListener {
    var  autocompleteFragment : AutocompleteSupportFragment? = null
    lateinit var binding : FragmentFindLocation2Binding
    private val YOUR_API_KEY = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        initializedPlace()
        binding = FragmentFindLocation2Binding.inflate(inflater)
        autocompleteFragment = childFragmentManager.findFragmentById(R.id.autoCompleteFragment) as AutocompleteSupportFragment

        return binding.root
    }

    private fun initializedPlace(){
        if(!Places.isInitialized())
            Places.initialize(requireContext(),YOUR_API_KEY , Locale.US)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Specify the types of place data to return.
        autocompleteFragment!!.setPlaceFields(listOf(Place.Field.LAT_LNG))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment!!.setOnPlaceSelectedListener(this)
    }

    override fun onError(p0: Status) {
        Toast.makeText(requireContext(),p0.statusMessage,Toast.LENGTH_SHORT).show()
    }

    override fun onPlaceSelected(p0: Place) {
      //there send place selected  to map fragment
    }

}