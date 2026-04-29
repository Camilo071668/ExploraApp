package com.juancaballero.exploraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.juancaballero.exploraapp.ui.theme.ExploraAppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juancaballero.exploraapp.ui.elements.HomeScreen
import com.juancaballero.exploraapp.ui.elements.LoginScreen
import com.juancaballero.exploraapp.ui.elements.RegisterScreen
import com.juancaballero.exploraapp.ui.elements.addTouristicPlaceScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializamos Firebase Auth
        val auth = Firebase.auth

        setContent {
            ExploraAppTheme {
                val myNavController = rememberNavController()

                // 1. LÓGICA DE PERSISTENCIA: 
                // Si auth.currentUser no es nulo, el usuario ya inició sesión antes.
                val startDest = if (auth.currentUser != null) "home" else "login"

                NavHost(
                    navController = myNavController,
                    startDestination = startDest, // Dinámico según la sesión
                    modifier = Modifier.fillMaxSize(),
                ) {
                    // Pantalla de Login
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = {
                                // Al loguearse con éxito, vamos a Home y limpiamos el historial
                                myNavController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            onNavigateToRegister = {
                                myNavController.navigate("register")
                            }
                        )
                    }

                    // Pantalla de Registro
                    composable("register") {
                        RegisterScreen(
                            onRegisterSuccess = {
                                // Si se registra, usualmente lo mandamos a la Home directamente
                                myNavController.navigate("home") {
                                    popUpTo("register") { inclusive = true }
                                }
                            },
                            onNavigateToLogin = {
                                myNavController.navigate("login")
                            },
                            onBackClick = {
                                myNavController.popBackStack()
                            }
                        )
                    }

                    composable("home") {
                        HomeScreen(
                            onClickTouristic = {myNavController.navigate("touristicPlaces")},
                            onLogout = {
                                myNavController.navigate("login") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("touristicPlaces") {
                        addTouristicPlaceScreen()
                    }

                }
            }
        }
    }
}
