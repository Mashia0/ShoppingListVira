package com.example.shoppinglist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ShoppingViewModel : ViewModel() {
    private val _shoppingItems = mutableStateListOf<ShoppingItem>()
    val shoppingItems: List<ShoppingItem> get() = _shoppingItems

    fun addItem(name: String, brand: String, size: String, details: String) {
        if (name.isNotBlank()) {
            _shoppingItems.add(
                0,
                ShoppingItem(name = name, brand = brand, size = size, details = details)
            )
        }
    }

    fun getItemById(itemId: String?): ShoppingItem? {
        if (itemId == null) return null
        return _shoppingItems.find { it.id == itemId }
    }

    fun clearAllItems() {
        _shoppingItems.clear()
    }
}