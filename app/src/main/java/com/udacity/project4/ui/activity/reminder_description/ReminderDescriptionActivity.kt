package com.udacity.project4.ui.activity.reminder_description

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
import androidx.databinding.DataBindingUtil
import com.example.domain.entity.Reminder
import com.udacity.project4.R
import com.udacity.project4.databinding.ActivityReminderDescriptionBinding
import com.udacity.project4.utils.Constance.REMINDER_KEY
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReminderDescriptionActivity : AppCompatActivity() {


    private lateinit var binding: ActivityReminderDescriptionBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reminder_description)
        val reminder = getReminderSent()

            binding.composeView.apply {
                setViewCompositionStrategy(DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    ReminderDescriptionScreen(reminder)
                }
            }
    }

    private fun getReminderSent() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getSerializableExtra(REMINDER_KEY, Reminder::class.java)
        else intent.getSerializableExtra(REMINDER_KEY) as Reminder !!


}
