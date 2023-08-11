package com.example.domain.entity

import java.io.Serializable


data class Reminder(
     var title: String? = null,
     var description: String? = null,
     var locationName: String? = null,
     var coordinates:Coordinates? = null,
     val id: Long
):Serializable


