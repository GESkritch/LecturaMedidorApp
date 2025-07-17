package com.mauricio.lecturamedidorapp.ui

import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.mauricio.lecturamedidorapp.model.Medicion
import com.mauricio.lecturamedidorapp.viewmodel.MedicionViewModel
import com.mauricio.lecturamedidorapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioLectura(viewModel: MedicionViewModel, onGuardar: () -> Unit) {
    val opciones = listOf(
        stringResource(R.string.tipo_agua),
        stringResource(R.string.tipo_luz),
        stringResource(R.string.tipo_gas)
    )

    var tipo by remember { mutableStateOf(opciones[0]) }
    var expanded by remember { mutableStateOf(false) }

    var valorTexto by remember { mutableStateOf("") }
    val valorValido = valorTexto.toDoubleOrNull()?.let { it > 0 } ?: false

    val snackbarHostState = remember { SnackbarHostState() }
    var mostrarSnackbar by remember { mutableStateOf(false) }

    val mensajeLecturaGuardada = stringResource(R.string.lectura_guardada)

    Column(modifier = Modifier.padding(16.dp)) {
        Text(stringResource(R.string.nueva_lectura), style = MaterialTheme.typography.titleLarge)

        SnackbarHost(hostState = snackbarHostState)

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = tipo,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.tipo_gasto)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                opciones.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            tipo = opcion
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = valorTexto,
            onValueChange = { valorTexto = it },
            label = { Text(stringResource(R.string.valor)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        if (!valorValido && valorTexto.isNotBlank()) {
            Text(
                text = stringResource(R.string.valor_invalido),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val medicion = Medicion(
                    tipo = tipo,
                    valor = valorTexto.toDouble(),
                    fecha = Date()
                )
                viewModel.guardarMedicion(medicion)
                mostrarSnackbar = true
                onGuardar()
            },
            enabled = valorValido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.guardar_lectura))
        }

        if (mostrarSnackbar) {
            LaunchedEffect(snackbarHostState) {
                snackbarHostState.showSnackbar(mensajeLecturaGuardada)
                mostrarSnackbar = false
            }
        }
    }
}