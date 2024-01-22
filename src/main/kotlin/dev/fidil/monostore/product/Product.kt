package dev.fidil.monostore.product

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.javamoney.moneta.CurrencyUnitBuilder
import org.javamoney.moneta.Money
import java.util.*
import kotlin.properties.Delegates

@Aggregate
class Product() {
    @AggregateIdentifier
    private lateinit var id: UUID
    private lateinit var name: String
    private lateinit var description: String
    private var price: Money = Money.zero(CurrencyUnitBuilder.of("USD", "default").build())
    private lateinit var state: ProductState
    private lateinit var imageUrl: String
    private var inStock by Delegates.notNull<Int>()

    @CommandHandler
    constructor(command: CreateProduct) : this() {
        AggregateLifecycle.apply(
            ProductCreated(
                UUID.randomUUID(),
                command.name,
                command.description,
                command.price,
                command.inStock,
                command.imageUrl,
                command.state
            )
        )
    }

    @CommandHandler
    fun handle(command: PublishProduct) {
        if (state != ProductState.DRAFT) {
            throw IllegalStateException("Product cannot be published. Invalid state: $state")
        }
        AggregateLifecycle.apply(ProductPublished(command.productId))
    }

    @CommandHandler
    fun handle(command: AddToStock) {
        AggregateLifecycle.apply(StockAdded(command.productId, command.quantity))
    }

    @CommandHandler
    fun handle(command: RemoveFromStock) {
        if (state != ProductState.PUBLISHED) {
            throw IllegalStateException("Product cannot be removed from stock. Invalid state: $state")
        }

        if (this.inStock - command.quantity < 0) {
            throw IllegalStateException("Not enough stock to remove $command.quantity items from stock")
        }

        AggregateLifecycle.apply(StockRemoved(command.productId, command.quantity))

        if (this.inStock - command.quantity == 0) {
            AggregateLifecycle.apply(ProductSoldOut(command.productId))
        }
    }

    @EventSourcingHandler
    fun on(event: ProductCreated) {
        id = event.accountId
        name = event.name
        description = event.description
        price = event.price
        inStock = event.inStock
        imageUrl = event.imageUrl
        state = event.state
    }

    @EventSourcingHandler
    fun on(event: ProductPublished) {
        state = ProductState.PUBLISHED
    }

    @EventSourcingHandler
    fun on(event: StockAdded) {
        inStock += event.quantity
    }

    @EventSourcingHandler
    fun on(event: StockRemoved) {
        inStock -= event.quantity

    }

    @EventSourcingHandler
    fun on(event: ProductSoldOut) {
        state = ProductState.SOLD_OUT
    }
}