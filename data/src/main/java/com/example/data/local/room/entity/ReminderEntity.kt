package com.example.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entity.Coordinates
import com.example.domain.entity.Reminder


/**
 * Immutable model class for a Reminder. In order to compile with Room
 *
 * @param title         title of the reminder
 * @param description   description of the reminder
 * @param name          location name of the reminder
 * @param latitude      latitude of the reminder location
 * @param longitude     longitude of the reminder location
 * @param id          id of the reminder
 */

@Entity(tableName = "reminders")
data class ReminderEntity(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "name") var locationName: String,
    @Embedded var coordinatesEntity: CoordinatesEntity,
    @PrimaryKey(autoGenerate = false) val id: Long
)

data class CoordinatesEntity(
    @ColumnInfo(name = "latitude") var latitude: Double,
    @ColumnInfo(name = "longitude") var longitude: Double
)

fun CoordinatesEntity.toCoordinates() = Coordinates(latitude, longitude)

fun Coordinates.toCoordinatesEntity() = CoordinatesEntity(latitude, longitude)

fun ReminderEntity.toReminder() = Reminder(title, description, locationName, coordinatesEntity.toCoordinates(), id)

fun Reminder.toReminderEntity() =
    ReminderEntity(title!!, description!!, locationName!!, coordinates!!.toCoordinatesEntity(), id)
