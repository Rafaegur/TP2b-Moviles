package com.example.ciudades.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_table")
data class Capital(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val country: String,
    val capitalName: String,
    val population: Int
)
