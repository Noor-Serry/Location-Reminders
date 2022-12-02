package com.udacity.project4.ui.login

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle


import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.udacity.project4.R
import com.udacity.project4.databinding.PageLoginBinding
import kotlin.properties.Delegates


class LoginPage : Fragment() {
   private lateinit var binding : PageLoginBinding
   private val providers = arrayListOf (AuthUI.IdpConfig.GoogleBuilder().build(),
       AuthUI.IdpConfig.EmailBuilder().build())
    lateinit var sharedPreferences :SharedPreferences
    lateinit var  editor :Editor
    lateinit var  authUIIntent :Intent
    lateinit var resultLauncher : ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PageLoginBinding.inflate(inflater)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        editor = sharedPreferences.edit()

        resultLauncher =  registerForActivityResult( ActivityResultContracts.StartActivityForResult(),this::onActivityResult)

        return binding.root

    }


    @SuppressLint("RestrictedApi", "SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(sharedPreferences.getString("currentUser","null")!= AuthUI.getInstance().auth.currentUser?.uid)
        startLoginUi()
        AuthUI.getInstance().auth.addAuthStateListener{
              if( sharedPreferences.getString("currentUser","null")== it.currentUser?.uid)
                  goToReminderListFragment()
               else{
                   editor.putString("currentUser", it.currentUser?.uid)
                   editor.apply()}
      }

    }

   private fun startLoginUi(){
         authUIIntent = AuthUI.getInstance().
        createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .build()
       resultLauncher.launch(authUIIntent)
    }

     private fun onActivityResult(result: ActivityResult?) {
        if(result?.resultCode ==RESULT_OK )
            goToReminderListFragment()
         else activity?.finish()
    }

    private fun goToReminderListFragment(){
        findNavController().navigate(R.id.action_loginPage_to_reminderListPage)
    }



}