package com.udacity.project4.ui.activity.reminder_description

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.entity.Reminder
import com.udacity.project4.R

@Composable
fun ReminderDescriptionScreen(reminder: Reminder) {

    ReminderDescriptionContent(reminder)
}


@Composable
fun ReminderDescriptionContent(reminder: Reminder) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text ="Reminder Description" ,)}, backgroundColor = colorResource(id = R.color.white))

        }){

        Column(
            Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            CustomTextField(reminder.title!!, "title")
            CustomTextField(reminder.description!!, "description")
            CustomTextField(value = reminder.locationName!!, label = "location name")
        }
    }
}

@Composable
fun CustomTextField(value: String, label: String) {

    OutlinedTextField(

        value = value,
        onValueChange = {},
        label = { Text(text = label) },
        readOnly = true,
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
        shape = RoundedCornerShape(topStartPercent = 25, bottomEndPercent = 25),
        colors = TextFieldDefaults.textFieldColors(
            focusedLabelColor = colorResource(id = R.color.green1),
            unfocusedLabelColor = colorResource(id = R.color.green1),
            focusedIndicatorColor = colorResource(id = R.color.green1),
            unfocusedIndicatorColor = colorResource(id = R.color.green1)
        )
    )
Spacer(modifier = Modifier.height(16.dp))
}