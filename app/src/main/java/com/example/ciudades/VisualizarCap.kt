package com.example.ciudades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.ciudades.data.AppDatabase
import com.example.ciudades.data.Capital
import com.example.ciudades.data.CapitalDao
import com.example.ciudades.ui.theme.CiudadesTheme
import kotlinx.coroutines.launch
import androidx.navigation.NavController

class VisualizarCap : ComponentActivity() {
    private lateinit var capitalDao: CapitalDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getDatabase(this)
        capitalDao = db.capitalDao()

        setContent {
            CiudadesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var capitalState by remember { mutableStateOf<Capital?>(null) }

                    // Pasamos la lambda onSearch que actualiza el estado capitalState
                    SearchCapitalForm(
                        modifier = Modifier.padding(innerPadding),
                        onSearch = { name ->
                            lifecycleScope.launch {
                                val capital = capitalDao.getCapitalByName(name)
                                capitalState = capital
                            }
                        }
                    )

                    // Mostrar detalles de la capital si el estado no es nulo
                    capitalState?.let { capital ->
                        CapitalDetails(capital)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchCapitalForm(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre de la Capital") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onSearch(name) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buscar Capital")
        }
    }
}

@Composable
fun CapitalDetails(capital: Capital) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "País: ${capital.country}")
        Text(text = "Capital: ${capital.name}")
        Text(text = "Población: ${capital.population}")
    }
}

@Preview(showBackground = true)
@Composable
fun SearchCapitalFormPreview() {
    CiudadesTheme {
        SearchCapitalForm(onSearch = { })
    }
}
