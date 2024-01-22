package dev.fidil.monostore.product

import org.axonframework.modelling.command.TargetAggregateIdentifier
import org.javamoney.moneta.Money
import java.util.*

data class CreateProduct(
    val name: String,
    val description: String,
    val price: Money,
    val inStock: Int,
    val imageUrl: String,
    val state: ProductState = ProductState.DRAFT
)

data class PublishProduct(@TargetAggregateIdentifier val productId: UUID)

data class AddToStock(@TargetAggregateIdentifier val productId: UUID, val quantity: Int)

data class RemoveFromStock(@TargetAggregateIdentifier val productId: UUID, val quantity: Int)