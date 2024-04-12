package com.softyorch.firestoreadvanced2compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.softyorch.firestoreadvanced2compose.ui.home.HomeScreen
import com.softyorch.firestoreadvanced2compose.ui.home.HomeViewModel
import com.softyorch.firestoreadvanced2compose.ui.theme.FirestoreAdvanced2ComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirestoreAdvanced2ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: HomeViewModel by viewModels()
                    HomeScreen(viewModel)
                }
            }
        }
    }
}
