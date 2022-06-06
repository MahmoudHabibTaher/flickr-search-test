package com.bigo.flickrsearch.android

import android.os.Bundle
import com.bigo.flickrsearch.Greeting
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bigo.flickrsearch.android.ui.theme.FlickrSearchTheme

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greetings(greeting = greet())
        }
    }
}

@Composable
private fun Greetings(greeting: String){
    FlickrSearchTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Text(text = greeting)
        }
    }
}