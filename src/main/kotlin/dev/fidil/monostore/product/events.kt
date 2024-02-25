package dev.fidil.monostore.product

import org.javamoney.moneta.Money
import java.util.*

data class ProductAddedToStock(
    val accountId: UUID,
    val name: String,
    val description: String,
    val price: Money,
    val inStock: Int,
    val imageUrl: String,
    val state: ProductState
)