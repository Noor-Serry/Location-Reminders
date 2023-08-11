package com.udacity.project4.ui.activity.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.udacity.project4.R
import com.udacity.project4.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        suspendUntilLoadUserState()
        setContentView(binding.root)
    }

    private fun suspendUntilLoadUserState() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (viewModel.isUserLoginState.value!=null) {
                        setStartDestination()
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else
                        false

                }
            })
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setStartDestination(){
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment?
        val navController = navHost!!.navController
        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.nav_graph)
        if(viewModel.isUserLoginState.value!!)
            graph.setStartDestination(R.id.remindersFragment)
        else graph.setStartDestination(R.id.loginPage)
        navController.graph = graph
    }


}
