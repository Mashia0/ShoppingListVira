package com.example.shoppinglist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ShoppingViewModel : ViewModel() {
    private val _shoppingItems = mutableStateListOf<String>()
    val shoppingItems: List<String> get() = _shoppingItems

    fun addItem(newItem: String) {
        if (newItem.isNotBlank()) {
            _shoppingItems.add(0, newItem)
        }
    }
}