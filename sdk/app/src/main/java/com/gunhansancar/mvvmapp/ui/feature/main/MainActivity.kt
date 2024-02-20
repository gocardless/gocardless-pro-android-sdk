package com.gunhansancar.mvvmapp.ui.feature.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gocardless.gocardlesssdk.model.Customer
import com.gunhansancar.mvvmapp.ui.theme.MVVMAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            MVVMAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            Box(modifier = Modifier.padding(16.dp)) {
                                Button(onClick = {
                                    viewModel.fetchCustomers()
                                }) {
                                    Text("Refresh")
                                }
                            }
                        },
                        floatingActionButton = {
                            AddCustomerFAB(onClick = { first, second ->
                                viewModel.addCustomer(first, second)
                            })
                        }, content = {
                            Box(modifier = Modifier.padding(top = it.calculateTopPadding())) {
                                Screen(viewModel = viewModel)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Screen(viewModel: MainViewModel) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    when (val uiStateValue = uiState.value) {
        is MainUiState.Success -> MainList(list = uiStateValue.customers.customers, viewModel)
        is MainUiState.Error -> Message(name = uiStateValue.message)
        MainUiState.Init -> Message(name = "Welcome")
        MainUiState.Loading -> Message(name = "Loading")
    }
}

@Composable
fun MainList(list: List<Customer>, viewModel: MainViewModel, modifier: Modifier = Modifier) {
    LazyColumn(
        Modifier.padding(16.dp)
    ) {
        itemsIndexed(list) { index, item ->
            Row {
                CustomerRow(item)

                Spacer(modifier = Modifier.weight(1f))

                Button(onClick = {
                    viewModel.deleteCustomer(item)
                }) {
                    Text("Delete")
                }
            }

            if (index < list.lastIndex)
                Divider(
                    color = Color.Black.copy(alpha = 0.3f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
        }
    }
}

@Composable
fun CustomerRow(customer: Customer, modifier: Modifier = Modifier) {
    Text(
        text = "${customer.givenName} ${customer.familyName}",
        modifier = modifier
    )
}

@Composable
fun Message(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "$name!",
        modifier = modifier.then(Modifier.padding(16.dp))
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCustomerFAB(onClick: (String, String) -> Unit) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Enter your name") },
            text = {
                Column {
                    TextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("First name") }
                    )
                    TextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Last name") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        onClick(firstName, lastName)
                    }
                ) {
                    Text("Save")
                }
            }
        )
    }

    FloatingActionButton(
        onClick = { openDialog.value = true },
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}
