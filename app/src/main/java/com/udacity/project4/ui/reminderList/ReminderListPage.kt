package com.udacity.project4.ui.reminderList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentReminderListPageBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ReminderListPage : Fragment() {
    lateinit var binding : FragmentReminderListPageBinding
    val viewMode : ReminderListViewModel by sharedViewModel()
    val adapter : RecyclerAdapter by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentReminderListPageBinding.inflate(layoutInflater)
        binding.viewModel = viewMode
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewMode.loadReminders()
        binding.newReminderButton.setOnClickListener(this::goToSelectLocationPage)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewMode.showSnackBar.observe(viewLifecycleOwner){
            Snackbar.make(requireView(),it, Snackbar.LENGTH_SHORT).show()
        }
        viewMode.reminders.observe(viewLifecycleOwner){
            adapter.setDataItem(it)
            binding.recyclerView.adapter = adapter
            binding.emptyReminderImage.visibility = View.GONE
        }
        binding.logOutButton.setOnClickListener(this::logOut)
    }


   private fun goToSelectLocationPage(view: View){
            findNavController().navigate(R.id.action_reminderListPage_to_maps)
    }


    private fun logOut(view : View){
        AuthUI.getInstance().signOut(requireContext())
        requireActivity().finish()
    }
}