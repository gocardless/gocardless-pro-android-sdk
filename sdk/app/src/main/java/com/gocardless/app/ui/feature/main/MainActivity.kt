package com.gocardless.app.ui.feature.main

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gocardless.app.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            Screen(viewModel = viewModel, action = this::openUrl)
                        },
                        content = {
                            Column(modifier = Modifier.padding(top = it.calculateTopPadding())) {
                                Button(onClick = {
                                    viewModel.createSinglePayment()
                                }) {
                                    Text("Create IBP Payment")
                                }

                                Button(onClick = {
                                    viewModel.createMandate()
                                }) {
                                    Text("Create DD Mandate")
                                }

                                Button(onClick = {
                                    viewModel.createVRPMandate()
                                }) {
                                    Text("Create VRP Mandate")
                                }

                                Button(onClick = {
                                    viewModel.createCustomPagePayment()
                                }) {
                                    Text("Create no UI payment for test user")
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    private fun openUrl(url: String?): Unit {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(this, browserIntent, Bundle())
    }
}

@Composable
fun Screen(viewModel: MainViewModel, action: (String?) -> Unit) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    when (val uiStateValue = uiState.value) {
        is MainUiState.Success -> action(uiStateValue.url)
        is MainUiState.Error -> Message(name = uiStateValue.message)
        MainUiState.Init -> Message(name = "Welcome")
        MainUiState.Loading -> Message(name = "Loading")
    }
}

@Composable
fun Message(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "$name!",
        modifier = modifier.then(Modifier.padding(16.dp))
    )
}
