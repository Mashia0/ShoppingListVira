package com.example.shoppinglist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ShoppingViewModel : ViewModel() {
    // Gunakan "cetakan" ShoppingItem yang baru
    private val _shoppingItems = mutableStateListOf<ShoppingItem>()
    val shoppingItems: List<ShoppingItem> get() = _shoppingItems

    // Ubah fungsi ini untuk menerima semua detail baru
    fun addItem(name: String, brand: String, size: String, details: String) {
        if (name.isNotBlank()) {
            _shoppingItems.add(
                0,
                ShoppingItem(name = name, brand = brand, size = size, details = details)
            )
        }
    }

    // Fungsi untuk mengambil item berdasarkan ID
    fun getItemById(itemId: String?): ShoppingItem? {
        if (itemId == null) return null
        return _shoppingItems.find { it.id == itemId }
    }

    // Fungsi untuk membersihkan daftar
    fun clearAllItems() {
        _shoppingItems.clear()
    }
}