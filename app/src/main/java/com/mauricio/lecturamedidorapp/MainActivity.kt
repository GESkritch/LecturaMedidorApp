package com.mauricio.lecturamedidorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.mauricio.lecturamedidorapp.data.MedicionDatabase
import com.mauricio.lecturamedidorapp.navigation.AppNavigation
import com.mauricio.lecturamedidorapp.ui.theme.LecturaMedidorAppTheme
import com.mauricio.lecturamedidorapp.viewmodel.MedicionViewModel
import com.mauricio.lecturamedidorapp.viewmodel.MedicionViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            MedicionDatabase::class.java,
            "medicion_db"
        ).build()

        val dao = database.medicionDao()
        val factory = MedicionViewModelFactory(dao)

        setContent {
            val viewModel: MedicionViewModel = viewModel(factory = factory)
            val navController = rememberNavController()

            LecturaMedidorAppTheme {
                AppNavigation(navController = navController, viewModel = viewModel)
            }
        }
    }
}