package com.example.momogallery

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.momogallery.ui.MediaViewModel
import com.example.momogallery.ui.screens.DetailScreen
import com.example.momogallery.ui.screens.GalleryScreen
import com.example.momogallery.ui.theme.MomoGalleryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MomoGalleryTheme {
                MomoGalleryApp()
            }
        }
    }
}

@Composable
fun MomoGalleryApp() {
    val navController = rememberNavController()
    val viewModel: MediaViewModel = viewModel()

    NavHost(navController = navController, startDestination = "gallery") {
        composable("gallery") {
            GalleryScreen(
                viewModel = viewModel,
                onMediaClick = { media ->
                    val encodedUri = Uri.encode(media.uri.toString())
                    navController.navigate("detail/$encodedUri")
                }
            )
        }
        composable(
            route = "detail/{uri}",
            arguments = listOf(navArgument("uri") { type = NavType.StringType })
        ) { backStackEntry ->
            val uriString = backStackEntry.arguments?.getString("uri")
            val uri = uriString?.let { Uri.parse(Uri.decode(it)) }
            
            if (uri != null) {
                DetailScreen(uri = uri, onBack = { navController.popBackStack() })
            }
        }
    }
}