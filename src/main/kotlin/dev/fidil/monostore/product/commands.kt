package dev.fidil.monostore.product

import org.javamoney.moneta.Money

data class AddProductToStock(
    val name: String,
    val description: String,
    val price: Money,
    val inStock: Int,
    val imageUrl: String,
    val state: ProductState? = ProductState.PENDING
)