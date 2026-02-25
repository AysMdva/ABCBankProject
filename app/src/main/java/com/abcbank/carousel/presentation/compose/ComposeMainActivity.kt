package com.abcbank.carousel.presentation.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.abcbank.carousel.presentation.MainViewModel
import com.abcbank.carousel.presentation.compose.screen.CarouselScreenRoute

class ComposeMainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colorScheme.onPrimaryContainer) {
                CarouselScreenRoute(viewModel = viewModel)
            }
        }
    }
}
