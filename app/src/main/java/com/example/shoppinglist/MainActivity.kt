package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.components.ItemInput
import com.example.shoppinglist.components.SearchInput
import com.example.shoppinglist.components.ShoppingList
import com.example.shoppinglist.ui.theme.ShoppingListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListApp(
    navController: NavController,
    // Panggil ViewModel di sini
    shoppingViewModel: ShoppingViewModel
) {
    var newItemText by rememberSaveable { mutableStateOf("") }
    var newItemDetails by rememberSaveable { mutableStateOf("") }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    // Ambil "array" dari ViewModel
    val shoppingItems = shoppingViewModel.shoppingItems

    val filteredItems by remember(searchQuery, shoppingItems) {
        derivedStateOf {
            if (searchQuery.isBlank()) {
                shoppingItems
            } else {
                // Filter berdasarkan nama
                shoppingItems.filter { it.name.contains(searchQuery, ignoreCase = true) }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Item")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            SearchInput(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            ShoppingList(
                items = filteredItems,
                onItemClick = { itemId ->
                    // Kirim ID item ke rute detail
                    navController.navigate(Screen.Detail.createRoute(itemId))
                }
            )
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Tambah Item Baru") },
                text = {
                    // Gunakan Column untuk dua input field
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = newItemText,
                            onValueChange = { newItemText = it },
                            label = { Text("Nama item") }
                        )
                        OutlinedTextField(
                            value = newItemDetails,
                            onValueChange = { newItemDetails = it },
                            label = { Text("Detail (cth: 2 buah, di kulkas)") }
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            // Kirim nama dan detail ke ViewModel
                            shoppingViewModel.addItem(newItemText, newItemDetails)
                            // Reset kedua field
                            newItemText = ""
                            newItemDetails = ""
                            showDialog = false
                        }
                    ) {
                        Text("Tambah")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Batal")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListAppPreview() {
    ShoppingListTheme {
        // Beri ViewModel kosong untuk preview
        ShoppingListApp(
            navController = rememberNavController(),
            shoppingViewModel = ShoppingViewModel()
        )
    }
}