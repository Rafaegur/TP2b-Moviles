package com.example.ciudades

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ciudades.ui.theme.CiudadesTheme
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import com.example.ciudades.data.AppDatabase
import com.example.ciudades.data.Capital

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CiudadesTheme {
                HomePage()
            }
        }
    }

    @Composable
    fun HomePage() {
        var capitals by remember { mutableStateOf<List<Capital>>(emptyList()) }
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        // Cargar la lista de capitales desde la base de datos
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                val db = AppDatabase.getDatabase(context)
                db.capitalDao().getAllCities().let { capitalsList ->
                    capitals = capitalsList
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "TP2",
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 32.dp)
            )

            Button(
                onClick = {
                    val intent = Intent(context, CargarCap::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cargar nueva capital")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val intent = Intent(context, VisualizarCap::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Visualizar capital")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val intent = Intent(context, BorrarCap::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Borrar capital")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Lista de Capitales", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar la lista de capitales
            LazyColumn {
                items(capitals) { capital ->
                    CapitalItem(capital)
                }
            }
        }
    }

    @Composable
    fun CapitalItem(capital: Capital) {
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
            Text("Nombre: ${capital.capitalName}")
            Text("País: ${capital.country}")
            Text("Población: ${capital.population}")
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
