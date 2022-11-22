package noor.serry.locationreminders.ui.map

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import noor.serry.locationreminders.databinding.FragmentFindLocationBinding

class FindLocation : Fragment() {
     lateinit var binding :FragmentFindLocationBinding
     lateinit var geocoder : Geocoder
     lateinit var directions : FindLocationDirections.ActionFindLocationToMaps
     lateinit var place : Address
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindLocationBinding.inflate(layoutInflater)
        geocoder = Geocoder(requireContext())
        directions = FindLocationDirections.actionFindLocationToMaps()
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       binding.search.addTextChangedListener{text ->  onTextChangedListener(text)}
        binding.result.setOnClickListener(this::onResultClickListener)

    }


      private fun onTextChangedListener(text :  Editable?){
          try{
                place =  geocoder.getFromLocationName(text.toString(),1)[0]
                  showResultInTextView()
          }
          catch (e : Exception){
              hideResultTextView()
          }
      }

    private fun hideResultTextView(){
        binding.result.visibility = View.GONE
    }

    private fun showResultInTextView(){
        binding.result.visibility = View.VISIBLE
        binding.result.text = place.getAddressLine(0)
    }

    private fun onResultClickListener(view : View){
        with(place){
        directions.latitude = latitude.toFloat()
        directions.longitude = longitude.toFloat()
            directions.name =countryName
            findNavController().navigate(directions)
    }}




}

