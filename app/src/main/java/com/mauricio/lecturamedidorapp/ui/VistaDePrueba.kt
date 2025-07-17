package com.mauricio.lecturamedidorapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.mauricio.lecturamedidorapp.viewmodel.MedicionViewModel
import com.mauricio.lecturamedidorapp.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun VistaDePrueba(viewModel: MedicionViewModel, onAgregarLectura: () -> Unit) {
    val listaMediciones by viewModel.listaMediciones.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var mostrarDialogo by remember { mutableStateOf(false) }

    val etiquetaTipo = stringResource(R.string.etiqueta_tipo)
    val etiquetaValor = stringResource(R.string.etiqueta_valor)
    val etiquetaFecha = stringResource(R.string.etiqueta_fecha)
    val titulo = stringResource(R.string.titulo_listado)
    val mensajeSinLecturas = stringResource(R.string.no_hay_lecturas)
    val textoBotonBorrar = stringResource(R.string.borrar_todo)
    val textoConfirmacion = stringResource(R.string.confirmar_borrado)
    val textoAdvertencia = stringResource(R.string.texto_confirmacion)
    val textoBorrar = stringResource(R.string.boton_borrar)
    val textoCancelar = stringResource(R.string.boton_cancelar)

    val tipoAgua = stringResource(R.string.tipo_agua)
    val tipoLuz = stringResource(R.string.tipo_luz)
    val tipoGas = stringResource(R.string.tipo_gas)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregarLectura) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text(titulo, style = MaterialTheme.typography.titleLarge)

            SnackbarHost(hostState = snackbarHostState)

            Spacer(modifier = Modifier.height(8.dp))

            if (listaMediciones.isEmpty()) {
                Text(mensajeSinLecturas)
            } else {
                LazyColumn {
                    items(listaMediciones) { medicion ->
                        val locale = Locale.getDefault()
                        val formatoFecha = SimpleDateFormat("dd MMM yyyy", locale)
                        val fechaFormateada = formatoFecha.format(medicion.fecha)

                        val icono = when (medicion.tipo) {
                            tipoAgua -> "💧"
                            tipoLuz -> "💡"
                            tipoGas -> "🔥"
                            else -> "?"
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("$etiquetaTipo $icono ${medicion.tipo}")
                                Text("$etiquetaValor ${medicion.valor}")
                                Text("$etiquetaFecha $fechaFormateada")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                /*
                Button(onClick = { mostrarDialogo = true }) {
                    Text(textoBotonBorrar)
                }
                */
                if (mostrarDialogo) {
                    AlertDialog(
                        onDismissRequest = { mostrarDialogo = false },
                        title = { Text(textoConfirmacion) },
                        text = { Text(textoAdvertencia) },
                        confirmButton = {
                            TextButton(onClick = {
                                viewModel.borrarTodo()
                                mostrarDialogo = false
                            }) {
                                Text(textoBorrar)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { mostrarDialogo = false }) {
                                Text(textoCancelar)
                            }
                        }
                    )
                }
            }
        }
    }
}