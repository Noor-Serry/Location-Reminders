package com.udacity.project4.ui.reminders

import com.example.domain.entity.Reminder

data class RemindersState(val emptyBoxVisibility :Boolean , val reminders :List<Reminder>)
