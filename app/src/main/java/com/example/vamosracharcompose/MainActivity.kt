package com.example.vamosracharcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vamosracharcompose.ui.theme.VamosRacharComposeTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VamosRacharComposeTheme {
                Interface()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Interface() {
    val edtConta = remember { mutableStateOf("") }
    val edtNumeroPessoas = remember { mutableStateOf("") }
    val resultado = remember { mutableStateOf("") }
    val valorConta = remember { mutableStateOf(0.0) }
    val nPessoas = remember { mutableStateOf(0) }
    val valorPorPessoa = remember { mutableStateOf(0.0) }
    Column {
        Text(text = "Vamos Rachar!")
        OutlinedTextField(
            value = edtNumeroPessoas.value,
            onValueChange = {
                edtNumeroPessoas.value = it
                valorPorPessoa.value = valorConta.value / nPessoas.value
            },
            modifier = Modifier.padding(top = 16.dp),
            label = { Text("Insira o número de pessoas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = edtConta.value,
            onValueChange = {
                edtConta.value = it
                valorPorPessoa.value = valorConta.value / nPessoas.value
            },
            modifier = Modifier.padding(top = 16.dp),
            label = { Text("Insira o valor da conta") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(
            onClick = {
                valorConta.value = edtConta.value.toDoubleOrNull() ?: 0.0 //declarar em cima com remember
                nPessoas.value = edtNumeroPessoas.value.toIntOrNull() ?: 1 //declarar em cima com remember

                valorPorPessoa.value = valorConta.value / nPessoas.value //declarar em cima com remember
                val resultadoTexto = //declarar em cima com remember
                    String.format(Locale.getDefault(), "Valor por pessoa: R$%.2f", valorPorPessoa)

                resultado.value = resultadoTexto

                val strTTS = //declarar em cima com remember
                    "A conta de R$$valorConta entre $nPessoas pessoas fica $valorPorPessoa para cada!"

                // Falar e compartilhar a frase strTTS
            },
            modifier = Modifier.padding(top = 40.dp)
        ) {
            Text(text = "Falar")
        }
        FloatingActionButton(
            onClick = {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "strTTS") //tirar as aspas
                // Iniciar a atividade de compartilhamento
            },
            modifier = Modifier.padding(top = 40.dp)
        ) {
            Icon(
                Icons.Default.Share,
                contentDescription = "Compartilhar"
            )
        }
        Text(
            text = resultado.value,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 40.dp)
        )
    }
}
