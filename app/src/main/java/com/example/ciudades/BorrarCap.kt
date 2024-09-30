package com.example.ciudades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.ciudades.data.AppDatabase
import com.example.ciudades.data.CapitalDao
import com.example.ciudades.ui.theme.CiudadesTheme
import kotlinx.coroutines.launch

class BorrarCap : ComponentActivity() {
    private lateinit var capitalDao: CapitalDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getDatabase(this)
        capitalDao = db.capitalDao()

        setContent {
            CiudadesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DeleteCityForm(
                        modifier = Modifier.padding(innerPadding),
                        onDeleteByName = { name ->
                            lifecycleScope.launch {
                                val capital = capitalDao.getCityByName(name)
                                capital?.let { capitalDao.deleteCity(it) }
                            }
                        },
                        onDeleteByCountry = { country ->
                            lifecycleScope.launch {
                                capitalDao.deleteCitiesByCountry(country)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DeleteCityForm(
    modifier: Modifier = Modifier,
    onDeleteByName: (String) -> Unit,
    onDeleteByCountry: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre de la Capital a Borrar") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onDeleteByName(name) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Borrar Capital por Nombre")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = country,
            onValueChange = { country = it },
            label = { Text("País para Borrar Capitales") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onDeleteByCountry(country) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Borrar Todas las Capitales por País")
        }
    }
}
