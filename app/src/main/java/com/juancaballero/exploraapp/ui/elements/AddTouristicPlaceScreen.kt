package com.juancaballero.exploraapp.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTouristicPlaceScreen() {
    // Definición de colores y gradiente según la imagen
    val orangeGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFD34724), Color(0xFFEC7047))
    )
    val backgroundColor = Color(0xFFF9F6F6)
    val inputBackground = Color(0xFFE5E2E6)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // --- ENCABEZADO (TARJETA NARANJA) ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            shape = RoundedCornerShape(40.dp),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(orangeGradient),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "Comparte tu\ndescubrimiento",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Ayuda a otros viajeros a encontrar los tesoros escondidos de nuestra tierra.",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // --- FORMULARIO ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            CustomInputField("NOMBRE DEL LUGAR", "Ej: Cascada del Fin del Mundo", inputBackground)
            CustomInputField("DEPARTAMENTO", "Ej: Putumayo", inputBackground)
            CustomInputField("CIUDAD", "Ej: Mocoa", inputBackground)
            CustomInputField(
                label = "DESCRIPCIÓN",
                placeholder = "Cuéntanos por qué este lugar es especial...",
                containerColor = inputBackground,
                isDescription = true
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // --- BOTÓN PUBLICAR ---
        Button(
            onClick = { /* Acción de publicar */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(orangeGradient),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Publicar",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun CustomInputField(
    label: String,
    placeholder: String,
    containerColor: Color,
    isDescription: Boolean = false
) {
    var textState by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF635C5C),
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )
        TextField(
            value = textState,
            onValueChange = { textState = it },
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Gray.copy(alpha = 0.6f),
                    fontSize = 15.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isDescription) 150.dp else 56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
    }
}