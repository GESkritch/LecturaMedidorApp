package com.mauricio.lecturamedidorapp.data

import androidx.room.*
import com.mauricio.lecturamedidorapp.model.Medicion
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(medicion: Medicion)

    @Query("SELECT * FROM mediciones ORDER BY fecha DESC")
    fun obtenerTodas(): Flow<List<Medicion>>

    @Query("DELETE FROM mediciones")
    suspend fun borrarTodo()
}