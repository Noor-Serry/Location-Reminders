package com.udacity.project4.ui.reminders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentRemindersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RemindersFragment : Fragment() {

    lateinit var binding: FragmentRemindersBinding
    val viewMode: RemindersViewModel by viewModels()
    private val adapter: ReminderAdapter = ReminderAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRemindersBinding.inflate(layoutInflater)
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addReminderButton.setOnClickListener { goToSelectLocationPage() }
        binding.logOutButton.setOnClickListener { logOut() }
      view()

    }

    private fun view(){
        lifecycleScope.launch {
            viewMode.remindersState.collect {
             adapter.setNewDataSet(it.reminders)
                binding.emptyReminderImage.visibility = if(it.emptyBoxVisibility) View.VISIBLE else View.GONE
            }}

    }


    private fun goToSelectLocationPage() {
            findNavController().navigate(R.id.action_remindersFragment_to_mapFragment)
    }


    private fun logOut() {
        viewMode.setUserLoginState(false)
        AuthUI.getInstance().signOut(requireContext())
        requireActivity().finish()
    }
}