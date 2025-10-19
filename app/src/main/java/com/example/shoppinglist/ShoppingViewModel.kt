package com.example.shoppinglist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ShoppingViewModel : ViewModel() {
    // Ganti dari <String> ke <ShoppingItem>
    private val _shoppingItems = mutableStateListOf<ShoppingItem>()
    val shoppingItems: List<ShoppingItem> get() = _shoppingItems

    // Ubah fungsi ini untuk menerima nama dan detail
    fun addItem(name: String, details: String) {
        if (name.isNotBlank()) {
            _shoppingItems.add(0, ShoppingItem(name = name, details = details))
        }
    }

    // Fungsi baru untuk mengambil item berdasarkan ID
    fun getItemById(itemId: String?): ShoppingItem? {
        return _shoppingItems.find { it.id == itemId }
    }

    // Fungsi baru untuk membersihkan daftar (digunakan di Settings)
    fun clearAllItems() {
        _shoppingItems.clear()
    }
}