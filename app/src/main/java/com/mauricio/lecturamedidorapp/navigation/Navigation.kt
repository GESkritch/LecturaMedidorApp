package com.mauricio.lecturamedidorapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mauricio.lecturamedidorapp.ui.FormularioLectura
import com.mauricio.lecturamedidorapp.ui.VistaDePrueba
import com.mauricio.lecturamedidorapp.viewmodel.MedicionViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: MedicionViewModel
) {
    NavHost(navController, startDestination = "listado") {
        composable("listado") {
            VistaDePrueba(viewModel = viewModel) {
                navController.navigate("formulario")
            }
        }
        composable("formulario") {
            FormularioLectura(viewModel = viewModel) {
                navController.popBackStack()
            }
        }
    }
}