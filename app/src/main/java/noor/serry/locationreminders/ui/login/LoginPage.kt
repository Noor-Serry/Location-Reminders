package noor.serry.locationreminders.ui.login

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import noor.serry.locationreminders.R
import noor.serry.locationreminders.databinding.PageLoginBinding

class LoginPage : Fragment() {
   private lateinit var binding : PageLoginBinding
   private val providers = arrayListOf (AuthUI.IdpConfig.GoogleBuilder().build(),
       AuthUI.IdpConfig.EmailBuilder().build())
    lateinit var resultLauncher : ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PageLoginBinding.inflate(inflater)
         resultLauncher =  registerForActivityResult( ActivityResultContracts.StartActivityForResult(),this::onActivityResult)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startLoginUi()
    }

   private fun startLoginUi(){
        val authUIIntent = AuthUI.getInstance().
        createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .build()
        resultLauncher.launch(authUIIntent)
    }

     private fun onActivityResult(result: ActivityResult?) {
        if(result?.resultCode ==RESULT_OK )
        findNavController().navigate(R.id.action_loginPage_to_reminderListPage)
         else activity?.finish()
    }

}