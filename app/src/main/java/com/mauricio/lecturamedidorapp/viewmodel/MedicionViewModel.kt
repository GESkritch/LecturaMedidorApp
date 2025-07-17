package com.mauricio.lecturamedidorapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mauricio.lecturamedidorapp.data.MedicionDao
import com.mauricio.lecturamedidorapp.model.Medicion
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MedicionViewModel(private val dao: MedicionDao) : ViewModel() {

    val listaMediciones: StateFlow<List<Medicion>> = dao.obtenerTodas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun guardarMedicion(medicion: Medicion) {
        viewModelScope.launch {
            dao.insertar(medicion)
        }
    }
    fun borrarTodo() {
        viewModelScope.launch {
            dao.borrarTodo()
        }
    }
}