package dev.fidil.monostore.product

import org.axonframework.modelling.command.TargetAggregateIdentifier
import org.javamoney.moneta.Money
import java.util.UUID

data class AddProductToStock(
    val name: String,
    val description: String,
    val price: Money,
    val inStock: Int,
    val imageUrl: String,
    val state: ProductState? = ProductState.PENDING
)

data class ActivateProduct(@TargetAggregateIdentifier val productId: UUID)