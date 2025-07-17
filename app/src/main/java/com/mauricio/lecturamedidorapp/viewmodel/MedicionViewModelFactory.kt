package com.mauricio.lecturamedidorapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mauricio.lecturamedidorapp.data.MedicionDao

class MedicionViewModelFactory(private val dao: MedicionDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MedicionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MedicionViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}