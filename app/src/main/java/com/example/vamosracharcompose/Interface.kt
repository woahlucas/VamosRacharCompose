package com.example.vamosracharcompose

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Interface(
    tts: TextToSpeech,
    compartilhar: (String) -> Unit,
) {
    val valorConta = remember { mutableStateOf("") }
    val nPessoas = remember { mutableStateOf("") }
    val resultado = remember { mutableStateOf("Valor por pessoa: R$0,00") }
    val strTTS = remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
        ) {
            Image(painter = painterResource(id = R.mipmap.ic_launcher_foreground), contentDescription = "aperto de mão")
            Text(text = "Vamos Rachar!")
            OutlinedTextField(
                value = nPessoas.value,
                onValueChange = {
                    nPessoas.value = it
                    if (valorConta.value.isNotEmpty() && nPessoas.value.isNotEmpty()) {
                        val valorPorPessoa = valorConta.value.toDouble() / nPessoas.value.toInt()
                        resultado.value = String.format(Locale.getDefault(), "Valor por pessoa: R$%.2f", valorPorPessoa)
                    } else {
                        resultado.value = ""
                    }
                },
                modifier = Modifier.padding(top = 16.dp),
                label = { Text("Insira o número de pessoas") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = valorConta.value,
                onValueChange = {
                    valorConta.value = it
                    if (valorConta.value.isNotEmpty() && nPessoas.value.isNotEmpty()) {
                        val valorPorPessoa = valorConta.value.toDouble() / nPessoas.value.toInt()
                        resultado.value = String.format(Locale.getDefault(), "Valor por pessoa: R$%.2f", valorPorPessoa)
                    } else {
                        resultado.value = ""
                    }
                },
                modifier = Modifier.padding(top = 16.dp),
                label = { Text("Insira o valor da conta") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Button(
                onClick = {
                    if (valorConta.value != "" && nPessoas.value != "") {
                        strTTS.value = "A conta de R$${valorConta.value} entre ${nPessoas.value} pessoas fica ${valorConta.value.toDouble() / nPessoas.value.toInt()} para cada!"
                        tts.speak(strTTS.value, TextToSpeech.QUEUE_FLUSH, null, "ID1")
                    }
                },
                modifier = Modifier.padding(top = 40.dp)
            ) {
                Text(text = "Falar")
            }
            Text(
                text = resultado.value,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 40.dp)
            )
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.fillMaxWidth(),
            ) {
                FloatingActionButton(
                    onClick = {
                        // Iniciar a atividade de compartilhamento
                        if (valorConta.value != "" && nPessoas.value != "") {
                            strTTS.value = "A conta de R$${valorConta.value} entre ${nPessoas.value} pessoas fica ${valorConta.value.toDouble() / nPessoas.value.toInt()} para cada!"
                            compartilhar.invoke(strTTS.value)
                        }
                    },
                    modifier = Modifier.padding(top = 40.dp)
                ) {
                    Icon(
                        Icons.Default.Share,
                        contentDescription = "Compartilhar"
                    )
                }
            }
        }
    }
}