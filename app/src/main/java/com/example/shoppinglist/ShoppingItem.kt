package com.example.shoppinglist

import java.util.UUID

data class ShoppingItem(
    // Kita gunakan ID unik untuk membedakan tiap item
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val details: String
)