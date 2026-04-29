package com.juancaballero.exploraapp.ui.elements

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    // 1. Instancia de Firebase y Contexto
    val auth = remember { Firebase.auth }
    val context = LocalContext.current

    // 2. Estados de los campos y UI
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val primaryOrange = Color(0xFFE45D25)
    val lightGrayBg = Color(0xFFF8F9FE)
    val inputBg = Color(0xFFE5E5EA)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = lightGrayBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- HEADER (Mantenemos tu diseño) ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp))
            ) {
                Box(modifier = Modifier.fillMaxSize().background(Color.LightGray))
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier.size(60.dp).clip(CircleShape).background(primaryOrange),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Home, "Logo", tint = Color.White, modifier = Modifier.size(30.dp))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Explorando Colombia", color = primaryOrange, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Bienvenido", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text("Explora la magia de Colombia hoy.", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(32.dp))

            // --- FORMULARIO ---
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)) {
                Text("CORREO ELECTRÓNICO", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth().height(56.dp).clip(RoundedCornerShape(28.dp)),
                    placeholder = { Text("nombre@ejemplo.com", color = Color.Gray) },
                    leadingIcon = { Icon(Icons.Default.Email, null, tint = Color.Gray) },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = inputBg,
                        unfocusedContainerColor = inputBg
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text("CONTRASEÑA", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = password, // Usamos la variable password corregida
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth().height(56.dp).clip(RoundedCornerShape(28.dp)),
                    placeholder = { Text("........", color = Color.Gray) },
                    leadingIcon = { Icon(Icons.Default.Lock, null, tint = Color.Gray) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = inputBg,
                        unfocusedContainerColor = inputBg
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(32.dp))

                // --- BOTÓN DE LOGIN CON LÓGICA FIREBASE ---
                Button(
                    onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            isLoading = true
                            auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    isLoading = false
                                    if (task.isSuccessful) {
                                        onLoginSuccess() // Navega a la Home
                                    } else {
                                        Toast.makeText(
                                            context, "Error: ${task.exception?.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        } else {
                            Toast.makeText(context, "Completa los campos", Toast.LENGTH_SHORT).show()
                        }
                    },
                    enabled = !isLoading, // Deshabilitar si está cargando
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().background(
                            brush = Brush.horizontalGradient(colors = listOf(primaryOrange, Color(0xFFFF8A65)))
                        ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Iniciar Sesión", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, null, modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }

                // (Resto de tu código: Divisores, Social Buttons y "Regístrate" se mantienen igual)
                // ...
            }

            // Espaciador para empujar el texto de registro al final
            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier.padding(bottom = 16.dp)) {
                Text(text = "¿No tienes cuenta? ", color = Color.Gray, fontSize = 14.sp)
                Text(
                    text = "Regístrate",
                    color = primaryOrange,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToRegister() }
                )
            }
        }
    }
}