package com.mauricio.lecturamedidorapp.data

import android.content.Context
import androidx.room.*
import com.mauricio.lecturamedidorapp.model.Medicion

@Database(entities = [Medicion::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MedicionDatabase : RoomDatabase() {
    abstract fun medicionDao(): MedicionDao

    companion object {
        @Volatile
        private var INSTANCE: MedicionDatabase? = null

        fun getDatabase(context: Context): MedicionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MedicionDatabase::class.java,
                    "medicion_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}