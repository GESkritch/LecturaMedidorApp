package com.mauricio.lecturamedidorapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "mediciones")
data class Medicion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipo: String,
    val valor: Double,
    val fecha: Date
)