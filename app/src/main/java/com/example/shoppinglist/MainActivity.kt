package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.components.ItemInput
import com.example.shoppinglist.components.SearchInput
import com.example.shoppinglist.components.ShoppingList
import com.example.shoppinglist.components.Title
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
                    // PANGGIL MainNavigation DI SINI
                    MainNavigation()
                }
            }
        }
    }
}

// Composable ini tetap ada, karena dipanggil oleh NavHost
@Composable
fun ShoppingListApp() {
    // State for the text in the new item input field
    var newItemText by rememberSaveable { mutableStateOf("") }
    // State for the text in the search input field
    var searchQuery by rememberSaveable { mutableStateOf("") }
    // State for the list of shopping items
    val shoppingItems = remember { mutableStateListOf<String>() }

    // A derived state that automatically updates when searchQuery or shoppingItems change
    val filteredItems by remember(searchQuery, shoppingItems) {
        derivedStateOf {
            if (searchQuery.isBlank()) {
                shoppingItems.toList() // Return a stable copy for the UI
            } else {
                shoppingItems.filter { it.contains(searchQuery, ignoreCase = true) }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Title() // Title sekarang ada di TopAppBar, jadi bisa di-disable
        Spacer(modifier = Modifier.height(16.dp))
        ItemInput(
            text = newItemText,
            onTextChange = { newItemText = it },
            onAddItem = {
                if (newItemText.isNotBlank()) {
                    shoppingItems.add(0, newItemText) // Tambahkan ke atas list
                    newItemText = "" // Clear the input field after adding
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchInput(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ShoppingList(items = filteredItems)
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListAppPreview() {
    ShoppingListTheme {
        ShoppingListApp()
    }
}