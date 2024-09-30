package com.example.ciudades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.ciudades.data.AppDatabase
import com.example.ciudades.data.Capital
import com.example.ciudades.data.CapitalDao
import com.example.ciudades.ui.theme.CiudadesTheme
import kotlinx.coroutines.launch

class CargarCap : ComponentActivity() {
    private lateinit var capitalDao: CapitalDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getDatabase(this)
        capitalDao = db.capitalDao()

        setContent {
            CiudadesTheme {
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { innerPadding ->
                    CityForm(
                        modifier = Modifier.padding(innerPadding),
                        onSave = { country, name, population ->
                            lifecycleScope.launch {
                                if (country.isNotBlank() && name.isNotBlank() && population > 0) {
                                    capitalDao.insertCity(
                                        Capital(country = country, capitalName = name, population = population)
                                    )
                                    snackbarHostState.showSnackbar("Capital guardada con éxito")
                                } else {
                                    snackbarHostState.showSnackbar("Error: Verifica los campos")
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CityForm(
    modifier: Modifier = Modifier,
    onSave: (String, String, Int) -> Unit
) {
    var country by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var population by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            value = country,
            onValueChange = { country = it },
            label = { Text("Nombre del País") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre de la Capital") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = population,
            onValueChange = { population = it },
            label = { Text("Población") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val populationValue = population.toIntOrNull() ?: 0
                onSave(country, name, populationValue)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Capital")
        }
    }
}
