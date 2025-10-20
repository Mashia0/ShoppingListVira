package com.example.shoppinglist

import java.util.UUID

data class ShoppingItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val brand: String, // Merek barang
    val size: String,  // Ukuran (ml/gr)
    val details: String // Catatan tambahan
)