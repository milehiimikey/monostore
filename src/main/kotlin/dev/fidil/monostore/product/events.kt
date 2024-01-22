package dev.fidil.monostore.product

import org.javamoney.moneta.Money
import java.util.*

data class ProductCreated(
    val accountId: UUID,
    val name: String,
    val description: String,
    val price: Money,
    val inStock: Int,
    val imageUrl: String,
    val state: ProductState
)

data class ProductPublished(val productId: UUID)

data class StockAdded(val productId: UUID, val quantity: Int)

data class StockRemoved(val productId: UUID, val quantity: Int)

data class ProductSoldOut(val productId: UUID)